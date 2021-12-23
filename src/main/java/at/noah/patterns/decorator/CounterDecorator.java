package at.noah.patterns.decorator;

public abstract class CounterDecorator implements Counter {

    private final Counter counter;

    public CounterDecorator(Counter counter) {
        if (counter == null) {
            throw new IllegalArgumentException("Underlying counter must not be null!");
        }
        this.counter = counter;
    }

    @Override
    public int read() {
        return counter.read();
    }

    @Override
    public Counter tick() {
        return counter.tick();
    }

    public Counter getCounter() {
        return counter;
    }
}
