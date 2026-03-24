package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import logic.Application;
import logic.ApplicationController;
import storage.FileStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controls the compare view of the application.
 * Lets users select applications via checkboxes and compare them side by side.
 */
public class CompareController {

    @FXML private ListView<Application> appListView;
    @FXML private TableView<Application> compareTable;
    @FXML private TableColumn<Application, String> colCompany;
    @FXML private TableColumn<Application, String> colRole;
    @FXML private TableColumn<Application, String> colPay;
    @FXML private TableColumn<Application, String> colLocation;
    @FXML private TableColumn<Application, String> colStatus;
    @FXML private TableColumn<Application, String> colDeadline;
    @FXML private Label hintLabel;

    private final ApplicationController appController =
            new ApplicationController(new FileStorage());

    /** Tracks which applications are currently checked. */
    private final Map<String, BooleanProperty> checkedState = new LinkedHashMap<>();

    private final ObservableList<Application> allApps    = FXCollections.observableArrayList();
    private final ObservableList<Application> selectedApps = FXCollections.observableArrayList();

    /**
     * Initializes the compare view after the FXML has been loaded.
     * Populates the checkbox list and sets up the comparison table columns.
     */
    @FXML
    public void initialize() {
        setupTable();
        loadApplications();
        setupListView();
    }

    /** Configures table columns with appropriate value factories. */
    private void setupTable() {
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
            var d = c.getValue().getDeadline();
            return new SimpleStringProperty(d != null ? d.toString() : "—");
        });

        // Highlight the highest-pay row
        compareTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Application app, boolean empty) {
                super.updateItem(app, empty);
                if (empty || app == null) {
                    setStyle("");
                } else {
                    boolean isTop = !selectedApps.isEmpty()
                            && selectedApps.stream()
                               .max(Comparator.comparingDouble(Application::getPay))
                               .filter(a -> a.getId().equals(app.getId()))
                               .isPresent()
                            && app.getPay() > 0;
                    setStyle(isTop ? "-fx-background-color: #f0fdf4;" : "");
                }
            }
        });

        compareTable.setItems(selectedApps);
        compareTable.setPlaceholder(new Label("Select applications on the left to compare."));
    }

    /** Loads all applications from storage into the checkbox list. */
    private void loadApplications() {
        List<Application> apps = appController.getAllApplications();
        allApps.setAll(apps);
        apps.forEach(a -> {
            BooleanProperty checked = new SimpleBooleanProperty(false);
            checked.addListener((obs, wasChecked, isNowChecked) -> updateComparison());
            checkedState.put(a.getId(), checked);
        });
    }

    /**
     * Sets up the ListView with checkboxes.
     * Each cell displays "Company — Role" and toggles the checked state on click.
     */
    private void setupListView() {
        appListView.setItems(allApps);
        appListView.setCellFactory(CheckBoxListCell.forListView(
                app -> checkedState.getOrDefault(app.getId(), new SimpleBooleanProperty(false)),
                new javafx.util.StringConverter<>() {
                    @Override public String toString(Application a) {
                        return a == null ? "" : a.getCompanyName() + "  —  " + a.getRoleTitle();
                    }
                    @Override public Application fromString(String s) { return null; }
                }
        ));

        if (allApps.isEmpty()) {
            hintLabel.setText("No applications found. Add some from the Dashboard first.");
        } else {
            hintLabel.setText("Check applications to compare. Highest pay is highlighted in green.");
        }
    }

    /**
     * Rebuilds the comparison table based on currently checked applications.
     * Results are sorted by pay descending, matching ApplicationController.compareApplications().
     */
    private void updateComparison() {
        List<String> checkedIds = checkedState.entrySet().stream()
                .filter(e -> e.getValue().get())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (checkedIds.isEmpty()) {
            selectedApps.clear();
            return;
        }

        List<Application> compared = appController.compareApplications(checkedIds);
        selectedApps.setAll(compared);

        // Force row factory to re-evaluate highlight after list changes
        compareTable.refresh();
    }
}
