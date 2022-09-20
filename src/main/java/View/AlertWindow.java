package View;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class AlertWindow {

    public static Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public AlertWindow() {
    }

    public static void offlineOutputAPIWindow(String report) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Report");
        alert.setContentText(report);
        alert.show();
    }

    public static void onlineOutputAPIWindow(String report) {
        TextInputDialog dialog = new TextInputDialog(report);
        dialog.getEditor().setEditable(false);
        dialog.getEditor().setStyle("-fx-background-color: transparent;" +
                "-fx-background-insets: 0px");
        dialog.setTitle("Report dialog");
        dialog.setHeaderText("Here is your report output");
        dialog.show();
    }

    public static void loadingWindow() {
        alert.setHeaderText("Please wait a moment.");
        File file = new File("src/main/resources/giphy.gif");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }
}
