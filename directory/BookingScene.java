import com.sun.jdi.IntegerValue;
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
 *  Creates a scene which is the booking scene of the application. It displays the booking page where the user can book a
 *  property from the available listings.
 *
 *  @Author Mi-kaela Marciales
 */

public class BookingScene extends SceneGenerator {
    private Scene bookScene;
    // the properties
    private Listings listings;
    // arrayList of loaded properties
    private AirbnbDataLoader loader;
    // the root of the Scene
    private BorderPane root;
    // variable for total price of booked property
    private int endPrice;


    public BookingScene(Listings l){
        super();
        listings = l;
        createPane();
    }

    /**
     *  Build the main pane and return it to be used to build the scene.
     *  @return the pane created.
     */
    public Pane createPane() {
        root = new BorderPane();
        root.setCenter(centerPane());
        root.setTop(topPane());
        bookScene = new Scene(root, 300,300);
        return root;
    }


    /**
     *  Create the pane to go in the center of the window.
     *  @return the center pane created.
     */
    private Pane centerPane() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
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
         *  When the user clicks the BOOK button, generate the grand total price
         *  and remove the property from available listings.
         *  If text is not filled in properly, return an error statement.
         */
        for(int i = 0; i < listings.numberOfProperties(); i++) {
            HashMap<String, Integer> propertyPrices = new HashMap<>();
            propertyPrices.put(listings.getProperty(i).getName(), listings.getProperty(i).getPrice());
            int enteredNights = Integer.parseInt(enterNights.getText());

            book.setOnAction(e -> {
                if (enterPropName.getText() != null && !enterPropName.getText().isEmpty()
                        && enterNights.getText() != null && !enterNights.getText().isEmpty()) {
                    endPrice = enteredNights*propertyPrices.get(enterPropName.getText());
                    //loader.load.remove(10);
                    bookConfirmation.setText("Thank you for booking!");
                } else {
                    bookConfirmation.setText("Please enter your details.");
                }
            });
        }

        pane.setGridLinesVisible(true);

        pane.setAlignment(Pos.CENTER);

        return pane;
    }

    /**
     *  Create the pane to go at the top of the window.
     *  @return the top pane created.
     */
    private Pane topPane()
    {
        TilePane pane = new TilePane();

        Label title = new Label("Booking");
        pane.getChildren().add(title);
        pane.setAlignment(Pos.CENTER);

        return pane;
    }

}
