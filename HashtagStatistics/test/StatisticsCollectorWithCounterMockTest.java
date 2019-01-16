import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class StatisticsCollectorWithCounterMockTest {
    private StatisticsCollector collector;
    @Mock
    private PostCounter counter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        collector = new StatisticsCollector(counter);
    }

    @Test
    public void collect() {
        String hashtag = "makeup";
        Instant timestamp = ZonedDateTime.now().toInstant();

        long endTime = timestamp.getEpochSecond();
        long startTime = timestamp.minus(1, ChronoUnit.HOURS).getEpochSecond();
        when(counter.count(hashtag, startTime, endTime)).thenReturn(21);

        endTime = startTime;
        startTime = timestamp.minus(2, ChronoUnit.HOURS).getEpochSecond();
        when(counter.count(hashtag, startTime, endTime)).thenReturn(42);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(21);
        expected.add(42);
        ArrayList<Integer> collected = collector.collect(hashtag, 2);
        Assert.assertEquals(expected, collected);
    }
}
