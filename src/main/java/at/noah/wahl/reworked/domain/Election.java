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

    private int lastVote = -1;

    public Election() {
        candidates = new ArrayList<>();
        logger = new DefaultElectionLogger();
    }

    public void addCandidates(Candidate... candidates) {
        this.candidates.addAll(Arrays.stream(candidates).toList());
    }

    public void start() {
        logger.printCandidates(candidates);

        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;

            while (!"quit".equalsIgnoreCase(input = inputReader.readLine())) {
                processInput(input);
            }

        } catch (IOException ioe) {
            System.err.println("Error: " + ioe.getMessage());
        }
    }

    public void undoInput() {
        if (!history.isEmpty()) {
            Vote vote = history.pop();

            vote.candidate().removePoints(vote.points());
            logger.setMainVote(!logger.getMainVote());
        } else {
            logger.println("      Nothing left to undo!");
        }
    }

    public void processInput(String input) {
        if ("undo".equalsIgnoreCase(input)) {
            undoInput();
        } else {
            logger.println("Voting for: " + input);

            int id = getNumber(input);

            if (inputValid(id)) {

                processVote(id);
                //lastVote = id;
            } else {
                logger.println("     Falsche Eingabe!");
            }
        }

        logger.printCandidates(candidates);
    }

    private void processVote(int id) {
        Candidate candidate = candidates.get(id - 1);

        int pointsAdded = candidate.addPoints(logger.getMainOrSecondAsNumber());

        history.push(new Vote(candidate, pointsAdded));

        logger.setMainVote(!logger.getMainVote());
    }

    public boolean inputValid(int input) {
        return (input > 0 && input <= candidates.size()); //&& !voteIsLastVote(input);
    }

    public boolean voteIsLastVote(int id) {
        boolean isLastVote = false;
        if (!logger.getMainVote()) {
            isLastVote = (id == lastVote);
            undoInput();
        }

        return isLastVote;
    }

    public int getNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public List<Candidate> getCandidates() {
        return List.copyOf(candidates);
    }
}
