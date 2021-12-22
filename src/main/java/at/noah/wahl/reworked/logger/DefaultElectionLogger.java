package at.noah.wahl.reworked.logger;

import at.noah.wahl.reworked.domain.Candidate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

public class DefaultElectionLogger implements ElectionLogger<Candidate> {

    private final File logFile;
    private boolean mainOrSecond = true;

    private final DecimalFormat decimalFormat = new DecimalFormat("000");

    public DefaultElectionLogger() {
        this.logFile = getLogFile();
    }

    @Override
    public void printCandidates(List<Candidate> candidates) {
        for (int i = 0; i < candidates.size(); i++) {
            print(i + 1 + "     " + candidates.get(i));
        }
        printInputLine();
    }

    @Override
    public void print(String message) {
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(logFile))) {
            System.out.println(message);
            fileWriter.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to file");
        }
    }

    @Override
    public void printInputLine() {
        print("-----------------------------------------------------------");
        int logEntryId = 0;
        if (mainOrSecond) {
            print(decimalFormat.format(logEntryId) + " Main Vote>");
        } else {
            print(decimalFormat.format(logEntryId) + " Second Vote>");
        }
    }

    @Override
    public File getLogFile() {
        return new File("./logFile");
    }

    public int getMainOrSecondAsNumber() {
        return mainOrSecond ? 1 : 2;
    }

    public boolean getMainOrSecond() {
        return mainOrSecond;
    }

    public void setMainOrSecond(boolean mainOrSecond) {
        this.mainOrSecond = mainOrSecond;
    }
}
