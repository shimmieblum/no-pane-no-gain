import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

/**
 * Represents one statistics pane on the statistics page. Used in StatsScene.
 * This consists of the name of the statistic at the top, the figure in the middle and two arrows, left and right,
 * to load the next statistic.
 *
 * @Author Judith Offenberg
 */

public class StatPane {

    private Statistics stats;
    private BorderPane pane;
    private Label title;
    private Label content;
    private Button nextStat;
    private Button previousStat;

    public StatPane(Statistics s) {
        pane = new BorderPane();
        pane.setPrefSize(250, 250);
        pane.setPadding(new Insets(20,20,20,20));

        stats = s;
        Pair<String, String> pair = s.nextStat(null);

        title = new Label(pair.getKey());
        content = new Label(pair.getValue());
        instantiateButtons();

        pane.setTop(title);
        pane.setCenter(content);
        pane.setLeft(previousStat);
        pane.setRight(nextStat);
    }

    /*
        Called when pane is first created. This creates and configures the buttons.
     */
    private void instantiateButtons() {
        nextStat = new Button(">");
        nextStat.setOnAction(e -> switchStatPane());

        previousStat = new Button("<");
        previousStat.setOnAction(e -> switchStatPane());
    }

    /*
        Called when a button is pressed. This loads a new statistic into the current pane.
     */
    private void switchStatPane() {
        Pair<String, String> pair = stats.nextStat(title.getText());

        title  = new Label(pair.getKey());
        content = new Label(pair.getValue().toString());
    }

    /*
        Returns the pane object.
        @return returns the pane object.
     */
    public BorderPane getPane() {
        return pane;
    }
}
