import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed.
 */
public class Viewer {
    private ViewerGUI gui;     // the Graphical User Interface
    private Listings listings;

    //field to keep track of currently viewed property
    private AirbnbListing currentProperty;
    //the ID of the currently viewed property
    private int currentID;
    //creates a list of all viewed properties
    private List<AirbnbListing> viewed;

    /**
     * Create a PropertyViewer and display its GUI on screen.
     */
    public Viewer()
    {
        gui = new ViewerGUI(this);
        listings = new Listings();

        //assigns the viewed list to an empty ArrayList
        viewed = new ArrayList<>();

        //initialise to first property
        currentID = 0;
        showProperty(currentID);


    }
    /**
     * Updates and displays the currently shown property according to the id. Implemented as helper function as it is often reused.
     * @param id: the ID of the desired property.
     */
    public void showProperty(int id) {
        //update the current property to the desired ID property.
        currentProperty = listings.getProperty(id);

        //always shows the current ID.
        gui.showID(currentProperty);

        //displays the property in the GUI.
        gui.showProperty(currentProperty);

        //adds the currently viewed property to the list of all viewed properties.
        viewed.add(currentProperty);

    }

    /**
     * Updates the current property to the next one and displays it.
     */
    public void nextProperty()
    {
        //increments the property ID by 1 (unless it is the last property) and then displays the new property.

        if(currentID < listings.numberOfProperties()){
            currentID++;
            showProperty(currentID);
        }else{ System.out.println("Last property.");
        }
    }

    /**
     * Update the current property to the previous one and displays it.
     */
    public void previousProperty()
    {
        //decrements the property ID by 1 (unless it is the first property) and then displays the new property.

        if(currentID > 0){
            currentID--;
            showProperty(currentID);
        }else{ System.out.println("First property.");
        }
    }

    //----- methods for challenge tasks -----

    /**
     * This method opens the system's default internet browser
     * The Google maps page should show the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
        //get updated latitude and longitude from current property.
        double latitude = currentProperty.getLatitude();
        double longitude = currentProperty.getLongitude();

        URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri);
    }

    /**
     * Uses the list length as the number of properties viewed.
     */
    public int getNumberOfPropertiesViewed()
    {
        return viewed.size();
    }

    /**
     * Iterates over all properties in the list and sums their prices in a total variable.
     * @return: the total sum divided by the total number of properties viewed to get the average.
     */
    public int averagePropertyPrice()
    {
        int totalPrice = 0;

        for(int i=0; i<viewed.size(); i++){
            totalPrice += viewed.get(i).getPrice();
        }

        return totalPrice/(getNumberOfPropertiesViewed());
    }
}
