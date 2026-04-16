package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.Application;
import logic.ApplicationController;
import logic.ApplicationStatus;

import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Controls the edit-application view.
 * Allows the user to modify all application fields including company, role,
 * pay, location, status, deadline, and notes.
 *
 * @author ashjwley03
 */
public class EditApplicationController {

    private static final String FEEDBACK_ERROR   = "feedback-error";
    private static final String FEEDBACK_SUCCESS = "feedback-success";
    private static final String FEEDBACK_NEUTRAL = "feedback-neutral";

    @FXML private Label pageTitleLabel;
    @FXML private TextField companyField;
    @FXML private TextField roleField;
    @FXML private TextField payField;
    @FXML private TextField locationField;
    @FXML private Label dateAppliedLabel;
    @FXML private ChoiceBox<ApplicationStatus> statusChoice;
    @FXML private Label currentStatusLabel;
    @FXML private DatePicker deadlinePicker;
    @FXML private TextArea notesArea;
    @FXML private Label feedbackLabel;

    private ApplicationController appController;
    private Application application;
    private Runnable onBack;

    /**
     * Sets the ApplicationController used to persist changes.
     * Must be called by MainController before the view is displayed.
     *
     * @param appController The application controller to use.
     */
    public void setAppController(ApplicationController appController) {
        this.appController = appController;
    }

