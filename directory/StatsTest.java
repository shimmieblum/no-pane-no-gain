import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class is designated to testing the Statistics class.
 *
 * @Author Judith Offenberg
 */

public class StatsTest {

    public static void main(String[] args) {
        Listings listings = new Listings();
        Statistics statistics = new Statistics(listings);

        //check correctness of stats
        System.out.println("\n Stat values: \n");

        HashMap<String, String> stats = statistics.getStats();
        for(String s : stats.keySet()) {
            System.out.print(s + ": ");

            //error messaged if unassigned or unchanged after initialisation
            if(stats.get(s).equals(null) || stats.get(s).equals("")){
                System.out.print( "\n Inconsistent value. Needs fix \n");
            } else{ //otherwise display value
                System.out.println(stats.get(s));
            }
        }

        //check correctness of stat transitions
        System.out.println("\n Stat transitions: \n");

        Random generator = new Random();
        Object[] keys = stats.keySet().toArray();
        Object randomKey = keys[generator.nextInt(keys.length)];
        //generate first random statistic name
        String stat = (String) keys[generator.nextInt(keys.length)];
        System.out.println(stat);

        Set<String> panes = new HashSet<>(); //should hold only one of each stat

        for(int i = 0; i< stats.size(); i++) {
            stat = statistics.nextStat(stat).getKey();
            //if this statistic was already computed before a full cycle, display error message
            System.out.println(stat);
            if(panes.contains(stat)) {
                System.out.println( "\n Statistic recomputed too soon. Needs fix \n");
                break;
            }
            panes.add(stat);

        }
    }

}
