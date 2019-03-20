import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AlertBox {

    static boolean result;
    public static boolean createAlertBox(String title, String message) {
        Stage window = new Stage();
        window.setWidth(200);
        window.setTitle(title);

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

        VBox root = new VBox();
        root.getChildren().addAll(messageLabel,yesButton,noButton);

        Scene scene  = new Scene(root);
        window.setScene(scene);

        window.show();
        return result;
    }
}





















