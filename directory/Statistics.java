import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import java.util.Dictionary;

/**
 * The class computing all the calculations and storing them in the stats HashMap by name and figure.
 * This class is used in StatPane and StatsScene.
 * It consists of all the statistics in "stats" and then stores the keys of those displayed or undisplayed statistics
 * in lists.
 *
 * @Author Judith Offenberg, Mi-kaela Marciales
 */

public class Statistics {

    private Listings listings;
    //all the statistics
    private Map<String, Object> stats;

    // the keys of currently displayed statistics - always 4
    private List<String> usedStats;
    //the keys of currently unused statistics - always 4
    private List<String> unusedStats;


    public Statistics(Listings l) {
        listings = l;
        stats = new HashMap<>();
        createStats();
        createStatGroups();
    }

    private void createStats() {
        //call all methods
        averageReviewsPerProperty();
        totalAvailableProperties();
        numberHomeApartments();
        mostExpensiveBorough();
        leastExpensiveBorough();
        mostReviewedProperty();
        mostExpensiveProperty();
        leastExpensiveProperty();
    }

    // adds the average reviews per property to the stats HashMap
    public void averageReviewsPerProperty() {
        int totalReviews = 0;
        int avgNumberReviews = 0;

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            totalReviews += listings.getProperty(i).getNumberOfReviews();
        }
        avgNumberReviews = totalReviews/listings.numberOfProperties();
        stats.put("Average Reviews Per Property", avgNumberReviews);
    }

    // adds the number of total available properties to the stats HashMap
    public void totalAvailableProperties() {
        stats.put("Total Available Properties", listings.numberOfProperties());
    }

    // adds the number of entire homes and apartments to the stats HashMap
    public void numberHomeApartments() {
        int totalHomeApartments = 0;

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            if (listings.getProperty(i).getRoom_type().equals("Entire Home/apt")) {
                totalHomeApartments++;
            }
        }
        stats.put("Number of Entire Homes and Apartments", totalHomeApartments);
    }

    // adds the String of the name of the most expensive borough to the stats HashMap
    public void mostExpensiveBorough() {
        int boroughTotalPrice = 0;
        int largestTotalPrice = 0;
        String mostExpensiveNeighbourhood = "";
        Dictionary neighbourhood = new Hashtable();
        Dictionary boroughTotal = new Hashtable();

        // add to neighbourhood dictionary key (property borough) and value (property price)
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            neighbourhood.put(listings.getProperty(i).getNeighbourhood(), listings.getProperty(i).getPrice());
        }
        // if a specific property's borough is the same as the key in the neighbourhood dictionary, add the property's
        // total minimum price to the borough's total price.
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            if (listings.getProperty(i).getNeighbourhood().equals(neighbourhood.get(listings.getProperty(i).getNeighbourhood()))) {
                boroughTotalPrice += listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice();
                boroughTotal.put(boroughTotalPrice, listings.getProperty(i).getNeighbourhood());
                // find largest total price
                if(boroughTotalPrice > largestTotalPrice){
                    largestTotalPrice = boroughTotalPrice;
                    mostExpensiveNeighbourhood = boroughTotal.get(largestTotalPrice).toString();
                }
            }
        }
        stats.put("Most Expensive Borough", mostExpensiveNeighbourhood);
        }

    // adds the String of the name of the least expensive borough to the stats HashMap
    public void leastExpensiveBorough() {
        int boroughTotalPrice = 0;
        int smallestTotalPrice = 0;
        String leastExpensiveNeighbourhood = "";
        Dictionary neighbourhood = new Hashtable();
        Dictionary boroughTotal = new Hashtable();

        // add to neighbourhood dictionary key (property borough) and value (property price)
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            neighbourhood.put(listings.getProperty(i).getNeighbourhood(), listings.getProperty(i).getPrice());
        }
        // if a specific property's borough is the same as the key in the neighbourhood dictionary, add the property's
        // total minimum price to the borough's total price.
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            if (listings.getProperty(i).getNeighbourhood().equals(neighbourhood.get(listings.getProperty(i).getNeighbourhood()))) {
                boroughTotalPrice += listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice();
                boroughTotal.put(boroughTotalPrice, listings.getProperty(i).getNeighbourhood());
                // find smallest total price
                if(boroughTotalPrice < smallestTotalPrice){
                    smallestTotalPrice = boroughTotalPrice;
                    leastExpensiveNeighbourhood = boroughTotal.get(smallestTotalPrice).toString();
                }
            }
        }
        stats.put("Least Expensive Borough", leastExpensiveNeighbourhood);
    }

    // adds the String of the name of host with the most reviews to the stats HashMap
    // calculatedHostListingsCount in AirbnbListing returns the total number of listings the host holds across AirBnB
    public void mostReviewedProperty() {
        int greatestNumberOfReviews = 0;
        String mostReviewedProperty = "";

        Dictionary propertiesReviews = new Hashtable();
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            propertiesReviews.put(listings.getProperty(i).getNumberOfReviews(), listings.getProperty(i).getName());
            if (listings.getProperty(i).getNumberOfReviews() > greatestNumberOfReviews) {
                greatestNumberOfReviews = listings.getProperty(i).getNumberOfReviews();
                mostReviewedProperty = propertiesReviews.get(greatestNumberOfReviews).toString();
            }
        }
        stats.put("Most Reviewed Property", mostReviewedProperty);

    }

    // adds the String of the name of the most expensive property to the stats HashMap
    public void mostExpensiveProperty() {
        int propertyTotalPrice;
        int mostCostlyProperty = 0;
        String mostExpensiveProperty = "";
        Dictionary propertiesPrices = new Hashtable();

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            // check this
            propertyTotalPrice = listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice();
            propertiesPrices.put(propertyTotalPrice, listings.getProperty(i).getName());
            if (propertyTotalPrice > mostCostlyProperty) {
                mostCostlyProperty = propertyTotalPrice;
                mostExpensiveProperty = propertiesPrices.get(mostCostlyProperty).toString();
            }
        }
        stats.put("Most Expensive Property", mostExpensiveProperty);
    }

    // adds the String of the name of the least expensive property to the stats HashMap
    public void leastExpensiveProperty() {
        int propertyTotalPrice;
        int leastCostlyProperty = 0;
        String leastExpensiveProperty = "";
        Dictionary propertiesPrices = new Hashtable();

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            // check this
            propertyTotalPrice = listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice();
            propertiesPrices.put(propertyTotalPrice, listings.getProperty(i).getName());
            if (propertyTotalPrice < leastCostlyProperty) {
                leastCostlyProperty = propertyTotalPrice;
                leastExpensiveProperty = propertiesPrices.get(leastCostlyProperty).toString();
            }
        }
        stats.put("Most Expensive Property", leastExpensiveProperty);
    }

    /*
        Returns all the statistics in one HashMap.
        @return the hashmap containing statistics.
     */
    public Map<String, Object> getStats() {
        return stats;
    }

    /*
        Divides the statistics into two groups, one of which is displayed and one which is waiting to be shown.
     */
    private void createStatGroups() {
        usedStats = new ArrayList<>();
        unusedStats = new ArrayList(stats.keySet());

    }

    /*
        Returns the next statistic to be displayed. It can be used to initialise a pane.
        @param currentStat the current statistic that needs to be changed. Null if there is no previous stat.
        @return the new Pair of String and Object to be displayed on the screen.
    */
    public Pair<String, Object> nextStat(String currentStat) {
        Pair<String, Object> pair;

        if(currentStat != null) unusedStats.add(currentStat); //inserts the current statistic into back of unused list

        String next = unusedStats.get(0); //gets the first unused stat
        unusedStats.remove(next);
        usedStats.add(next); // adds the new statistic into the used list
        pair = new Pair<>(next, stats.get(next));
        return pair;
    }
}
