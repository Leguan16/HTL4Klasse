package at.noah.streaming;

import java.math.BigInteger;
import java.util.function.Supplier;

public class FibonacciSupplier implements Supplier<BigInteger> {

    private int index = 0;

    private BigInteger previous = BigInteger.ONE;
    private BigInteger next = BigInteger.ONE;

    @Override
    public BigInteger get() {
        index++;

        BigInteger fibonacci = previous;
        previous = next;
        next = fibonacci.add(previous);

        return fibonacci;
    }

    public int getIndex() {
        return index;
    }
}
