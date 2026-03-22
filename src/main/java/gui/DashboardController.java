package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controls the dashboard view of the application.
 * Manages the display and filtering of internship applications in a table.
 */
public class DashboardController {

    @FXML private TextField searchField;
    @FXML private TableView<ApplicationEntry> applicationTable;
    @FXML private TableColumn<ApplicationEntry, String> colCompany;
    @FXML private TableColumn<ApplicationEntry, String> colRole;
    @FXML private TableColumn<ApplicationEntry, String> colStatus;
    @FXML private TableColumn<ApplicationEntry, String> colDeadline;

    private final ObservableList<ApplicationEntry> masterList =
            FXCollections.observableArrayList();
    private FilteredList<ApplicationEntry> filteredList;

    /**
     * Initializes the dashboard after the FXML has been loaded.
     * Sets up table columns and binds the filtered list to the table view.
     */
    @FXML
    public void initialize() {
        colCompany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("roleTitle"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        filteredList = new FilteredList<>(masterList, p -> true);
        applicationTable.setItems(filteredList);
    }

    @FXML
    private void handleNewApplication() {
        System.out.println("New application alert");
    }

    /**
     * Filters the application list based on the search keyword.
     * Matches entries by company name or role title.
     */
    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();
        filteredList.setPredicate(entry ->
                keyword.isEmpty()
                        || entry.getCompanyName().toLowerCase().contains(keyword)
                        || entry.getRoleTitle().toLowerCase().contains(keyword)
        );
    }
}