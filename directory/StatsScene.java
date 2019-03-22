import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * creates a scene which is the statistic scene of the application. It displays different statistics about the data.
 */
public class StatsScene {
    private Scene statsScene;
    // the properties
    private Listings listings;
    // the root of the Scene
    private BorderPane root;
    // number text fields
    private NumberTextField minField, maxField;
    // labels used
    private Label priceDetails,priceRangeLabel;
    // buttons
    private Button submit;
    // the object containing all statistics
    private Statistics stats;

    public StatsScene(Listings l){
        listings = l;
        stats  = new Statistics(listings);
        createScene();

    }

    /**
     * set up the welcome scene.
     */
    public void createScene() {
        root = new BorderPane();
        addCenterPane();
        statsScene = new Scene(root, 300,300);
    }


    /**
     * Add a FlowPane to the center of the root pane.
     */
    private void addCenterPane() {
        FlowPane centerPane = new FlowPane();
        centerPane.setPadding(new Insets(4,4,4,4));
        centerPane.setVgap(4);
        centerPane.setHgap(4);

        centerPane.getChildren().addAll(createPane(), createPane(),createPane(),createPane() );
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);
    }

    /**
     * create BorderPane with  one statistic
     * @return the initial BorderPane with one statistic
     */
    private BorderPane createPane() {
        BorderPane pane = new StatPane(stats).getPane();
        return pane;
    }


    /**
     * @return the welcomeScene created by this Class
     */
    public Scene getScene() {
        return statsScene;
    }




    /**
     * Inner class to limit the a textField to accept only numbers.
     */
    public class NumberTextField extends TextField
    {
        /**
         * only allow new text to be input if the text is valid. ie only a sequence of numbers.
         */
        @Override
        public void replaceText(int start, int end, String text)
        {
            if (validate(text))
            {
                super.replaceText(start, end, text);
            }
        }

        /**
         * only allow new text to be input if the text is valid. ie only a sequence of numbers.
         */
        @Override
        public void replaceSelection(String text)
        {
            if (validate(text))
            {
                super.replaceSelection(text);
            }
        }

        /**
         * check a string is valid. ie only a sequence of numbers.
         * @return true if it is valid, false if it isn't.
         */
        private boolean validate(String text)
        {
            return text.matches("[0-9]*");
        }
    }
}
