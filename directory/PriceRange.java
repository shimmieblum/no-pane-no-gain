/**
 * this represents the price range the user is looking for
 */
public class PriceRange {
    private int minimum;
    private int maximum;

    public PriceRange() {
        minimum = 0;
        maximum = 999999999;
    }

    /**
     * set the minimum to a new value
     * @param minimum the new value
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * set the maximum to a new value
     * @param maximum the new value
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }


    /**
     * @return the maximum
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * @return the minimum
     */
    public int getMinimum() {
        return minimum;
    }
}
