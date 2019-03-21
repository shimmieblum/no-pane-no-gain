import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

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
    private Map<String, Object> allStats;
    // the currently displayes statistics - always 4
    private Map<String, Object> usedStats;
    //the currently unused statistics - always 4
    private Map<String, Object> unusedStats;


    public StatsScene(Listings l){
        usedStats = new HashMap<String, Object>();
        unusedStats = new HashMap<String, Object>();

        listings = l;
        createScene();
        Statistics s  = new Statistics(listings);
        allStats = s.getStats();
        for(int i = 0; i<4; i++ ) {
            //assign 4 to each stat group
        }


    }

    /**
     * set up the welcome scene.
     */
    public void createScene() {
        root = new BorderPane();
        addTopPane();
        statsScene = new Scene(root, 300,300);
    }

    /**
     * Add a gridPane to the top of the root pane.
     */
    private void addTopPane() {
        VBox topPane = new VBox();
        topPane.setPadding(new Insets(4,4,4,4));
        priceRangeLabel = new Label("Price range");

        topPane.getChildren().addAll(priceRangeLabel,createPricePane(),createPriceDetails());
        topPane.setAlignment(Pos.TOP_RIGHT);
        root.setTop(topPane);
    }

    /**
     * create pricePane which contains the NumberTextBoxes to be used to input the prices.
     * @return the pricePane
     */
    private HBox createPricePane() {
        // pricePane
        HBox pricePane = new HBox();
        pricePane.setSpacing(3);
        //  numberText Boxes
        minField = new NumberTextField();
        maxField = new NumberTextField();
        submit = new Button("Submit");
        submit.setOnAction(e -> setPrice());
        Label sign1 = new Label("From: £");
        Label sign2 = new Label("To: £");
        setTextSize(12, sign1,sign2, priceRangeLabel, submit);
        pricePane.setAlignment(Pos.TOP_RIGHT);

        // set prompt text in text box
        minField.setPromptText("min price");
        maxField.setPromptText("max price");
        pricePane.getChildren().addAll(sign1,minField,sign2,maxField, submit);

        return pricePane;
    }

    /**
     * create the label with the details of the price range.
     * @return the label created.
     */
    private Label createPriceDetails() {
        priceDetails = new Label();
        priceDetails.setText("Please choose a price range.");
        return priceDetails;
    }

    /**
     * update the price range label.
     */
    private void setPriceDetails() {
        priceDetails.setText("price range: From: £" + range.getMinimum() + " To: £" + range.getMaximum());
    }

    /**
     * check the values of min and max and set the PriceRange if they are good.
     * update the price details label.
     * if they are not good, print an appropriate message.
     */
    public void setPrice() {
        int min = Integer.parseInt(minField.getText());
        int max = Integer.parseInt(maxField.getText());

        if(goodValues(min, max)) {
            range.setMinimum(min);
            range.setMaximum(max);
            setPriceDetails();

        }
        else {
            invalidValueMessage();
        }
    }

    /**
     * set the size of the text of a label.
     * @param size the new size
     * @param labeleds the labeled objects to be altered.
     */
    private void setTextSize(double size, Labeled... labeleds) {
        for (int i = 0; i < labeleds.length; i++) {
            Labeled label = labeleds[i];
            label.setFont(new Font(size));
        }
    }

    private void invalidValueMessage() {
        System.out.println("invalid value.");
    }

    /**
     * @return the welcomeScene created by this Class
     */
    public Scene getScene() {
        return welcomeScene;
    }

    /**
     * check the values are good, that is the min value is less than or equal to the max
     * @param min
     * @param max
     * @return true if the values are good, false if they aren't.
     */
    public boolean goodValues(int min, int max) {
        if (min <= max) {
            return true;
        }
        return false;
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
