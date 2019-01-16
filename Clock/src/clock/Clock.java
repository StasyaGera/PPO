package clock;

import java.time.Instant;
import java.time.temporal.TemporalUnit;

public interface Clock {
    Instant getNow();
    void setNow(Instant now);
    void plus(long amountToAdd, TemporalUnit unit);
    void minus(long amountToSubtract, TemporalUnit unit);
}
