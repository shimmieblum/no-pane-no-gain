
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * An abstract class superclass for all the new Scenes, in order to keep them consistent.
 *
 * Each scene has a next and back button on the top. these buttons change the scene to display the
 * next scene or the previous scene. The correct scene is obtained from the variables
 * nextSceneGenerator and backSceneGenerator. These are set when the window is created and the first scene
 * is displayed by the MainWindow class.
 *
 * Furthermore the next button will only work if the condition is met. each scene can define its own
 * condition by overriding the ethod nextSceneConditionMet() which returns a boolean.
 *
 * The text of the 'next' and 'back' buttons can be changed using the nextText() and backText() methods.
 * By default the text is next and back, but different scenes may require changes to this. For example, mapScene
 * doesn't have a 'next' button, it will have a 'statistics button'
 *
 * The method createPane() must be implemented to return a Pane which is the main pane of the window.
 */
public abstract class SceneGenerator {
    // the uniform width and height of each scene.
    public static int SCENE_WIDTH = 800;
    public static int SCENE_HEIGHT = 600;
    // the generators for the next scene and the previous scene.
    private SceneGenerator nextSceneGenerator, backSceneGenerator;


    public SceneGenerator() {
        nextSceneGenerator = null;
        backSceneGenerator = null;
    }

    /**
     * this method is called to obtain the scene generated by this scene generator.
     *
     * the Stage must be passed as a parameter in order to allow the scene to be changed
     * when the next/previous buttons have been pushed.
     * @param window the Stage.
     * @return the Scene being created.
     */
    public Scene getScene(Stage window) {
        BorderPane root = new BorderPane();

        //set the top and bottom bars, and leave the centre for each subclass to create.
        root.setTop(topBar(window));
        root.setCenter(createPane());
        root.setBottom(bottomBar());
        root.setStyle("-fx-background-color: red");

        return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    }

    private Pane topBar(Stage window) {
        // the next and back buttons
        Button nextButton = new Button(nextText());
        Button backButton = new Button(backText());
        // on use, try to move to the next/ back scene.
        nextButton.setOnAction(actionEvent -> nextScene(window));

        backButton.setOnAction(actionEvent -> backScene(window));

        HBox buttons = new HBox();

        buttons.setSpacing(3);
        buttons.setAlignment(Pos.BASELINE_LEFT);
        buttons.setPadding(new Insets(5,5,5,5));
        buttons.getChildren().addAll(backButton, nextButton);

        return buttons;
    }

    public Pane bottomBar() {

        // create and size ImageViewer of logo
        Image logo = new Image(getClass().getResourceAsStream("Untitled - Copy.png"));
        ImageView view = new ImageView(logo);
        view.setFitHeight(100);
        view.setFitWidth(130);

        // create label with logo
        Label logoLabel = new Label();
        logoLabel.setGraphic(view);

        // create Label with small print
        Label smallPrint = new Label();
        smallPrint.setText("This programme is property of th the government of the United States of America." +
                "\nAny attemps to reproduce or copy this will result in a full on investigation and will probably " +
                "result in invasion." +
                "\nFor more info please call 999, and ask for agent P.");
        smallPrint.setFont(new Font(6));
        smallPrint.setTextAlignment(TextAlignment.CENTER);
        // create VBox to hold details of bottom label.
        VBox bottomBar = new VBox();
        bottomBar.getChildren().addAll(logoLabel, smallPrint);
        bottomBar.setAlignment(Pos.CENTER);
        return bottomBar;

    }


    private void nextScene(Stage window) {
        if (nextSceneGenerator != null & nextSceneConditionMet()) {
            window.setScene(nextSceneGenerator.getScene(window));

        }
    }

    private void backScene(Stage window) {
        if (backSceneGenerator !=null) {
            window.setScene(backSceneGenerator.getScene(window));
        }
    }

    /**
     * implement to check if the user can move on. unless this is overridden,
     * it will return true as it is presumed there are no requirements to meet.
     * @return If the user has fulfilled the conditions required for this scene,
     * return true. else return false.
     */
    public boolean nextSceneConditionMet() {
        return true;
    }

    /**
     * Set the text of the next button.
     * @return the text of the next button.
     */
    public String nextText() {
        return "NEXT";
    }

    /**
     * Set the text of the back button.
     * @return the text of the back button.
     */
    public String backText() {
        return "BACK";
    }


    /**
     * set the correct scene for the back scene and next scene.
     * @param nextSceneGenerator the generator to create the next scene.
     * @param backSceneGenerator the generator to create the previous scene.
     */
    public void setSceneGenerators(SceneGenerator nextSceneGenerator, SceneGenerator backSceneGenerator) {
        this.nextSceneGenerator = nextSceneGenerator;
        this.backSceneGenerator = backSceneGenerator;
    }

    /**
     * set the size of the text of a label.
     * @param size the new size
     * @param labeleds the labeled objects to be altered.
     */
    public void setTextSize(double size, Labeled... labeleds) {
        for (int i = 0; i < labeleds.length; i++) {
            Labeled label = labeleds[i];
            label.setFont(new Font(size));
        }
    }

    /**
     * implement this class to create the pane containing the main details of the window.
     */
    public abstract Pane createPane();


}