import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import rules.HostReachableRule;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@HostReachableRule.HostReachable("api.vk.com")
public class VKPostCounterTest {
    @ClassRule
    public static final HostReachableRule rule = new HostReachableRule();

    @Test
    public void count() {
        Instant timestamp = ZonedDateTime.now().toInstant();
        long endTime = timestamp.getEpochSecond();
        long startTime = timestamp.minus(10, ChronoUnit.HOURS).getEpochSecond();

        int result = new VKPostCounter().count("autumn", startTime, endTime);
        Assert.assertTrue(result >= 0);
    }
}
