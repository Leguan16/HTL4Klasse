package at.noah.patterns.decorator;

public class SkipCounter extends CounterDecorator {

    private int skip;

    public SkipCounter(Counter counter, int skip) {
        super(counter);
        if (skip < 0) {
            throw new IllegalArgumentException("skip cant be negative");
        }

        this.skip = skip;
    }

    @Override
    public Counter tick() {
        for (int i = 0; i <= skip; i++) {
            super.tick();
        }
        return this;
    }
}
