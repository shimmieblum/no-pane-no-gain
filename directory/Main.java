import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AirbnbDataLoader loader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> list = loader.load();
        System.out.println(list.get(2));
    }
}
