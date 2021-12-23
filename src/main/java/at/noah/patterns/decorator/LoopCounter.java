package at.noah.patterns.decorator;

public class LoopCounter implements Counter {

    private final int[] values;
    private int count;

    public LoopCounter(int... values) {
        if (values == null || values.length == 0)
            throw new IllegalArgumentException();
        this.values = values;
        this.count = 0;
    }

    @Override
    public int read() {
        return values[count];
    }

    @Override
    public Counter tick() {
        int index = count % values.length;
        if (index + 1 > values.length-1) {
            count = 0;
        } else {
            count++;
        }
        return this;
    }
}
