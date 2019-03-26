import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public abstract class ChoiceScene extends SceneGenerator{

    private PriceRange range;
    private Label priceDetails;

    public ChoiceScene() {
        super();
        range = null;
    }

    @Override
    public Pane createPane() {
        BorderPane pane = new BorderPane();
        pane.setTop(topPane());
        pane.setCenter(createCentrePane());
        return pane;
    }

    /**
     * Add a gridPane to the top of the root pane.
     */
    private Pane topPane() {

        priceDetails = new Label();
        priceDetails.setText("choose a price range");

        VBox topPane = new VBox();
        topPane.getChildren().addAll(createPricePane(), priceDetails);
        topPane.setAlignment(Pos.BASELINE_RIGHT);
        topPane.setSpacing(10);
        topPane.setPadding(new Insets(5,5,5,5));

        topPane.setStyle("-fx-background-color: red");
        return topPane;
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
    public void setPrice(NumberTextField maxField, NumberTextField minField) {

        boolean validValues = false;
        // check there are values input
        if (minField.getLength() > 0 && maxField.getLength() > 0) {

            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            // check the min is less than the max
            if (goodValues(min, max)) {
                range = new PriceRange();
                range.setMinimum(min);
                range.setMaximum(max);
                setPriceDetails();
                validValues = true;
            }
        }
        if(!validValues) {
            invalidValueMessage();
        }
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

    private void invalidValueMessage() {
        AlertBox.createAlertBox("Invalid Values", "Please enter valid values.");
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
        NumberTextField minField = new NumberTextField();
        NumberTextField maxField = new NumberTextField();
        minField.setOnAction(e -> setPrice(maxField, minField));
        maxField.setOnAction(actionEvent -> setPrice(maxField, minField));

        Button submit = new Button("Submit");
        submit.setOnAction(e -> setPrice(maxField, minField));

        Label sign1 = new Label("From: £");
        Label sign2 = new Label("To: £");

        setTextSize(12, sign1,sign2, submit);
        pricePane.setAlignment(Pos.TOP_RIGHT);

        // set prompt text in text box
        minField.setPromptText("min price");
        maxField.setPromptText("max price");

        pricePane.getChildren().addAll(sign1,minField,sign2,maxField, submit);

        return pricePane;
    }

    public abstract Pane createCentrePane();






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