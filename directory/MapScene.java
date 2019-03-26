import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.collections.*;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

/**
 * A class to display a map of london boroughs, each borough can be clicked
 * on to launch a second scene which displays a table containing information
 * about that boroughs airbnb listings.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MapScene extends SceneGenerator
{
    boolean filtered = false;
    AirbnbDataLoader airbnbDataLoader = new AirbnbDataLoader();
    
    @FXML
    private TextField priceFrom;
    
    @FXML
    private TextField priceTo;

    public MapScene()

    /**
     * The loading of the map scene from fxml document
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        URL url = getClass().getResource("map.fxml");
        Pane root = FXMLLoader.load(url);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        Scene scene = new Scene (scrollPane);

        stage.setTitle("MapScene of London boroughs");
        stage.setHeight(700);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    
    /**
     * The action to be taken when one of the buttons that indicate a borough is
     * clicked.
     */
    @FXML
    private void Click(ActionEvent e)
    { 
        //first get the ID of the pressed button to determine the borough
        Button button = (Button) e.getSource();
        String id = button.getId();
        //check if the list should display within a price range
        if (filtered == false) {
            //add the listings in that borough to an observable list so it can be displayed
            ObservableList<AirbnbListing> listings = airbnbDataLoader.loadSpecific(id);
            try {
                launchTableViewer(listings);
            }
            catch (Exception exception) {
                System.out.println("error opening window");
            }
        }
        else {
            //filter the list to only display within the bounds of the
            //selected price
            String firstPrice = priceFrom.getText();
            String secondPrice = priceTo.getText();
            int price1 = 0;
            int price2 = 100000;
            if (!firstPrice.equals("")) {
                price1 = Integer.parseInt(firstPrice);
            }
            if (!secondPrice.equals("")) {
                price2 = Integer.parseInt(secondPrice);
            }
            ObservableList<AirbnbListing> listings = airbnbDataLoader.loadSpecific(id, price1, price2);
            try {
                launchTableViewer(listings);
            }
            catch (Exception exception) {
                System.out.println("error opening window");
            }
        }
    }

    /**
     * launches a new window that contains a table view that holds all of the selected
     * boroughs available airbnb properties.
     */
    private void launchTableViewer(ObservableList<AirbnbListing> listings)
    {

        // Create the table view and its columns and assign their correct data
        TableView tableView = new TableView();
        
        TableColumn columnHost = new TableColumn("Name of Host");
        columnHost.setMinWidth(150);
        columnHost.setCellValueFactory(
                new PropertyValueFactory<AirbnbListing, String>("host_name"));
                
        TableColumn columnPrice = new TableColumn("Property price");
        columnPrice.setMinWidth(150);
        columnPrice.setCellValueFactory(
                new PropertyValueFactory<AirbnbListing, String>("price"));
                
        TableColumn columnStayLength = new TableColumn("Minimum length of stay");
        columnStayLength.setMinWidth(150);
        columnStayLength.setCellValueFactory(
                new PropertyValueFactory<AirbnbListing, String>("minimumNights"));
        
        TableColumn columnReviews = new TableColumn("Number of reviews");
        columnReviews.setMinWidth(150);
        columnReviews.setCellValueFactory(
                new PropertyValueFactory<AirbnbListing, String>("numberOfReviews"));
        
        tableView.getColumns().addAll(columnHost, columnPrice, columnStayLength, columnReviews);
        tableView.setItems(listings);
        //lambda to complete an event when the table is clicked on.
        tableView.setOnMouseClicked(e -> {tableSelect(tableView);});
        
        
        ObservableList<AirbnbListing> selectedListings;
        selectedListings = tableView.getSelectionModel().getSelectedItems();                                                                   // });
        
        //create the pane to hold the table
        BorderPane root = new BorderPane();

        // Add the table into the pane
        root.setCenter(tableView);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setTitle("Borough of: " + listings.get(0).getNeighbourhood());
        stage.setScene(scene);

        // Show the Stage (window)
        stage.showAndWait();
    }
    
    /**
     * method to determine first whether a row has been selected in the table
     * and if so display the description (name) of the property in a new window.
     */
    private void tableSelect(TableView tableView)
    {
       ObservableList<AirbnbListing> selectedListing = tableView.getSelectionModel().
                                                       getSelectedItems();    
       if(selectedListing.size() == 1) {
            launchPropertyDescription(selectedListing);
            //makes sure that the window is not displayed when sorting table
            tableView.getSelectionModel().clearSelection();
       }
    }
    
    /**
     * method to launch a new window containing a brief description of the selected
     * property
     */
    private void launchPropertyDescription(ObservableList<AirbnbListing> selectedListing)
    {
       Label myLabel = new Label(selectedListing.get(0).getName());
       StackPane root = new StackPane();
       root.getChildren().addAll(myLabel);
       
       Stage stage = new Stage();
       stage.initModality(Modality.APPLICATION_MODAL);
       Scene scene = new Scene(root, 500, 100);
       stage.setTitle("Description of " + selectedListing.get(0).getHost_name()
                       + "'s property");
       stage.setScene(scene);

       // Show the Stage (window)
       stage.showAndWait();
    }
    
    /**
     * method to indicate that the list of airbnb properties should be filtered
     */
    @FXML
    private void filterClick(ActionEvent e)
    {
        String firstPrice = priceFrom.getText();
        String secondPrice = priceTo.getText();
        if(firstPrice.equals("") && secondPrice.equals("")) {
            System.out.println("Please enter an amount to filter");
            filtered = false;
        }
        else if (!firstPrice.equals("")  && secondPrice.equals("")) {
            System.out.println("Showing properties starting from £" +
                                firstPrice);
            filtered = true;
        }
        else if (firstPrice.equals("")  && !secondPrice.equals("")) {
            System.out.println("Showing properties up to £" +
                                secondPrice);
            filtered = true;
        }
        else if (!firstPrice.equals("") && !secondPrice.equals("")) {
            System.out.println("Showing properties starting from £" +
                                firstPrice + " up to £" + secondPrice);
            filtered = true;
        }
    }
}
