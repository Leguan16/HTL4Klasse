package at.noah.wahl.reworked;

import java.text.DecimalFormat;
import java.util.Objects;

public class Candidate {

    private final String firstName;
    private final String lastName;
    private int punkte;
    private int platz1;

    public Candidate(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public void addPoints(int p) {
        this.punkte += p;
        if (p == 2)
            this.platz1++;
    }

    public String toString() {
        DecimalFormat dc = new DecimalFormat("###0");
        return dc.format(punkte) + " / " + dc.format(platz1) + "   " + this.firstName + " " + this.lastName;
    }

    public char firstChar() {
        return this.firstName.toLowerCase().charAt(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return punkte == candidate.punkte && platz1 == candidate.platz1 && Objects.equals(firstName, candidate.firstName) && Objects.equals(getLastName(), candidate.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, getLastName(), punkte, platz1);
    }

    public String getLastName() {
        return lastName;
    }
}
