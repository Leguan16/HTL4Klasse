package at.noah.wahl.reworked.domain;

import at.noah.wahl.reworked.logger.DefaultElectionLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Election {
    private final List<Candidate> candidates;

    private final DefaultElectionLogger logger;

    private final LinkedList<Vote> history = new LinkedList<>();

    public Election() {
        candidates = new ArrayList<>();
        logger = new DefaultElectionLogger();
    }

    public void addCandidates(Candidate... candidates) {
        this.candidates.addAll(Arrays.stream(candidates).toList());
    }

    public void start() throws IOException {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {

            logger.printCandidates(candidates);
            String input;

            while (!"quit".equalsIgnoreCase(input = inputReader.readLine())) {
                if ("undo".equalsIgnoreCase(input)) {
                    undoInput();
                } else {
                    processInput(input);
                }
            }
        }
    }

    private void undoInput() {
        if (!history.isEmpty()) {
            Vote vote = history.removeLast();

            vote.candidate().removePoints(vote.points());
            logger.setMainOrSecond(!logger.getMainOrSecond());
        } else {
            logger.print("      Nothing left to undo!");
        }
        logger.printCandidates(candidates);

    }

    private void processInput(String input) {

        logger.print(input);

        int id = getNumber(input);

        if (!inputValid(id)) {
            logger.print("     Falsche Eingabe!");
            logger.printCandidates(candidates);
            return;
        }

        Candidate candidate = candidates.get(id - 1);

        int pointsAdded = candidate.addPoints(logger.getMainOrSecondAsNumber());

        history.addLast(new Vote(candidate, pointsAdded));

        logger.printCandidates(candidates);

        logger.setMainOrSecond(!logger.getMainOrSecond());

    }

    private boolean inputValid(int input) {
        return input > 0 && input < candidates.size();
    }

    private int getNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
