import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a scene which is the statistic scene of the application. It displays different statistics about the data.
 *
 * @Author Judith Offenberg
 */

public class StatsScene extends SceneGenerator{
    // the properties
    private Listings listings;
    // the root of the Scene
    private BorderPane root;
    // the object containing all statistics
    private Statistics stats;

    
    public StatsScene(Listings l){
        listings = l;
        stats  = new Statistics(listings);
        createPaneDetails();

    }

    /**
     * Set up the statistics scene using a Borderlayout. Top and bottom are configured in the superclass.
     */
    public void createPaneDetails() {
        root = new BorderPane();
        addCenterPane();
    }


    /**
     * Add a FlowPane to the center of the root pane.
     */
    private void addCenterPane() {
        FlowPane centerPane = new FlowPane();
        centerPane.setPadding(new Insets(4,4,4,4));
        centerPane.setVgap(4);
        centerPane.setHgap(4);

        centerPane.getChildren().addAll(createStatsPane(), createStatsPane(),createStatsPane(),createStatsPane() );
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);
    }

    /**
     * create BorderPane with  one statistic
     * @return the initial BorderPane with one statistic
     */
    private BorderPane createStatsPane() {
        BorderPane pane = new StatPane(stats).getPane();
        return pane;
    }


    /**
     * @return the welcomeScene created by this Class
     */
    public Pane createPane() {
        return root;
    }

}
