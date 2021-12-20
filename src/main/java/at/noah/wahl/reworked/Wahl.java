package at.noah.wahl.reworked;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wahl {
    private final List<Candidate> candidates;


    private final Logger logger;

    public Wahl() throws IOException {
        candidates = new ArrayList<>();
        logger = new Logger();
    }

    public void addCandidates(Candidate... candidates) {
        this.candidates.addAll(Arrays.stream(candidates).toList());
    }

    public void start() throws IOException {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {

            logger.printCanditates(candidates);
            String input;

            while (!"quit".equalsIgnoreCase(input = inputReader.readLine())) {
                logger.print(input);

                int id = getNumber(input);

                if (id != -1 && id <= candidates.size()) {
                    candidates.get(id).addPoints(logger.getMainOrSecond());
                    logger.printCanditates(candidates);
                    logger.setMainOrSecond(!logger.getMainOrSecond());
                } else {
                    logger.print("     Falsche Eingabe!");
                }

                logger.print("-----------------------------------------------------------");

                logger.printInputLine();
            }
        }
    }

    private int getNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
