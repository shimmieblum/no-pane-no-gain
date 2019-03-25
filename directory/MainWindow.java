
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
        window.setTitle("Property-Properly");
        window.setOnCloseRequest(e -> {
            e.consume();
            close();
        });
        // create each Scene with its own class.
        Scene welcomeScene = createSceneGenerators(listings);
        // the user opens onto the welcome scene.
        setScene(welcomeScene);
        // show the scene
        window.show();
    }

    private Scene createSceneGenerators(Listings listings) {
        SceneGenerator welcomeScene, mapScene, statsScene, challengeScene;
        welcomeScene = new WelcomeScene();
        // mapScene = new MapScene(); TODO
        statsScene = new StatsScene(listings);
        // challengeScene = new ChallengeScene(); TODO

       /* welcomeScene.setSceneGenerators(mapScene, null);
        mapScene.setSceneGenerators(null, welcomeScene);  TODO */
        return welcomeScene.getScene(window);
    }

    /**
     * set scene to the a new scene scene.
     */
    public void setScene(Scene scene) {
        window.setScene(scene);
    }

    private void close() {
        boolean close = OptionsBox.createAlertBox("want to quit", "Do you really want to quit?");
        if (close) {
            window.close();
        }

    }


}
