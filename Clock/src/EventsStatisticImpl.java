import clock.Clock;

import java.time.Instant;
import java.util.*;

public class EventsStatisticImpl implements EventsStatistic {
    private Clock clock;
    private Map<String, Deque<Instant>> events;

    private void cleanup(String name, Instant now) {
        Deque<Instant> timestamps = events.get(name);
        while (!timestamps.isEmpty()
                && (now.getEpochSecond() - timestamps.peek().getEpochSecond()) > 3600) {
            timestamps.pop();
        }
    }


    public EventsStatisticImpl(Clock clock) {
        events = new HashMap<>();
        this.clock = clock;
    }

    public void incEvent(String name) {
        Instant now = clock.getNow();
        events.putIfAbsent(name, new LinkedList<>());
        events.get(name).add(now);
        cleanup(name, now);
    }

    public double getEventStatisticByName(String name) {
        if (!events.containsKey(name)) {
            return 0;
        }
        cleanup(name, clock.getNow());
        return (double)events.get(name).size() / 60;
    }

    public double getAllEventStatistic() {
        double result = 0d;
        for (String name: events.keySet()) {
            result += getEventStatisticByName(name);
        }
        return result;
    }

    public void printStatistic() {
        for (String name: events.keySet()) {
            System.out.println(name + ": " + getEventStatisticByName(name) + "rpm");
        }
    }
}