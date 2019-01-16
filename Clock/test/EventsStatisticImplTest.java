import clock.Clock;
import clock.SetableClock;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.junit.Assert.*;


public class EventsStatisticImplTest {
    private Clock clock;
    private EventsStatistic statistic;

    private final double EPS = 1e-5;
    private String[] events = {"A", "B", "C", "D"};

    @Before
    public void setUp() {
        clock = new SetableClock(Instant.now().minus(70, ChronoUnit.MINUTES));
        statistic = new EventsStatisticImpl(clock);

        // 1:10 ago
        statistic.incEvent(events[0]);

        clock.plus(10, ChronoUnit.MINUTES);

        // 1:00 ago
        for (int i = 0; i < 60; i++) {
            statistic.incEvent(events[1]);
            statistic.incEvent(events[1]);
            statistic.incEvent(events[2]);
            clock.plus(1, ChronoUnit.MINUTES);
        }
        // 0:00 ago
    }

    @Test
    public void getEventStatisticByName() {
        double resA = statistic.getEventStatisticByName(events[0]);
        assertTrue("More than an hour ago", Math.abs(resA) < EPS);

        double resB = statistic.getEventStatisticByName(events[1]);
        assertTrue("Wrong result for event B", Math.abs(resB - 2.0) < EPS);

        double resC = statistic.getEventStatisticByName(events[2]);
        assertTrue("Wrong result for event C", Math.abs(resC - 1.0) < EPS);

        double resD = statistic.getEventStatisticByName(events[3]);
        assertTrue("Wrong result for event D", Math.abs(resD) < EPS);
    }

    @Test
    public void getAllEventStatistic() {
        double res = statistic.getAllEventStatistic();
        assertTrue("Wrong result for all events", Math.abs(res - 3.0) < EPS);
    }

    @Test
    public void printStatistic() {
        System.out.println("Print statistic:");
        statistic.printStatistic();
    }
}