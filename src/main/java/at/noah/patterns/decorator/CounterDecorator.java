package at.noah.patterns.decorator;

public abstract class CounterDecorator implements Counter {

    private final Counter counter;

    public CounterDecorator(Counter counter) {
        this.counter = counter;
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
