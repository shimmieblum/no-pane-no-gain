import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A file of Listings is a collection of properties. It reads properties from a file from the data loader,
 * and it can be used to retrieve single properties.
 *
 * The data to be read is loaded from AirbnbDataLoader.
 *
 */
public class Listings
{
    private ArrayList<AirbnbListing> properties;
    private AirbnbDataLoader dataLoader;

    /**
     * Constructor for objects of class Listings
     */
    public Listings()
    {
        dataLoader = new AirbnbDataLoader();
        properties = dataLoader.load();
    }

    /**
     * Return a property from this file of listings.
     */
    public AirbnbListing getProperty(int propertyNumber)
    {
        return properties.get(propertyNumber);
    }

    /**
     * Return the number of Properties in this file of listings.
     */
    public int numberOfProperties()
    {
        return properties.size();
    }

    /**
     * Return an ArrayList containing the rows in the AirBnB London data set csv file.
     */
    public ArrayList<AirbnbListing> loadProperties() {
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
                int availability365 = convertInt(line[10]);

                AirbnbListing currentProperty = new AirbnbListing(id, name, host_id, host_name,
                        neighbourhood, latitude,longitude, room_type, price,
                        minimumNights, 0, "N/A",0.0, 0, availability365);
                listings.add(currentProperty);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong when loading the property file");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
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
