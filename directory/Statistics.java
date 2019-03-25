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
    private HashMap<String, String> stats;

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
        String avgReviews = "";

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            totalReviews += listings.getProperty(i).getNumberOfReviews();
        }
        avgNumberReviews = totalReviews/listings.numberOfProperties();
        avgReviews = Integer.toString(avgNumberReviews);

        stats.put("Average Reviews Per Property", avgReviews);
    }

    // adds the number of total available properties to the stats HashMap
    public void totalAvailableProperties() {
        stats.put("Total Available Properties", Integer.toString(listings.numberOfProperties()));
    }

    // adds the number of entire homes and apartments to the stats HashMap
    public void numberHomeApartments() {
        int totalHomeApartments = 0;
        String homeApts = "";

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            if (listings.getProperty(i).getRoom_type().equals("Entire home/apt")) {
                totalHomeApartments++;
            }
        }
        homeApts = Integer.toString(totalHomeApartments);

        stats.put("Number of Entire Homes and Apartments", homeApts);
    }

    // adds the String of the name of the most expensive borough to the stats HashMap
    public void mostExpensiveBorough() {
        String mostExpensiveNeighbourhood = "";
        HashMap<String, Integer> boroughTotal = new HashMap<>();
        Pair<String, Integer> mostExp = new Pair<>("", 0);

        for (int i = 0; i < listings.numberOfProperties(); i++) {
           String borough = listings.getProperty(i).getNeighbourhood();
           int price = listings.getProperty(i).getPrice()*listings.getProperty(i).getMinimumNights();
           if(boroughTotal.containsKey(borough)) price += boroughTotal.get(borough);
           boroughTotal.put(borough, price);
        }

        for(String borough : boroughTotal.keySet()) {
            int price = boroughTotal.get(borough);
            if(price > mostExp.getValue()) mostExp = new Pair<>(borough, boroughTotal.get(borough));
        }
        mostExpensiveNeighbourhood = mostExp.getKey();

        stats.put("Most Expensive Borough", mostExpensiveNeighbourhood);
    }

    // adds the String of the name of the least expensive borough to the stats HashMap
    public void leastExpensiveBorough() {
        String leastExpensiveNeighbourhood = "";
        HashMap<String, Integer> boroughTotal = new HashMap<>();
        Pair<String, Integer> leastExp = new Pair<>("", 1000000);

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            String borough = listings.getProperty(i).getNeighbourhood();
            int price = listings.getProperty(i).getPrice()*listings.getProperty(i).getMinimumNights();
            if(boroughTotal.containsKey(borough)) price += boroughTotal.get(borough);
            boroughTotal.put(borough, price);
        }

        for(String borough : boroughTotal.keySet()) {
            int price = boroughTotal.get(borough);
            if(price < leastExp.getValue()) leastExp = new Pair<>(borough, boroughTotal.get(borough));
        }
        leastExpensiveNeighbourhood = leastExp.getKey();

        stats.put("Least Expensive Borough", leastExpensiveNeighbourhood);
    }

    // adds the String of the name of host with the most reviews to the stats HashMap
    public void mostReviewedHost() {
        String mostReviewedHost = "";
        HashMap<String, Integer> hostsReviews = new HashMap<>();
        Pair<String, Integer> mostRev = new Pair<>("", 0);

        for (int i = 0; i < listings.numberOfProperties(); i++) {
            String host = listings.getProperty(i).getHost_name();
            int numReviews = listings.getProperty(i).getNumberOfReviews();
            if (hostsReviews.containsKey(host)) numReviews += hostsReviews.get(host);
            hostsReviews.put(host, numReviews);
        }

        for (String host : hostsReviews.keySet()) {
            int numReviews = hostsReviews.get(host);
            if(numReviews > mostRev.getValue()) mostRev = new Pair<>(host, hostsReviews.get(host));
        }
        mostReviewedHost = mostRev.getKey();

        stats.put("Most Reviewed Host", mostReviewedHost);

    }

    // adds the String of the name of the most expensive property to the stats HashMap
    public void mostExpensiveProperty() {
        String mostExpensiveProperty = "";
        Pair<String, Integer> mostExp = new Pair<>("", 0);

        for(int i = 0; i < listings.numberOfProperties(); i++) {
            AirbnbListing property = listings.getProperty(i);
            int totalPrice = property.getMinimumNights() * property.getPrice();
            if(totalPrice > mostExp.getValue()) mostExp = new Pair<>(property.getName(), totalPrice);
        }
        mostExpensiveProperty = mostExp.getKey();


        stats.put("Most Expensive Property", mostExpensiveProperty);
    }

    // adds the String of the name of the least expensive property to the stats HashMap
    public void leastExpensiveProperty() {
        String leastExpensiveProperty = "";
        Pair<String, Integer> leastExp = new Pair<>("", 10000000);

        for(int i = 0; i < listings.numberOfProperties(); i++) {
            AirbnbListing property = listings.getProperty(i);
            int totalPrice = property.getMinimumNights() * property.getPrice();
            if(totalPrice < leastExp.getValue()) leastExp = new Pair<>(property.getName(), totalPrice);
        }
        leastExpensiveProperty = leastExp.getKey();

        stats.put("Least Expensive Property", leastExpensiveProperty);
    }

    /*
        Returns all the statistics in one HashMap.
        @return the hashmap containing statistics.
     */
    public HashMap<String, String> getStats() { return stats; }

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
        @return the new Pair of String and String to be displayed on the screen.
    */
    public Pair<String, String> nextStat(String currentStat) {
        Pair<String, String> pair;

        if(currentStat != null) unusedStats.add(currentStat); //inserts the current statistic into back of unused list

        String next = unusedStats.get(0); //gets the first unused stat
        unusedStats.remove(next);
        usedStats.add(next); // adds the new statistic into the used list
        pair = new Pair<>(next, stats.get(next));
        return pair;
    }
}
