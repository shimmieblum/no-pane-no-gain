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

public class BookingPane extends PaneGenerator {
    // the properties
    private Listings listings;
    // the root of the Scene
    private BorderPane root;
    // variable for total price of booked property
    private int endPrice;
    // HashMap of all the properties
    HashMap<String, Integer> properties = new HashMap<>();

    public BookingPane(Listings l){
        super();
        listings = l;
        // fill HashMap with all the properties
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            properties.put(listings.getProperty(i).getName(), listings.getProperty(i).getPrice());
        }
    }

    /**
     * Create the pane to go in the center of the window.
     * @return the center pane created.
     */
    public Pane getPane(PriceRange range) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setVgap(15);
        pane.setHgap(15);

        Label propName = new Label("Property Name:");
        GridPane.setConstraints(propName, 0, 0);
        pane.getChildren().add(propName);
        TextField enterPropName = new TextField();
        enterPropName.setPromptText("Name of property.");
        GridPane.setConstraints(enterPropName, 1, 0);
        pane.getChildren().add(enterPropName);

        Label nights = new Label("Nights:");
        GridPane.setConstraints(nights, 0, 1);
        pane.getChildren().add(nights);
        TextField enterNights = new TextField();
        enterNights.setPromptText("How many nights?");
        GridPane.setConstraints(enterNights, 1, 1);
        pane.getChildren().add(enterNights);

        Label totalPrice = new Label("Total:");
        GridPane.setConstraints(totalPrice, 0, 2);
        pane.getChildren().add(totalPrice);
        Label grandTotal = new Label("");
        GridPane.setConstraints(grandTotal, 1, 2);
        pane.getChildren().add(grandTotal);

        Button book = new Button("Book");
        GridPane.setConstraints(book, 0,3);
        pane.getChildren().add(book);

        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 3);
        pane.getChildren().add(clear);

        final Label bookConfirmation = new Label();
        GridPane.setConstraints(bookConfirmation, 0, 4);
        GridPane.setColumnSpan(bookConfirmation, 2);
        pane.getChildren().add(bookConfirmation);

        /*
         * When the user clicks the Book button, generate the grand total price
         * and remove the property from available listings.
         * If text is not filled in properly, return an error statement.
         * The user can also click Clear to clear all fields.
         */

        book.setOnAction(e -> {
            if (!properties.keySet().contains(enterPropName.getText())) {
                bookConfirmation.setText("That property does not exist! Please try again.");
            }
            else if (enterPropName.getText() != null && !enterPropName.getText().isEmpty()
                    && enterNights.getText() != null && !enterNights.getText().isEmpty()
                    && properties.keySet().contains(enterPropName.getText())) {
                int enteredNights = Integer.parseInt(enterNights.getText());
                endPrice = enteredNights*properties.get(enterPropName.getText());
                grandTotal.setText(Integer.toString(endPrice));
                properties.remove(enterPropName.getText());
                bookConfirmation.setText("Thank you for booking!");
                enterPropName.clear();
                enterNights.clear();
            } else {
                bookConfirmation.setText("Please fill in all your details correctly.");
            }
        });

        clear.setOnAction(e -> {
            enterPropName.clear();
            enterNights.clear();
            grandTotal.setText(null);
            bookConfirmation.setText(null);
        });

        pane.setAlignment(Pos.CENTER);

        return pane;
    }
}
