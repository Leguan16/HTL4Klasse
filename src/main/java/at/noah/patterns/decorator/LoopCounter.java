package at.noah.patterns.decorator;

public class LoopCounter implements Counter {

    private int[] values;

    public LoopCounter(int... values) {
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
