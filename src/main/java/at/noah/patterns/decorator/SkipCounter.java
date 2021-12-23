package at.noah.patterns.decorator;

public class SkipCounter extends CounterDecorator {

    private int skip;

    public SkipCounter(Counter counter, int skip) {
        super(counter);
    }
}
