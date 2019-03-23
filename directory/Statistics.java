import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private AirbnbListing currentListing;
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
        mostReviewedHost();
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
            if (listings.getProperty(i).getRoom_type().equals("Entire Home")
                    || listings.getProperty(i).getRoom_type().equals("apt")) {
                totalHomeApartments++;
            }
        }
        stats.put("Number of Entire Homes and Apartments", totalHomeApartments);
    }

    // adds the String of the name of the most expensive borough to the stats HashMap
    public void mostExpensiveBorough() {
        /* 1. find sum of all properties in each borough (taking into
         * account minimum number of nights)
         * 2. compare sums and find most expensive boroughs
         */
        int boroughTotalPrice = 0;
        int largestTotalPrice = 0;
        // find total price of properties in a borough
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            if (listings.getProperty(i).getNeighbourhood().equals(currentListing.getNeighbourhood())) {
                boroughTotalPrice += (currentListing.getMinimumNights()*currentListing.getPrice());
            }
            // find largest total price
            for (int n = 0; n <= boroughTotalPrice; n++) {
                if (n > largestTotalPrice) {
                    largestTotalPrice = n;
                }
            }
            stats.put("Total Available Properties", listings.getProperty(i).getNeighbourhood()); //TODO can't be inside the loop (but also have u tested its functionality?)
            //maybe create local data structure to store this instead? TODO same for other loop ones
        }
    }

    // adds the String of the name of the least expensive borough to the stats HashMap
    public void leastExpensiveBorough() {
        int boroughTotalPrice = 0;
        int smallestTotalPrice = 0;
        // find total price of properties in a borough
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            if (listings.getProperty(i).getNeighbourhood().equals(currentListing.getNeighbourhood())) {
                boroughTotalPrice += (currentListing.getMinimumNights()*currentListing.getPrice());
            }
            // find smallest total price
            for (int n = 0; n <= boroughTotalPrice; n++) {
                if (n < smallestTotalPrice) {
                    smallestTotalPrice = n;
                }
            }
            stats.put("Total Available Properties", listings.getProperty(i).getNeighbourhood()); //TODO can't be inside the loop (but also have u tested its functionality?)
        }
    }

    // adds the String of the name of host with the most reviews to the stats HashMap
    // calculatedHostListingsCount in AirbnbListing returns the total number of listings the host holds across AirBnB
    public void mostReviewedHost() {
        int greatestNumberOfReviews = 0;
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            for (int n = 0; n <= listings.getProperty(i).getNumberOfReviews(); n++){
                if (n > greatestNumberOfReviews) {
                    greatestNumberOfReviews = n;
                }
            }
            stats.put("Most Reviewed Host", listings.getProperty(i).getHost_name()); //TODO can't be inside the loop (but also have u tested its functionality?)
        }
    }

    // adds the String of the name of the most expensive property to the stats HashMap
    public void mostExpensiveProperty() {
        int propertyTotalPrice = 0;
        int mostExpensiveProperty = 0;
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            propertyTotalPrice = (listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice());
            for (int n = 0; n <= propertyTotalPrice; n++){
                if (n > mostExpensiveProperty) {
                    mostExpensiveProperty = n;
                }
            }
            stats.put("Most Expensive Property", listings.getProperty(i).getName()); //TODO can't be inside the loop (but also have u tested its functionality?)
        }
    }

    // adds the String of the name of the least expensive property to the stats HashMap
    public void leastExpensiveProperty() {
        int propertyTotalPrice = 0;
        int leastExpensiveProperty = 0;
        for (int i = 0; i < listings.numberOfProperties(); i++) {
            propertyTotalPrice = (listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice());
            for (int n = 0; n <= propertyTotalPrice; n++){
                if (n < leastExpensiveProperty) {
                    leastExpensiveProperty = n;
                }
            }
            stats.put("Least Expensive Property", listings.getProperty(i).getName()); //TODO can't be inside the loop (but also have u tested its functionality?)
        }
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
