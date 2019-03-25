
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
public class WelcomeScene extends SceneGenerator {
    private PriceRange range;
    private Label priceDetails;

    public WelcomeScene(){
        super();
        range = new PriceRange();
        priceDetails = new Label();
    }

    /**
     * build the main pane and return it to be used to build the scene.
     * @return the pane created.
     */
    public Pane createPane() {
        BorderPane root = new BorderPane();
        root.setTop(topPane());
        root.setStyle("-fx-background-color: blue");
        root.setCenter(centrePane());
        return root;
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


    /**
     * create the pane to go in the centre of the window.
     */

    public Pane centrePane() {
        GridPane pane = new GridPane();

        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("Property Properly Logo.png")));

        Button helpButton = new Button("Help");
        Button gameButton = new Button("Game");
        Button animationButton = new Button("Animate");

        gameButton.setPrefWidth(70);
        helpButton.setPrefWidth(70);
        animationButton.setPrefWidth(70);

        animationButton.setOnAction(e -> {
            runAnimation(view);
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

        pane.setGridLinesVisible(true);

        pane.setAlignment(Pos.CENTER);

        return pane;
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
        AlertBox.createAlertBox("Invalid Values", "Please enter valid values.");
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



    private void runAnimation(Node node) {

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

        Stage stage = new Stage();
        stage.setTitle("Stop animation");

        FlowPane pane = new FlowPane();
        Button stop = new Button("Stop");
        stop.setOnAction(actionEvent -> {
            scaleTransition.stop();
            stage.close();
        });

        pane.getChildren().add(stop);
        pane.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(pane));

        stage.showAndWait();
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