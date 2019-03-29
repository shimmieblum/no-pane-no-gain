
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * this class simply extends the JavaFX Application class and allows for the creation of four
 * separate scenes to be placed in the stage. These scenes are created in their own classes.
 */
public class MainWindow extends Application {

    // the properties
    private Listings listings;

    // the stage
    private Stage window;

    // the scene.
    private Scene scene;

    // the root pane
    private BorderPane root;

    // the PaneGenerator currently in use in the Array of PaneGenerators
    private int currentPaneIndex;

    // the list of PaneGenerators in order.
    private PaneGenerator[] paneList;


    // the width and height of the scene.
    public static int SCENE_WIDTH = 800;
    public static int SCENE_HEIGHT = 600;


    /**
     * construct a main window.
     */
    public MainWindow(){}

    public static void main(String[] args) {
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

        paneList = new PaneGenerator[10];
        currentPaneIndex = 0;
        fillPaneList();

        window = stage;
        window.setTitle("Property-Properly");
        window.setOnCloseRequest(e -> {
            e.consume();
            close();
        });
        scene = getScene();

        window.setScene(scene);
        window.show();
    }

    /**
     * fill the pane list with an ordered array of panes to be navigated between.
     */
    private void fillPaneList() {
        paneList[0] = new WelcomePane();
        paneList[1] = new MapPane();
        paneList[2] = new StatsPane(listings);
        paneList[3] = new BookingPane(listings);
    }

    /**
     * this method is called to obtain the scene generated by this scene generator.
     *
     * the Stage must be passed as a parameter in order to allow the scene to be changed
     * when the next/previous buttons have been pushed.
     * @return the Scene being created.
     */
    public Scene getScene() {
        root = new BorderPane();

        //set the top and bottom bars, and leave the centre for each subclass to create.
        root.setTop(topBar());
        setCenterPane(paneList[0].getPane(null));
        root.getCenter().setStyle("-fx-background-color: skyblue");
        root.setBottom(bottomBar());
        root.setStyle("-fx-background-color: red");

        return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    }

    /**
     * create the top bar with buttons 'next' and 'back'.
     * these buttons can be disabled if a condition isn't met
     * @return
     */
    private Pane topBar() {
        // the next and back buttons
        Button nextButton = new Button("NEXT");
        Button backButton = new Button("BACK");
        // on use, try to move to the next/ back scene.
        nextButton.setOnAction(actionEvent -> nextScene());

        backButton.setOnAction(actionEvent -> backScene());

        HBox buttons = new HBox();

        buttons.setSpacing(3);
        buttons.setAlignment(Pos.BASELINE_LEFT);
        buttons.setPadding(new Insets(5,5,5,5));
        buttons.getChildren().addAll(backButton, nextButton);

        return buttons;
    }

    /**
     * set the bottom bar with logo and text - common to all.
     * @return
     */
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
        smallPrint.setText("This programme is property of the the government of the United States of America." +
                "\nAny attempts to reproduce or copy this will result in a full on investigation and will probably " +
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

    /**
     * move to the next scene
     */
    private void nextScene() {
        if (paneList[currentPaneIndex + 1] != null && paneList[currentPaneIndex].nextConditionMet()) {
            int i = currentPaneIndex;
            PaneGenerator previousGenerator = paneList[i];
            currentPaneIndex++;
            setCenterPane(paneList[currentPaneIndex].getPane(previousGenerator.getRange()));
        }
    }

    /**
     * move to the back scene.
     */
    private void backScene() {
        if(currentPaneIndex >
                0) {
            int i = currentPaneIndex;
            PaneGenerator previousGenerator = paneList[i];
            currentPaneIndex--;
            setCenterPane(paneList[currentPaneIndex].getPane(previousGenerator.getRange()));
        }
    }

    /**
     * set centre pane with a pane.
     * @param pane
     */
    private void setCenterPane(Pane pane) {
        root.setCenter(pane);
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
    public String setNextText(String text) {
        return text;
    }

    /**
     * Set the text of the back button.
     * @return the text of the back button.
     */
    public String setBackText(String text) {
        return text;
    }

    /**
     * offer option to not to close.
     */
    private void close() {
        boolean close = OptionsBox.createAlertBox("want to quit", "Do you really want to quit?");
        if (close) {
            window.close();
        }

    }

    /**
     * set the size of the text of a label.
     * @param size the new size
     * @param labeleds the labeled objects to be altered.
     */
    public static void setTextSize(double size, Labeled... labeleds) {
        for (int i = 0; i < labeleds.length; i++) {
            Labeled label = labeleds[i];
            label.setFont(new Font(size));
        }
    }

}
