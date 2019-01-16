import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> collectedInfo = new StatisticsCollector(new VKPostCounter()).collect("beautiful", 4);
        for (Integer c: collectedInfo) {
            System.out.println(c);
        }
    }
}
