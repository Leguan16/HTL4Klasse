package at.noah.wahl.reworked;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Wahl {
    private final List<Kandidat> candidates;
    private final File logFile;
    private int logEntryId;
    DecimalFormat decimalFormat = new DecimalFormat("000");

    public Wahl() throws IOException {
        candidates = new ArrayList<>();
        logFile = getLogFile().orElseThrow(() -> new FileNotFoundException("Could not get Logfile"));
    }

    private Optional<File> getLogFile() throws IOException {
        if (!Files.exists(Path.of("./logFile.txt"))) {
            return Optional.of(new File(String.valueOf(Files.createFile(Path.of("./logFile.txt")))));
        } else {
            return Optional.of(new File(String.valueOf(Path.of("./logFile.txt"))));
        }
    }

    public static void main(String[] args) throws Exception {
        Wahl wahl = new Wahl();
        wahl.addCandidates(
                new Kandidat("Dominik Hofmann"),
                new Kandidat("Kilian Prager"),
                new Kandidat("Niklas Hochstöger"),
                new Kandidat("Paul Pfiel"),
                new Kandidat("Raid Alarkhanov")
        );

        wahl.start();
    }

    private void addCandidates(Kandidat... candidates) {
        this.candidates.addAll(Arrays.stream(candidates).toList());
    }

    private void start() {
        logEntryId = 0;

        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(logFile));
             BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))){

            System.out.print(decimalFormat.format(logEntryId) + " >");
            fileWriter.print(decimalFormat.format(logEntryId) + " >");

            String input;

            while ((input = inputReader.readLine()) != null && !input.equals("quit")) {
                int ok = 0;
                int ul = 0;

                fileWriter.println(input);
                System.out.println(input);

                if (isValid(input)) {

                    if (input.charAt(0) == '-') {
                        ul++;
                        ok++;
                    }
                    if (input.charAt(1) == '-') {
                        ul++;
                        ok++;
                    }
                    if (ul > 1) ok = 0;

                    for (int a = 0; a < candidates.size(); a++) {
                        if (input.charAt(0) == candidates.get(a).firstChar()) {
                            candidates.get(a).addPoints(2);
                            ok += 1;
                        }
                        if (input.charAt(1) == candidates.get(a).firstChar()) {
                            candidates.get(a).addPoints(1);
                            ok += 1;
                        }
                    }

                }

                if (ok != 2) {
                    System.out.println("     Falsche Eingabe!");
                    fileWriter.println("     Falsche Eingabe!");
                } else {
                    logEntryId++;
                    for (int a = 0; a < candidates.size(); a++) {
                        System.out.println("    " + candidates.get(a));
                        fileWriter.println("     " + candidates.get(a));
                    }
                }
                System.out.println("-----------------------------------------------------------");
                fileWriter.println("-----------------------------------------------------------");
                fileWriter.flush();
                System.out.print(decimalFormat.format(logEntryId) + " >");
                fileWriter.print(decimalFormat.format(logEntryId) + " >");
            }
        } catch (IOException ioe) {
            System.err.println("Error: " + ioe.getMessage());
        }

    }

    boolean isValid(String s) {
        if (s.length() < 2) return false;
        char ch1 = s.charAt(0);
        char ch2 = s.charAt(1);
        int test1 = 0, test2 = 0;
        if (s.length() != 2) return false;
        if (ch1 == ch2) return false;
        for (int i = 0; i < candidates.size(); i++) {
            if (ch1 == candidates.get(i).firstChar()) test1++;
            if (ch2 == candidates.get(i).firstChar()) test2++;
        }
        if (ch1 == '-') test1++;
        if (ch2 == '-') test2++;
        if (test1 != 1 || test2 != 1) return false;

        return true;

    }

    private void printCanditates() {

    }


}
