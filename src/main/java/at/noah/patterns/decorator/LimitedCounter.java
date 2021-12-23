package at.noah.patterns.decorator;

public class LimitedCounter extends CounterDecorator implements Counter {

    private final int limit;
    private int count;

    public LimitedCounter(Counter counter, int limit) {
        super(counter);
        this.limit = limit;
    }

    @Override
    public int read() {
        return super.getCounter().read();
    }

    @Override
    public Counter tick() {
        if (count < limit) {
            super.getCounter().tick();
            count++;
        }
        return super.getCounter();
    }
}
