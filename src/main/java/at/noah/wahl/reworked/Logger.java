package at.noah.wahl.reworked;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class Logger {
    private final File logFile;
    private boolean mainOrSecond = true;

    private final DecimalFormat decimalFormat = new DecimalFormat("000");

    public Logger() throws IOException {
        this.logFile = getLogFile().orElseThrow(FileNotFoundException::new);
    }

    public void print(String message) {
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(logFile))) {
            System.out.println(message);
            fileWriter.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    void printCanditates(List<Candidate> candidates) {
        for (int i = 0; i < candidates.size(); i++) {
            print(i + 1 + "     " + candidates.get(i));
        }
        printInputLine();
    }

    public void printInputLine() {
        int logEntryId = 0;
        if (mainOrSecond) {
            print(decimalFormat.format(logEntryId) + " Main Vote>");
        } else {
            print(decimalFormat.format(logEntryId) + " Second Vote>");
        }
    }

    public Optional<File> getLogFile() throws IOException {
        if (!Files.exists(Path.of("./logFile.txt"))) {
            return Optional.of(new File(String.valueOf(Files.createFile(Path.of("./logFile.txt")))));
        } else {
            return Optional.of(new File(String.valueOf(Path.of("./logFile.txt"))));
        }
    }

    public void setMainOrSecond(boolean mainOrSecond) {
        this.mainOrSecond = mainOrSecond;
    }

    public boolean getMainOrSecond() {
        return mainOrSecond;
    }
}
