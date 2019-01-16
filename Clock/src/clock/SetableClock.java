package clock;

import java.time.Instant;
import java.time.temporal.TemporalUnit;

public class SetableClock implements Clock {
    private Instant now;

    public SetableClock(Instant now) {
        setNow(now);
    }

    @Override
    public void setNow(Instant now) {
        this.now = now;
    }

    @Override
    public void plus(long amountToAdd, TemporalUnit unit) {
        setNow(getNow().plus(amountToAdd, unit));
    }

    @Override
    public void minus(long amountToSubtract, TemporalUnit unit) {
        setNow(getNow().minus(amountToSubtract, unit));
    }

    @Override
    public Instant getNow() {
        return now;
    }
}