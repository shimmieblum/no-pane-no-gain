import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * this class simply extends the JavaFX Application class and allows for the creation of four
 * separate scenes to be placed in the stage. These scenes are created in their own classes.
 *
 */
public class MainWindow extends Application {
    // the properties
    private Listings listings;

    // the stage
    private Stage window;
    // 4 Scenes to show 4 different displays.
    private Scene welcomeScene, mapScene, statsScene, challengeScene;

    public MainWindow(String[] args) {
        launch(args);
    }

    /**
     * start the Application off, this method is called on construction by the
     * launch method in the constructor
     * @param stage the stage.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        listings = new Listings();

        window = stage;
        // create each Scene with its own class.
        welcomeScene = new WelcomeScene(listings).getScene();
        mapScene = new MapScene(listings).getScene();
        statsScene = new StatsScene(listings).getScene();
        challengeScene = new ChallengeScene(listings).getScene();
        // the user opens onto the welcome scene.
        setWelcomeScene();
        // show the scene
        window.show();
    }

    /**
     * set scene to the welcome scene.
     */
    public void setWelcomeScene() {
        window.setScene(welcomeScene);
    }

    /**
     * set scene to the map scene
     */
    public void setMapScene(){
        window.setScene(mapScene);
    }

    /**
     * set the scene to the stats scene
     */
    public void setStatsScene(){
        window.setScene(statsScene);
    }

    /**
     * set the scene to the challenge scene.
     */
    public void setChallengeScene() {
        window.setScene(challengeScene);
    }

}
