package at.noah.patterns;

import at.noah.patterns.decorator.BaseCounter;
import at.noah.patterns.decorator.Counter;

public class DecoratorMain {

    public static void main(String[] args) {
        Counter counter = new BaseCounter(7);

        while (true) {
            System.out.println(counter.tick().read());
        }
    }
}
