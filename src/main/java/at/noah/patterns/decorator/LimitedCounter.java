package at.noah.patterns.decorator;

public class LimitedCounter implements Counter {

    private final int limit;

    public LimitedCounter(Counter counter, int limit) {
        this.limit = limit;
    }

    @Override
    public int read() {
        return 0;
    }

    @Override
    public Counter tick() {
        return null;
    }
}
