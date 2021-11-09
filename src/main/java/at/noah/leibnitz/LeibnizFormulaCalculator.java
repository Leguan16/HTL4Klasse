package at.noah.leibnitz;

import java.util.concurrent.Callable;

public record LeibnizFormulaCalculator(int from, int to) implements Callable<Double> {

    @Override
    public Double call() throws Exception {
        double result = 0.0;
        for (int i = from; i < to; i++) {
            result += Math.pow(-1, i) / (2*i+1);
        }

        return result*4;
    }
}
