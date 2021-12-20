package at.noah.wahl.reworked;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.*;

public class Wahl {
    private final List<Candidate> candidates;
    private final File logFile;
    private int logEntryId;
    private boolean mainOrSecond = true;

    DecimalFormat decimalFormat = new DecimalFormat("000");

    public Wahl() throws IOException {
        candidates = new ArrayList<>();
        logFile = getLogFile().orElseThrow(() -> new FileNotFoundException("Could not get Logfile"));
    }

    public Optional<File> getLogFile() throws IOException {
        if (!Files.exists(Path.of("./logFile.txt"))) {
            return Optional.of(new File(String.valueOf(Files.createFile(Path.of("./logFile.txt")))));
        } else {
            return Optional.of(new File(String.valueOf(Path.of("./logFile.txt"))));
        }
    }

    public void addCandidates(Candidate... candidates) {
        this.candidates.addAll(Arrays.stream(candidates).toList());
    }

    public void start() throws IOException {
        logEntryId = 0;

        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(logFile));
             BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))){

            printCanditates(fileWriter);
            String input;

            while (!"quit".equalsIgnoreCase(input = inputReader.readLine())) {
                print(input, fileWriter);

                int id = getNumber(input);

                if (id != -1 && id <= candidates.size()) {
                    addPoints(candidates.get(id));
                    logEntryId++;
                    printCanditates(fileWriter);
                    mainOrSecond = !mainOrSecond;
                } else {
                    print("     Falsche Eingabe!", fileWriter);
                }

                print("-----------------------------------------------------------", fileWriter);
                fileWriter.flush();
                printInputLine(fileWriter);
            }
        }
    }

    private void addPoints(Candidate candidate) {
        if (mainOrSecond) {
            candidate.addPoints(2);
        } else {
            candidate.addPoints(1);
        }
    }

    private int getNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private void print(String message, PrintWriter fileWriter) {
        System.out.println(message);
        fileWriter.println(message);
    }

    private void printCanditates(PrintWriter fileWriter) {
        for (int i = 0; i < candidates.size(); i++) {
            print(i+1 + "     " + candidates.get(i), fileWriter);
        }
        printInputLine(fileWriter);
    }

    private void printInputLine(PrintWriter fileWriter) {
        if (mainOrSecond) {
            print(decimalFormat.format(logEntryId) + " Main Vote>", fileWriter);
        } else {
            print(decimalFormat.format(logEntryId) + " Second Vote>", fileWriter);
        }
    }
}
