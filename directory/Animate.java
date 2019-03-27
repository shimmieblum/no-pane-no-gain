import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Animate {
    Node node;
    double scaleX;
    double scaleY;


    public Animate(Node node) {
        this.node = node;
        scaleX = node.getScaleX();
        scaleY = node.getScaleY();
        animate();
    }

    public void animate() {
        Random random = new Random();
        int i = random.nextInt(2);
        switch (i){
            case 0:
                scaleTransition();
                break;
            case 1:
                rotateTransition();
                break;
        }
    }

    private void scaleTransition() {

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setNode(node);

        scaleTransition.setByY(0.5);
        scaleTransition.setByX(0.5);

        //Setting the cycle count for the translation
        scaleTransition.setCycleCount(40);

        //Setting auto reverse value to true
        scaleTransition.setAutoReverse(true);

        scaleTransition.play();

        stopBox(scaleTransition);
    }

    private void rotateTransition() {


        RotateTransition rt = new RotateTransition(Duration.millis(3000), node);
        rt.setByAngle(180);
        rt.setCycleCount(4);
        rt.setAutoReverse(true);

        rt.play();

        stopBox(rt);
    }

    private void stopBox(Animation animation) {
        Stage stage = new Stage();
        stage.setOnCloseRequest(e -> stopAnimation(animation, stage));
        stage.setTitle("Stop animation");
        stage.initModality(Modality.APPLICATION_MODAL);

        FlowPane pane = new FlowPane();
        Button stop = new Button("Stop");
        stop.setOnAction(actionEvent -> {
            stopAnimation(animation, stage);
        });

        pane.getChildren().add(stop);
        pane.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(pane));
        stage.setWidth(300);
        stage.setHeight(100);
        stage.showAndWait();
    }

    private void stopAnimation(Animation animation, Stage stage) {
        animation.stop();
        node.setRotate(0);
        node.setScaleX(scaleX);
        node.setScaleY(scaleY);
        stage.close();
    }


}
