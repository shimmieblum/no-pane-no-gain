import javafx.scene.layout.Pane;

public abstract class PaneGenerator {
    private PriceRange range;

    public PaneGenerator() {
        range = null;
    }

    /**
     * @return the range of this PaneGenerator.
     */
    public PriceRange getRange() {
        return range;
    }

    /**
     * set the range by inputting the min and max values.
     * @param min minimum price
     * @param max maximum price
     */
    public void setRange(int min, int max) {
        range = new PriceRange();
        range.setMaximum(max);
        range.setMinimum(min);
    }

    /**
     * set a condition to be met before the next scene can be brought up.
     * by default, returns true; override when desired.
     * @return true if the condition is met, false if it is not.
     */
    public boolean nextConditionMet() {
        return true;
    }

    /**
     * get the pane created by this paneGenerator;
     * the range is passed in so the range variable can be set to it if so desired.
     * @param range the range that has been created thus far.
     */
    public abstract Pane getPane(PriceRange range);

}
