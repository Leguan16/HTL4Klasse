package at.noah.wahl.reworked.logger;

import at.noah.wahl.reworked.domain.Candidate;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.List;

public class DefaultElectionLogger implements ElectionLogger<Candidate> {

    private final File logFile;
    private final DecimalFormat decimalFormat = new DecimalFormat("000");
    private boolean mainVote = true;
    private int logEntryId = 0;

    public DefaultElectionLogger() {
        this.logFile = getLogFile();
        clearFile();
    }

    @Override
    public void printCandidates(List<Candidate> candidates) {
        clearFile();
        println("ID    Stand   Name");
        for (int i = 0; i < candidates.size(); i++) {
            println((i + 1) + "     " + candidates.get(i));
        }
        printInputLine();
    }

    @Override
    public void println(String message) {
        try {
            System.out.println(message);
            Files.writeString(logFile.toPath(), message + "\r\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    @Override
    public void print(String message) {
        try {
            System.out.print(message);
            Files.writeString(logFile.toPath(), message, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    @Override
    public void printInputLine() {

        println("-----------------------------------------------------------");

        if (getMainVote()) {
            print(decimalFormat.format(logEntryId) + " Main Vote (insert id to vote)> ");
        } else {
            print(decimalFormat.format(logEntryId) + " Second Vote (insert id to vote)> ");
        }
        logEntryId++;
    }

    @Override
    public File getLogFile() {
        return new File("./logFile.txt");
    }

    @Override
    public void clearFile() {
        try {
            new DataOutputStream(new FileOutputStream(logFile)).close();
        } catch (IOException e) {
            System.err.println("Error writing to file");
            System.exit(2);
        }
    }

    public int getMainOrSecondAsNumber() {
        return mainVote ? 1 : 0;
    }

    public boolean getMainVote() {
        return mainVote;
    }

    public void setMainVote(boolean mainVote) {
        this.mainVote = mainVote;
    }
}
