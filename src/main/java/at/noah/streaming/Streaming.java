package at.noah.streaming;

import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.*;

public class Streaming {

    public static void main(String[] args) {
        h();
    }
    public static int[] a() {
        return IntStream
                .range(1,21)
                .filter(value -> value%2==1)
                .map(operand -> (int) Math.pow(operand, 2))
                .toArray();
    }

    public static double b() {
        return IntStream
                .range(1,101)
                .mapToDouble(value -> value)
                .reduce(0.0, (left, right) -> left += 1.0/((right+1.0)*(right+2.0)));
    }

    public static int[] c() {
        return new Random()
                .ints(1, 46)
                .distinct()
                .limit(6)
                .sorted()
                .toArray();
    }

    public static long d() {
        return LongStream
                .range(1,21)
                .reduce(1, (left, right) -> left*right);
    }

    public static long e() {
        return LongStream
                .range(1,1001)
                .mapToObj(String::valueOf)
                .reduce((s, s2) -> s+s2)
                .orElseThrow(() -> new NoSuchElementException("No element found"))
                .chars()
                .filter(value -> value=='1')
                .count();
    }

    public static BigInteger f() {
        FactorialSupplier factorialSupplierTest = new FactorialSupplier();
        return Stream
                .iterate(factorialSupplierTest.get(), bigInteger -> factorialSupplierTest.get())
                .filter(bigInteger -> bigInteger.toString().length() > 20)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No big integer found"));
    }

    public static int g() {
        FactorialSupplier factorialSupplier = new FactorialSupplier();

        return Stream
                .generate(factorialSupplier)
                .filter(factorialNumber -> factorialNumber.compareTo(BigInteger.valueOf(10).pow(10000)) > 0)
                .findFirst()
                .map(bigInteger -> factorialSupplier.getI())
                .orElseThrow(() -> new NoSuchElementException("No Biginteger greater than 10e10000 found!"));
    }

    public static int h() {
        FibonacciSupplier fibonacciSupplier = new FibonacciSupplier();

        return Stream
                .generate(fibonacciSupplier)
                .filter(bigInteger -> bigInteger.toString().length() >= 1000)
                .findFirst()
                .map(bigInteger -> fibonacciSupplier.getIndex())
                .orElseThrow(() -> new NoSuchElementException("No element found"));
    }

}
