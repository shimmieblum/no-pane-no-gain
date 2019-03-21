import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {

    private Listings listings;
    private Map<String, Object> stats;


    public Statistics(Listings l) {
        listings = l;
        stats = new HashMap<>();
        createStats();
    }

    //Mikaela: we need to fill the DST stats with 8 statistics, each needs to store the data (like total or borough or whatever)
    // and the other the name of the statistic (a string) - so List is not good, maybe HashMap<String, Object> or something else?

    private void createStats() {
        //call all methods
        averageReviewsPerProperty();
        totalAvailableProperties();
        // TODO etc.
    }


    public void averageReviewsPerProperty() {
        //TODO eg:
        // stats.add("avg etc", int );
    }

    public int totalAvailableProperties() {
        //TODO
    }

    public int numberHomeApartments() {
        //TODO
    }

    public String mostExpensiveBorough() {
        //TODO
    }

    //TODO 4 additional statistics

    public Map<String, Object> getStats() {
        return stats;
    }
}
