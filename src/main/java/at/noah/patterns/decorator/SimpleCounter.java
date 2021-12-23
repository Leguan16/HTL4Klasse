package at.noah.patterns.decorator;

public class SimpleCounter implements Counter {

    public SimpleCounter() {
    }

    public SimpleCounter(int count) {

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
