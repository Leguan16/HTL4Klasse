package at.noah.patterns.decorator;

public class LimitedCounter extends CounterDecorator implements Counter {

    private final int limit;

    public LimitedCounter(Counter counter, int limit) {
        super(counter);
        this.limit = limit;
    }

    @Override
    public int read() {
        return Math.min(getCounter().read(), limit);
    }

    @Override
    public Counter tick() {
        getCounter().tick();
        return this;
    }
}
