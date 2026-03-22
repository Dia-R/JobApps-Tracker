package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * Controls the main window of the application.
 * Handles navigation between different views displayed in the content area.
 */
public class MainController {

    /**
     * Initializes the controller after the FXML has been loaded.
     * Displays the dashboard view by default.
     */
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        showDashboard();
    }

    @FXML
    private void showDashboard() {
        loadView("/view/DashboardView.fxml");
    }

    @FXML
    private void showCalendar() {
        loadView("/view/CalendarView.fxml");
    }

    @FXML
    private void showCompare() {
        loadView("/view/CompareView.fxml");
    }

    /**
     * Loads the specified FXML view and replaces the current content in the content area.
     *
     * @param fxmlPath Path to the FXML file to be loaded.
     */
    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load view: " + fxmlPath, e);
        }
    }
}