    /**
     * Registers a callback to return to the dashboard.
     *
     * @param onBack Runnable to execute when navigating back.
     */
    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }

    /**
     * Loads the given application's data into the view fields.
     * Must be called after FXML injection and after setAppController.
     *
     * @param app The application to display and edit.
     */
    public void loadApplication(Application app) {
        this.application = app;

        pageTitleLabel.setText(app.getCompanyName() + " — " + app.getRoleTitle());

        companyField.setText(app.getCompanyName());
        roleField.setText(app.getRoleTitle());
        payField.setText(app.getPay() > 0 ? String.format("%.0f", app.getPay()) : "");
        locationField.setText(app.getLocation() != null ? app.getLocation() : "");
        dateAppliedLabel.setText(app.getDateApplied().toString());

        statusChoice.getItems().setAll(ApplicationStatus.values());
        statusChoice.setValue(app.getStatus());
        currentStatusLabel.setText("Currently: " + app.getStatus().name());

        deadlinePicker.setValue(app.getDeadline());

        notesArea.setText(app.getNotes() != null ? app.getNotes() : "");
        feedbackLabel.setText("");
    }

    /**
     * Clears the deadline date picker, setting the deadline to null.
     */
    @FXML
    private void handleClearDeadline() {
        deadlinePicker.setValue(null);
    }

    /**
     * Validates and saves any modified fields to the application.
     * Delegates to field-specific save methods in sequence, stopping on the first error.
     * Displays inline feedback indicating success, no changes, or the specific error encountered.
     *
     * <p><b>Note:</b> saves are not atomic — if an earlier step succeeds but a later step
     * fails, the earlier change is already persisted and will not be rolled back.</p>
     */
    @FXML
    private void handleSave() {
        clearFeedback();
        OptionalDouble parsedPay = parsePay();
        if (parsedPay.isEmpty()) return;

        try {
            boolean changed = false;
            changed |= saveDetails(parsedPay.getAsDouble());
            changed |= saveStatus();
            changed |= saveDeadline();
            changed |= saveNotes();

            if (changed) {
                refreshAfterSave();
                setFeedback("Changes saved successfully.", FEEDBACK_SUCCESS);
            } else {
                setFeedback("No changes to save.", FEEDBACK_NEUTRAL);
            }
        } catch (IllegalStateException e) {
            // Status transition error — revert the choice box to the last known valid state
            statusChoice.setValue(application.getStatus());
            setFeedback(e.getMessage(), FEEDBACK_ERROR);
        } catch (RuntimeException e) {
            setFeedback(e.getMessage(), FEEDBACK_ERROR);
        }
    }

    /**
     * Saves changes to core application details (company, role, pay, location)
     * if any of those fields have been modified since the view was loaded.
     *
     * @param newPay The already-validated pay value from {@link #parsePay()}.
     * @return {@code true} if a change was detected and persisted, {@code false} otherwise.
     * @throws IllegalArgumentException if the company name or role title is blank.
     */
    private boolean saveDetails(double newPay) {
        String newCompany  = companyField.getText().trim();
        String newRole     = roleField.getText().trim();
        String newLocation = locationField.getText().trim();

        boolean hasChanged = !newCompany.equals(application.getCompanyName())
                || !newRole.equals(application.getRoleTitle())
                || Double.compare(newPay, application.getPay()) != 0
                || !newLocation.equals(
                application.getLocation() != null ? application.getLocation() : "");
        if (!hasChanged) return false;

        appController.updateDetails(
                application.getId(), newCompany, newRole, newPay, newLocation);
        return true;
    }

    /**
     * Saves the status field if it has been changed to a different value.
     *
     * @return {@code true} if a change was detected and persisted, {@code false} otherwise.
     * @throws IllegalStateException if the selected transition violates business rules
     *                               (e.g. modifying a terminal-state application).
     */
    private boolean saveStatus() {
        ApplicationStatus selected = statusChoice.getValue();
        if (selected == null || selected == application.getStatus()) return false;
        appController.updateStatus(application.getId(), selected);
        return true;
    }

    /**
     * Saves the deadline field if it has been changed or cleared since the view was loaded.
     *
     * @return {@code true} if a change was detected and persisted, {@code false} otherwise.
     * @throws RuntimeException wrapping the underlying storage error, prefixed with a
     *                          user-facing message.
     */
    private boolean saveDeadline() {
        LocalDate newDeadline     = deadlinePicker.getValue();
        LocalDate currentDeadline = application.getDeadline();
        boolean hasChanged = (newDeadline == null && currentDeadline != null)
                || (newDeadline != null && !newDeadline.equals(currentDeadline));
        if (!hasChanged) return false;

        try {
            appController.updateDeadline(application.getId(), newDeadline);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update deadline: " + e.getMessage(), e);
        }
        return true;
    }

    /**
     * Saves the notes field if the text has been modified since the view was loaded.
     *
     * @return {@code true} if a change was detected and persisted, {@code false} otherwise.
     * @throws RuntimeException wrapping the underlying storage error, prefixed with a
     *                          user-facing message.
     */
    private boolean saveNotes() {
        String newNotes     = notesArea.getText().trim();
        String currentNotes = application.getNotes() != null ? application.getNotes() : "";
        if (newNotes.equals(currentNotes)) return false;

        try {
            appController.updateNotes(application.getId(), newNotes);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to update notes: " + e.getMessage(), e);
        }
        return true;
    }

    /**
     * Reloads the application from storage and refreshes the page title and current
     * status label to reflect the latest persisted state.
     * Called after at least one field has been successfully saved.
     */
    private void refreshAfterSave() {
        this.application = appController.getApplicationById(application.getId());
        pageTitleLabel.setText(
                application.getCompanyName() + " — " + application.getRoleTitle());
        currentStatusLabel.setText("Currently: " + application.getStatus().name());
    }

    /**
     * Sets the feedback label text and applies the given CSS style class,
     * removing any previously applied feedback style first.
     *
     * @param message    The message to display to the user.
     * @param styleClass One of {@link #FEEDBACK_ERROR}, {@link #FEEDBACK_SUCCESS},
     *                   or {@link #FEEDBACK_NEUTRAL}.
     */
    private void setFeedback(String message, String styleClass) {
        feedbackLabel.getStyleClass().removeAll(FEEDBACK_ERROR, FEEDBACK_SUCCESS, FEEDBACK_NEUTRAL);
        feedbackLabel.getStyleClass().add(styleClass);
        feedbackLabel.setText(message);
    }

    /**
     * Clears the feedback label text and removes any applied feedback style class.
     */
    private void clearFeedback() {
        feedbackLabel.getStyleClass().removeAll(FEEDBACK_ERROR, FEEDBACK_SUCCESS, FEEDBACK_NEUTRAL);
        feedbackLabel.setText("");
    }

    /**
     * Parses the pay field text into a double value.
     * Returns an empty {@code OptionalDouble} and sets an error feedback message
     * if the input is not a valid non-negative number.
     *
     * @return An {@code OptionalDouble} containing the parsed value, or
     *         {@code OptionalDouble.empty()} if parsing fails.
     */
    private OptionalDouble parsePay() {
        String payText = payField.getText().trim();
        if (payText.isEmpty()) return OptionalDouble.of(0);
        try {
            double value = Double.parseDouble(payText);
            if (value < 0) {
                setFeedback("Pay cannot be negative.", FEEDBACK_ERROR);
                return OptionalDouble.empty();
            }
            return OptionalDouble.of(value);
        } catch (NumberFormatException e) {
            setFeedback("Pay must be a valid number (e.g. 120000).", FEEDBACK_ERROR);
            return OptionalDouble.empty();
        }
    }

    /**
     * Prompts the user for confirmation and, if confirmed, permanently deletes
     * the current application and navigates back to the dashboard.
     */
    @FXML
    private void handleDelete() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Application");
        confirm.setHeaderText("Are you sure?");
        confirm.setContentText("This will permanently delete the application for "
                + application.getCompanyName() + " — " + application.getRoleTitle() + ".");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                appController.deleteApplication(application.getId());
            } catch (RuntimeException e) {
                GuiUtils.showError("Could Not Delete", e.getMessage());
                return;
            }
            if (onBack != null) {
                onBack.run();
            }
        }
    }

    /**
     * Navigates back to the dashboard without saving any unsaved changes.
     */
    @FXML
    private void handleBack() {
        if (onBack != null) {
            onBack.run();
        }
    }
}