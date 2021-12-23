package at.noah.patterns.decorator;

import java.nio.file.Path;

public class LogCounter extends CounterDecorator {

    private Path logFile;

    public LogCounter(Counter counter, Path path) {
        super(counter);
    }
}