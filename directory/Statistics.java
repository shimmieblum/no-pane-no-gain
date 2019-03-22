import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {

    private Listings listings;
    private AirbnbListing currentListing;
    private Map<String, Object> stats;


    public Statistics(Listings l, AirbnbListing c) {
        listings = l;
        currentListing = c;
        stats = new HashMap<>();
        createStats();
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
        for (int i = 0; i < listings.getNumberOfProperties(); i++) {
            totalReviews += listings.getProperty(i).getNumberOfReviews();
        }
        avgNumberReviews = totalReviews/listings.getNumberOfProperties();
        stats.put("Average Reviews Per Property", avgNumberReviews);
    }

    // adds the number of total available properties to the stats HashMap
    public void totalAvailableProperties() {
        stats.put("Total Available Properties", listings.getNumberOfProperties());
    }

    // adds the number of entire homes and apartments to the stats HashMap
    public void numberHomeApartments() {
        int totalHomeApartments = 0;
        for (int i = 0; i < listings.getNumberOfProperties(); i++) {
            if (listings.getProperty(i).getRoom_type().equals("Entire Home") || listings.getProperty(i).getRoom_type().equals("apt")) {
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
        for (int i = 0; i < listings.getNumberOfProperties(); i++) {
            if (listings.getProperty(i).getNeighbourhood().equals(currentListing.getNeighbourhood())) {
                boroughTotalPrice += (currentListing.getMinimumNights()*currentListing.getPrice());
            }
            // find largest total price
            for (int n = 0; n <= boroughTotalPrice; n++) {
                if (n > largestTotalPrice) {
                    largestTotalPrice = n;
                }
            }
            stats.put("Total Available Properties", listings.getProperty(i).getNeighbourhood());
        }
    }

    // adds the String of the name of the least expensive borough to the stats HashMap
    public void leastExpensiveBorough() {
        int boroughTotalPrice = 0;
        int smallestTotalPrice = 0;
        // find total price of properties in a borough
        for (int i = 0; i < listings.getNumberOfProperties(); i++) {
            if (listings.getProperty(i).getNeighbourhood().equals(currentListing.getNeighbourhood())) {
                boroughTotalPrice += (currentListing.getMinimumNights()*currentListing.getPrice());
            }
            // find smallest total price
            for (int n = 0; n <= boroughTotalPrice; n++) {
                if (n < smallestTotalPrice) {
                    smallestTotalPrice = n;
                }
            }
            stats.put("Total Available Properties", listings.getProperty(i).getNeighbourhood());
        }
    }

    // adds the String of the name of host with the most reviews to the stats HashMap
    // calculatedHostListingsCount in AirbnbListing returns the total number of listings the host holds across AirBnB
    public void mostReviewedHost() {
        int greatestNumberOfReviews = 0;
        for (int i = 0; i < listings.getNumberOfProperties(); i++) {
            for (int n = 0; n <= listings.getProperty(i).getNumberOfReviews(); n++){
                if (n > greatestNumberOfReviews) {
                    greatestNumberOfReviews = n;
                }
            }
            stats.put("Most Reviewed Host", listings.getProperty(i).getHost_name());
        }
    }

    // adds the String of the name of the most expensive property to the stats HashMap
    public void mostExpensiveProperty() {
        int propertyTotalPrice = 0;
        int mostExpensiveProperty = 0;
        for (int i = 0; i < listings.getNumberOfProperties(); i++) {
            propertyTotalPrice = (listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice());
            for (int n = 0; n <= propertyTotalPrice; n++){
                if (n > mostExpensiveProperty) {
                    mostExpensiveProperty = n;
                }
            }
            stats.put("Most Expensive Property", listings.getProperty(i).getName());
        }
    }

    // adds the String of the name of the least expensive property to the stats HashMap
    public void leastExpensiveProperty() {
        int propertyTotalPrice = 0;
        int leastExpensiveProperty = 0;
        for (int i = 0; i < listings.getNumberOfProperties(); i++) {
            propertyTotalPrice = (listings.getProperty(i).getMinimumNights()*listings.getProperty(i).getPrice());
            for (int n = 0; n <= propertyTotalPrice; n++){
                if (n < leastExpensiveProperty) {
                    leastExpensiveProperty = n;
                }
            }
            stats.put("Least Expensive Property", listings.getProperty(i).getName());
        }
    }

    public Map<String, Object> getStats() {
        return stats;
    }
}
