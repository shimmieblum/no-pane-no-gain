import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void createAlertBox(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label(message);

        Button OK = new Button("OK");
        OK.setOnAction(actionEvent -> window.close());
        VBox root = new VBox();
        root.getChildren().addAll(messageLabel,OK);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Scene scene  = new Scene(root, 300,100);
        window.setScene(scene);

        window.showAndWait();
    }
}
