package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import logic.Application;
import logic.ApplicationController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls the compare view of the application.
 * Lets users select applications from the list and compare them side by side.
 */
public class CompareController {

    private static final String STYLE_ROW_BEST_PAY = "row-best-pay";

    @FXML private ListView<Application> appListView;
    @FXML private TableView<Application> compareTable;
    @FXML private TableColumn<Application, String> colCompany;
    @FXML private TableColumn<Application, String> colRole;
    @FXML private TableColumn<Application, String> colPay;
    @FXML private TableColumn<Application, String> colLocation;
    @FXML private TableColumn<Application, String> colStatus;
    @FXML private TableColumn<Application, String> colDeadline;
    @FXML private Label hintLabel;

    private ApplicationController appController;

    private final ObservableList<Application> allApps = FXCollections.observableArrayList();
    private final ObservableList<Application> selectedApps = FXCollections.observableArrayList();

    /**
     * Sets the ApplicationController used to load and compare applications.
     * Must be called by MainController before the view is displayed.
     *
     * @param appController The application controller to use.
     */
    public void setAppController(ApplicationController appController) {
        this.appController = appController;
    }

    /**
     * Initialises the controller after the FXML has been loaded.
     * Sets up the comparison table, list selection, and row highlighting.
     */
    @FXML
    public void initialize() {
        setupTable();
        appListView.setItems(allApps);
        appListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        appListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Application app, boolean empty) {
                super.updateItem(app, empty);
                if (empty || app == null) {
                    setText(null);
                } else {
                    setText(app.getCompanyName() + "  —  " + app.getRoleTitle());
                }
            }
        });
        appListView.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener.Change<? extends Application> change) -> {
                    updateComparisonFromSelection();
                });
    }

    /**
     * Loads application data after dependencies have been injected.
     * Called by MainController immediately after setAppController.
     * Displays an error dialog if the logic layer throws an unexpected exception.
     */
    public void loadData() {
        try {
            loadApplications();
        } catch (RuntimeException e) {
            GuiUtils.showError("Could Not Load Applications", e.getMessage());
            return;
        }
        updateHintLabel();
    }

    /**
     * Returns the compared and sorted list of applications for the given IDs.
     * Package-private to allow direct invocation from tests without requiring JavaFX.
     *
     * @param ids List of application IDs to compare.
     * @return Applications sorted by pay descending, or an empty list if ids is empty.
     */
    List<Application> getComparedApplications(List<String> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return appController.compareApplications(ids);
    }

    private void setupTable() {
        compareTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        colCompany.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getCompanyName()));
        colRole.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getRoleTitle()));
        colPay.setCellValueFactory(c -> {
            double pay = c.getValue().getPay();
            return new SimpleStringProperty(pay > 0 ? String.format("$%.0f", pay) : "—");
        });
        colLocation.setCellValueFactory(c -> {
            String loc = c.getValue().getLocation();
            return new SimpleStringProperty(loc != null && !loc.isBlank() ? loc : "—");
        });
        colStatus.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getStatus().name()));
        colDeadline.setCellValueFactory(c -> {
            LocalDate d = c.getValue().getDeadline();
            return new SimpleStringProperty(d != null ? d.toString() : "—");
        });

        compareTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Application app, boolean empty) {
                super.updateItem(app, empty);
                getStyleClass().remove(STYLE_ROW_BEST_PAY);
                if (!empty && app != null && !selectedApps.isEmpty()) {
                    Application topPayer = selectedApps.stream()
                            .max(Comparator.comparingDouble(Application::getPay))
                            .orElse(null);
                    if (topPayer != null && topPayer.getId().equals(app.getId())) {
                        getStyleClass().add(STYLE_ROW_BEST_PAY);
                    }
                }
            }
        });

        compareTable.setItems(selectedApps);
        compareTable.setPlaceholder(new Label("Select applications on the left to compare."));
    }

    private void loadApplications() {
        List<Application> apps = appController.getAllApplications();
        appListView.getSelectionModel().clearSelection();
        allApps.setAll(apps);
    }

    private void updateHintLabel() {
        if (allApps.isEmpty()) {
            hintLabel.setText("No applications found. Add some from the Dashboard first.");
        } else {
            hintLabel.setText("Click an app to compare. Hold ⌘ (Mac) or Ctrl (Windows) and click to select several.");
        }
    }

    private void updateComparisonFromSelection() {
        List<Application> selected = new ArrayList<>(
                appListView.getSelectionModel().getSelectedItems());
        if (selected.isEmpty()) {
            selectedApps.clear();
            return;
        }

        List<String> ids = selected.stream()
                .map(Application::getId)
                .collect(Collectors.toList());

        try {
            List<Application> compared = appController.compareApplications(ids);
            selectedApps.setAll(compared);
            compareTable.refresh();
        } catch (RuntimeException e) {
            GuiUtils.showError("Could Not Compare Applications", e.getMessage());
        }
    }
}