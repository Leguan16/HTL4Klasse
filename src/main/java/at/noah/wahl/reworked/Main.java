package at.noah.wahl.reworked;

import at.noah.wahl.reworked.domain.Candidate;
import at.noah.wahl.reworked.domain.Election;

public class Main {

    public static void main(String[] args) throws Exception {
        Election election = new Election();
        election.addCandidates(
                new Candidate("Dominik", "Hofmann"),
                new Candidate("Kilian", "Prager"),
                new Candidate("Niklas", "Hochstöger"),
                new Candidate("Paul", "Pfiel"),
                new Candidate("Raid", "Alarkhanov")
        );

        election.start();
    }
}
