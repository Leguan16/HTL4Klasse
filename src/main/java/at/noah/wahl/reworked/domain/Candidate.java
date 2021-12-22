package at.noah.wahl.reworked.domain;

import java.text.DecimalFormat;
import java.util.Objects;

public class Candidate {

    private final String firstName;
    private final String lastName;
    private int points;
    private int mainVoteCount;

    public Candidate(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString() {
        DecimalFormat dc = new DecimalFormat("###0");
        return dc.format(points) + " / " + dc.format(mainVoteCount) + "   " + this.firstName + " " + this.lastName;
    }

    public char firstChar() {
        return this.firstName.toLowerCase().charAt(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return points == candidate.points && mainVoteCount == candidate.mainVoteCount && Objects.equals(firstName, candidate.firstName) && Objects.equals(getLastName(), candidate.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, getLastName(), points, mainVoteCount);
    }

    public String getLastName() {
        return lastName;
    }

    public int addPoints(int mainOrSecond) {
        int actualPoints = 1 + mainOrSecond;
        this.points += actualPoints;

        if (actualPoints == 2) {
            this.mainVoteCount++;
        }

        return actualPoints;
    }

    public void removePoints(int points) {
        if (points == 2) {
            this.mainVoteCount--;
        }

        this.points -= points;
    }

}
