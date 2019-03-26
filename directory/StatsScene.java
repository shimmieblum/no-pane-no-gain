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

public class StatsScene extends SceneGenerator {
    private Scene statsScene;
    // the properties
    private Listings listings;
    // the root of the Scene
    private BorderPane root;
    // the object containing all statistics
    private Statistics stats;


    public StatsScene(Listings l){
        listings = l;
        stats  = new Statistics(listings);
        createScene();

    }

    /**
     * Set up the statistics scene using a Borderlayout. Top and bottom are configured in the superclass.
     */
    public void createScene() {
        root = new BorderPane();
        addCenterPane();
        statsScene = new Scene(root, 300,300);
    }


    /**
     * Add a FlowPane to the center of the root pane.
     */
    private void addCenterPane() {
        FlowPane centerPane = new FlowPane();
        centerPane.setPadding(new Insets(20,20,20,20));
        centerPane.setVgap(4);
        centerPane.setHgap(4);
        centerPane.setPrefWrapLength(600);

        centerPane.getChildren().addAll(createPane(), createPane(),createPane(),createPane() );
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);
    }

    /**
     * create BorderPane with  one statistic
     * @return the initial BorderPane with one statistic
     */
    public Pane createPane() {
        BorderPane pane = new StatPane(stats).getPane();
        return pane;
    }

}
