package at.noah.leibnitz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    static ExecutorService service;

    public static void main(String[] args) {
        service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<Double>> futureResults = createThreads();
        double finalResult = calculateResult(futureResults);

        service.shutdown();
        System.out.println(finalResult);
    }

    private static List<Future<Double>> createThreads() {
        List<Future<Double>> listToReturn = new ArrayList<>();
        for (int i = 0; i <= 10000000; i += 10000) {
            listToReturn.add(service.submit(new LeibnizFormulaCalculator(i, i + 10000)));
        }

        return listToReturn;
    }

    private static double calculateResult(List<Future<Double>> list) {
        return list
                .stream()
                .mapToDouble(value -> {
                    try {
                        return value.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    return 0.0;
                }).sum();
    }
}
