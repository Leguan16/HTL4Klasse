package at.noah.patterns.decorator;

public class MultipleCounter extends CounterDecorator {

    private int multiple;
    private int tickCallsSinceLastSuperTick = 0;

    public MultipleCounter(Counter counter, int multiple) {
        super(counter);

        if (multiple < 1) {
            throw new IllegalArgumentException("multiple must not be lower then 1");
        }

        this.multiple = multiple;
    }

    @Override
    public Counter tick() {
        /*tickCallsSinceLastSuperTick++;
        if (tickCallsSinceLastSuperTick == multiple) {
            super.tick();
            tickCallsSinceLastSuperTick = 0;
        }
        */

        if (tickCallsSinceLastSuperTick == multiple - 1) {
            tickCallsSinceLastSuperTick = 0;
            super.tick();
        } else {
            tickCallsSinceLastSuperTick++;
        }

        return this;
    }

    @Override
    public int read() {
        return super.read();
    }
}
