import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

/**
 * Creates a scene which is the statistic scene of the application. It displays different statistics about the data.
 *
 * @Author Judith Offenberg
 */

public class StatsPane extends PaneGenerator {
    // the properties
    private Listings listings;
    // the root of the Scene
    private BorderPane root;
    // the object containing all statistics
    private Statistics stats;


    public StatsPane(Listings l){
        listings = l;
        stats  = new Statistics(listings);
    }


    /**
     * create and return the pane that will go in the centre of the screen
     * @return the main pane of this scene
     */
    public Pane getPane(PriceRange range) {
        FlowPane centerPane = new FlowPane();
        centerPane.setPadding(new Insets(20,20,20,20));
        centerPane.setVgap(4);
        centerPane.setHgap(4);
        centerPane.setPrefWrapLength(600);

        centerPane.getChildren().addAll(createStatsPane(), createStatsPane(),createStatsPane(), createStatsPane());
        centerPane.setAlignment(Pos.CENTER);
        return centerPane;
    }

    /**
     * create BorderPane with  one statistic
     * @return the initial BorderPane with one statistic
     */
 public Pane createStatsPane() {
     BorderPane pane = new IndividualStatPane(stats).getPane();
     return pane;
 }

}
