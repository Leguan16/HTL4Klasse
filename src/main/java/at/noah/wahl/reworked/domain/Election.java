package at.noah.wahl.reworked.domain;

import at.noah.wahl.reworked.logger.DefaultElectionLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Election {
    private final List<Candidate> candidates;

    private final DefaultElectionLogger logger;

    private final Stack<Vote> history = new Stack<>();

    public Election() {
        candidates = new ArrayList<>();
        logger = new DefaultElectionLogger();
    }

    public void addCandidates(Candidate... candidates) {
        this.candidates.addAll(Arrays.stream(candidates).toList());
    }

    public void start() throws IOException {
        logger.printCandidates(candidates);

        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
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
            Vote vote = history.pop();

            vote.candidate().removePoints(vote.points());
            logger.setMainVote(!logger.getMainVote());
        } else {
            logger.println("      Nothing left to undo!");
        }
        logger.printCandidates(candidates);

    }

    private void processInput(String input) {

        logger.println("Voting for: " + input);

        int id = getNumber(input);

        if (!inputValid(id)) {
            logger.println("     Falsche Eingabe!");
            logger.printCandidates(candidates);
            return;
        }

        Candidate candidate = candidates.get(id - 1);

        int pointsAdded = candidate.addPoints(logger.getMainOrSecondAsNumber());

        history.push(new Vote(candidate, pointsAdded));

        logger.setMainVote(!logger.getMainVote());
        logger.printCandidates(candidates);
    }

    private boolean inputValid(int input) {
        return input > 0 && input <= candidates.size();
    }

    private int getNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
