package gui;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * Shared utility methods for the GUI layer.
 */
public final class GuiUtils {

    private GuiUtils() {}

    /**
     * Displays a modal error dialog with the given title and message.
     * The dialog is attached to the active application window so it appears
     * centred over the app rather than in a separate top-left window.
     *
     * @param title   Short title shown in the dialog header.
     * @param message Detailed message shown in the dialog body.
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);

        Window.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst().ifPresent(alert::initOwner);

        alert.showAndWait();
    }
}