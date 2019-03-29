import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

/**
 * Represents one statistics pane on the statistics page. Used in StatsPane.
 * This consists of the name of the statistic at the top, the figure in the middle and two arrows, left and right,
 * to load the next statistic.
 *
 * @Author Judith Offenberg
 */

public class IndividualStatPane {

    private Statistics stats;
    private BorderPane pane;

    private Label title;
    private Label content;

    private Button nextStat;
    private Button previousStat;

    public IndividualStatPane(Statistics s) {
        pane = new BorderPane();
        pane.setPrefSize(200, 200);
        pane.setPadding(new Insets(20,20,20,20));

        stats = s;
        Pair<String, String> pair = s.nextStat(null);

        title = new Label(pair.getKey());
        content = new Label(pair.getValue());
        content.setWrapText(true);

        instantiateButtons();

        pane.setTop(title);
        pane.setCenter(content);
        pane.setLeft(previousStat);
        pane.setRight(nextStat);
        pane.setAlignment(pane.getTop(), Pos.CENTER);

        pane.setStyle("-fx-border-color: white");
        pane.setStyle("-fx-background-color: skyblue");
    }

    /*
        Called when pane is first created. This creates and configures the buttons.
     */
    private void instantiateButtons() {
        nextStat = new Button(">");
        nextStat.setOnAction(e -> switchStatPane());

        previousStat = new Button("<");
        previousStat.setOnAction(e -> this.switchStatPane());
    }

    /*
        Called when a button is pressed. This loads a new statistic into the current pane.
     */
    private void switchStatPane() {
        Pair<String, String> pair = stats.nextStat(title.getText());

        title.setText(pair.getKey());
        content.setText(pair.getValue());
    }

    /*
        Returns the pane object.
        @return returns the pane object.
     */
    public BorderPane getPane() {
        return pane;
    }
}
