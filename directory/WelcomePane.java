

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * creates a scene which is the welcome scene of the application. It displays instructions
 * for use, the logo of the app and a means to input a price range.
 */
public class WelcomePane extends ChoicePane {

    public WelcomePane() {
        super();
    }


    /**
     * create the pane to go in the centre of the window.
     */

    public Pane createCentrePane() {
        GridPane pane = new GridPane();

        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("Untitled - Copy.png")));

        Button helpButton = new Button("Help");
        Button gameButton = new Button("Game");
        Button animationButton = new Button("Animate");

        gameButton.setPrefWidth(70);
        helpButton.setPrefWidth(70);
        animationButton.setPrefWidth(70);

        animationButton.setOnAction(e -> {
            new Animate(view);
            view.setFitWidth(300);
            view.setFitHeight(250);
        });

        view.setFitWidth(300);
        view.setFitHeight(250);

        HBox buttons = new HBox();
        buttons.getChildren().addAll(helpButton, gameButton, animationButton);
        buttons.setSpacing(30);
        buttons.setAlignment(Pos.CENTER);

        pane.add(buttons, 0,0, 2,1);
        pane.add(view, 0,1,2,2);

        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: skyblue");
        return pane;
    }

    /**
     * set a condition: the next button can only be used if a price range has been submitted.
     * @return true if the condition is met. else false.
     */
    @Override
    public boolean nextConditionMet() {
        if (getRange() == null) {
            return false;
        }
        return true;
    }



}
