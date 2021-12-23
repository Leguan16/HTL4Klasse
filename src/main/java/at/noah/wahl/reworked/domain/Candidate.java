package at.noah.wahl.reworked.domain;

import java.text.DecimalFormat;

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

    public int getPoints() {
        return points;
    }

    public int getMainVoteCount() {
        return mainVoteCount;
    }
}
