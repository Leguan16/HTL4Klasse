package at.noah.wahl.reworked;

public class Main {

    public static void main(String[] args) throws Exception {
        Wahl wahl = new Wahl();
        wahl.addCandidates(
                new Candidate("Dominik", "Hofmann"),
                new Candidate("Kilian", "Prager"),
                new Candidate("Niklas", "Hochstöger"),
                new Candidate("Paul", "Pfiel"),
                new Candidate("Raid", "Alarkhanov")
        );

        wahl.start();
    }
}
