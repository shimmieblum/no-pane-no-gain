import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.HashMap;

/**
 * Creates a scene which is the booking scene of the application. It displays the booking page where the user can book a
 * property from the available listings.
 *
 * @Author Mi-kaela Marciales
 */

public class BookingScene extends SceneGenerator {
    // the properties
    private Listings listings;
    // arrayList of loaded properties
    private AirbnbDataLoader loader;
    // the root of the Scene
    private BorderPane root;
    // variable for total price of booked property
    private int endPrice;
    // HashMap of all the properties
    HashMap<String, Integer> properties = new HashMap<>();

    public BookingScene(Listings l){
        super();
        listings = l;
        createScene();
        // fill HashMap with all the properties
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            properties.put(listings.getProperty(i).getName(), listings.getProperty(i).getPrice());
        }
    }

    /**
     * Build the main pane and return it to be used to build the scene.
     * @return the pane created.
     */
    public Pane createScene() {
        root = new BorderPane();
        root.setCenter(createPane());
        return root;
    }

    /**
     * Create the pane to go in the center of the window.
     * @return the center pane created.
     */
    public Pane createPane() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setVgap(5);
        pane.setHgap(5);

        Label propName = new Label("Property Name:");
        GridPane.setConstraints(propName, 0, 0);
        pane.getChildren().add(propName);
        TextField enterPropName = new TextField();
        enterPropName.setPromptText("Please enter the name of your desired property.");
        GridPane.setConstraints(propName, 1, 0);
        pane.getChildren().add(propName);

        Label nights = new Label("Nights:");
        GridPane.setConstraints(nights, 0, 1);
        pane.getChildren().add(nights);
        TextField enterNights = new TextField();
        enterNights.setPromptText("How many nights would you like to stay?");
        GridPane.setConstraints(enterNights, 1, 1);
        pane.getChildren().add(enterNights);

        Label totalPrice = new Label("Total:");
        GridPane.setConstraints(totalPrice, 0, 2);
        pane.getChildren().add(totalPrice);
        Label grandTotal = new Label(Integer.toString(endPrice));
        GridPane.setConstraints(grandTotal, 1, 2);
        pane.getChildren().add(grandTotal);

        Button book = new Button("BOOK");
        GridPane.setConstraints(book, 0,3);
        pane.getChildren().add(book);

        final Label bookConfirmation = new Label();
        GridPane.setConstraints(bookConfirmation, 0, 4);
        GridPane.setColumnSpan(bookConfirmation, 2);
        pane.getChildren().add(bookConfirmation);

        /**
         * When the user clicks the BOOK button, generate the grand total price
         * and remove the property from available listings.
         * If text is not filled in properly, return an error statement.
         */
        for(int i = 0; i < listings.numberOfProperties(); i++) {
            HashMap<String, Integer> propertyPrices = new HashMap<>();
            propertyPrices.put(listings.getProperty(i).getName(), listings.getProperty(i).getPrice());
            int enteredNights = Integer.parseInt(enterNights.getText());
            //int currentListingIndex = 0;

            //if (enterPropName.getText().equals(listings.getProperty(i).getName())) {
                //currentListingIndex = i;
            //}

            book.setOnAction(e -> {
                if (enterPropName.getText() != null && !enterPropName.getText().isEmpty()
                        && enterNights.getText() != null && !enterNights.getText().isEmpty()
                        && properties.keySet().contains(enterPropName.getText())) {
                    endPrice = enteredNights*propertyPrices.get(enterPropName.getText());
                    properties.remove(enterPropName.getText());
                    //listings.deleteListing(currentListingIndex);
                    bookConfirmation.setText("Thank you for booking!");
                } else {
                    bookConfirmation.setText("Please fill in all your details correctly.");
                }
            });
        }

        pane.setAlignment(Pos.CENTER);

        return pane;
    }
}
