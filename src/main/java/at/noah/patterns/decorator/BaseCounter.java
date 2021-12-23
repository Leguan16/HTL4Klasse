package at.noah.patterns.decorator;

public class BaseCounter implements Counter {

    private final int base;
    private int counter = 0;

    public BaseCounter(int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base can only be between 2 or 10");
        }

        this.base = base;
    }

    @Override
    public int read() {
        return convertToBase(counter);
    }

    private int convertToBase(int n) {
        String number = Integer.toString(n, base);
        return Integer.parseInt(number);
    }

    @Override
    public Counter tick() {
        counter++;

        return this;
    }
}
