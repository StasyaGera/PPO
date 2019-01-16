import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class StatisticsCollector {
    private PostCounter counter;

    StatisticsCollector(PostCounter counter) {
        this.counter = counter;
    }

    public ArrayList<Integer> collect(String hashtag, int hours) {
        ArrayList<Integer> result = new ArrayList<>();
        long startTime, endTime;
        Instant timestamp = ZonedDateTime.now().toInstant();
        for (int h = 0; h < hours; h++) {
            endTime = timestamp.minus(h, ChronoUnit.HOURS).getEpochSecond();
            startTime = timestamp.minus(h + 1, ChronoUnit.HOURS).getEpochSecond();

            result.add(counter.count(hashtag, startTime, endTime));
        }
        return result;
    }
}
