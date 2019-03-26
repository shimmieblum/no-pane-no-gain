import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javafx.collections.*;


public class AirbnbDataLoader {
 
    /** 
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     */
    public ArrayList<AirbnbListing> load() {
        System.out.print("Begin loading Airbnb london dataset...");
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                String neighbourhood = line[4];
                double latitude = convertDouble(line[5]);
                double longitude = convertDouble(line[6]);
                String room_type = line[7];
                int price = convertInt(line[8]);
                int minimumNights = convertInt(line[9]);
                int numberOfReviews = convertInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth = convertDouble(line[12]);
                int calculatedHostListingsCount = convertInt(line[13]);
                int availability365 = convertInt(line[14]);
 
                AirbnbListing listing = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                    );
                listings.add(listing);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }
    
    /**
     * Return an array list of all air bnb listings for one specific london borough.
     */
    public ObservableList<AirbnbListing> loadSpecific(String borough)
    {
        ArrayList<AirbnbListing> listings = load();
        ObservableList<AirbnbListing> listingsSpecific = FXCollections.observableArrayList();;
        int i = 0;
        //int count = 0;
        while (i < listings.size()) {
            if (listings.get(i).getNeighbourhood().replaceAll(" ", "").toLowerCase().equals(borough.toLowerCase())) {
                listingsSpecific.add(listings.get(i));
                i++;
                // count++;
            }
            else {
                i++;
            }
        }
        // System.out.println(borough);
        // System.out.println(count);
        return listingsSpecific;
    }
    
    /**
     * Return an array list of all air bnb listings for one specific london borough
     * but only those within a defined price range
     */
    public ObservableList<AirbnbListing> loadSpecific(String borough, int priceFrom, int priceTo)
    {
        ArrayList<AirbnbListing> listings = load();
        ObservableList<AirbnbListing> listingsSpecific = FXCollections.observableArrayList();;
        int i = 0;
        //int count = 0;
        while (i < listings.size()) {
            if (listings.get(i).getNeighbourhood().replaceAll(" ", "").toLowerCase().equals(borough.toLowerCase())
                && listings.get(i).getPrice() >= priceFrom
                && listings.get(i).getPrice() <= priceTo) {
                listingsSpecific.add(listings.get(i));
                i++;
                // count++;
            }
            else {
                i++;
            }
        }
        // System.out.println(borough);
        // System.out.println(count);
        return listingsSpecific;
    }
    
    /**
     * Return the number of properties in a specific london borough.
     */
    public int loadNumberOfProperties(String borough)
    //not sure if i ended up using this method
    {
        ArrayList<AirbnbListing> listings = load();
        ArrayList<AirbnbListing> listingsSpecific = new ArrayList<AirbnbListing>();
        int i = 0;
        int count = 0;
        while (i < listings.size()) {
            if (listings.get(i).getNeighbourhood().replaceAll(" ", "").toLowerCase().equals(borough.toLowerCase())) {
                listingsSpecific.add(listings.get(i));
                System.out.println(listings.get(i).getNeighbourhood());
                count = count + 1;
                i++;
            }
            else {
                i++;
            }
        }
        System.out.println(count);
        return count;
        
    }
    
    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }

}
