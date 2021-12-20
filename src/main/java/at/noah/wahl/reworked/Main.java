package at.noah.wahl.reworked;

public class Main {

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
}
