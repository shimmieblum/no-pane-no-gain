import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class OptionsBox {

    static boolean result;
    public static boolean createAlertBox(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label(message);
        Button yesButton = new Button("yes");
        Button noButton = new Button("no");
        yesButton.setOnAction(e -> {
            result = true;
            window.close();}
        );
        noButton.setOnAction(e -> {
            result = false;
            window.close();
        });

        HBox optionsBox = new HBox();
        optionsBox.getChildren().addAll(yesButton,noButton);
        optionsBox.setSpacing(3);
        optionsBox.setAlignment(Pos.CENTER);

        VBox root = new VBox();
        root.getChildren().addAll(messageLabel, optionsBox);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);

        Scene scene  = new Scene(root, 300,100);
        window.setScene(scene);

        window.showAndWait();
        return result;
    }
}