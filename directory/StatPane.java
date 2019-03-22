import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

public class StatPane {

    private Statistics stats;
    private BorderPane pane;
    private Label title;
    private Label content;
    private Button nextStat;
    private Button previousStat;

    public StatPane(Statistics s) {
        pane = new BorderPane();
        pane.setTop(title);
        pane.setCenter(content);
        pane.setLeft(previousStat);
        pane.setRight(nextStat);

        stats = s;
        Pair<String, Object> pair = s.nextStat(null);
        title = new Label(pair.getKey());
        content = new Label(pair.getValue().toString());
        instantiateButtons();
    }

    private void instantiateButtons() {
        nextStat = new Button(">");
        nextStat.setOnAction(e -> switchStatPane());

        previousStat = new Button("<");
        previousStat.setOnAction(e -> switchStatPane());
    }

    private void switchStatPane() {
        //currently switched statistic as param or object?
        Pair<String, Object> entry = stats.nextStat(title.getText());

        title  = new Label(entry.getKey());
        content = new Label(entry.getValue().toString());
    }

    private void changePane(String l, Object c) {
        title = new Label(l);
        content = new Label(c.toString());
    }

    public BorderPane getPane() {
        return pane;
    }
}
