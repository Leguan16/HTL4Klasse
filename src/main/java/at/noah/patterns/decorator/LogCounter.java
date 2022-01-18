package at.noah.patterns.decorator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LogCounter extends CounterDecorator {

    private Path logFile;

    public LogCounter(Counter counter, Path path) throws IOException {
        super(counter);
        createLogFile(path);
    }

    private void createLogFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            logFile = Files.createFile(path);
        } else if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("provided path is a directory");
        } else {
            logFile = path;
        }
    }

    @Override
    public Counter tick() {
        try {
            super.tick();
            Files.writeString(logFile, "tick()", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("could not write to file");
        }
        return this;
    }

    @Override
    public int read() {
        try {
            Files.writeString(logFile, "read() = " + super.read(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("could not write to file");
        }
        return super.read();
    }
}