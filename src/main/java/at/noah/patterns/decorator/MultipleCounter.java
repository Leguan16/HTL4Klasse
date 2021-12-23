package at.noah.patterns.decorator;

public class MultipleCounter extends CounterDecorator {

    private int multiple;
    private int tickCallsSinceLastSuperTick = 0;

    public MultipleCounter(Counter counter, int multiple) {
        super(counter);
    }
}
