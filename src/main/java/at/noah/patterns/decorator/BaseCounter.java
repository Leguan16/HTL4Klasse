package at.noah.patterns.decorator;

public class BaseCounter implements Counter {

    private final int base;

    public BaseCounter(int base) {
        this.base = base;
    }

    public int read() {
        return 0;
    }

    private int convertToBase(int n) {
        return 0;
    }

    public Counter tick() {
        return null;
    }
}
