package at.noah.wahl.reworked.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ElectionTest {

    private Election election;

    @BeforeEach
    public void setup() {
        election = new Election();

        election.addCandidates(
                new Candidate("Dominik", "Hofmann"),
                new Candidate("Kilian", "Prager"),
                new Candidate("Niklas", "Hochstöger"),
                new Candidate("Paul", "Pfiel"),
                new Candidate("Raid", "Alarkhanov")
        );

    }

    @Test
    public void MainVote() {
        election.processInput("1");

        Candidate candidate = election.getCandidates().get(0);

        assertThat(candidate.getPoints()).isEqualTo(2);
        assertThat(candidate.getMainVoteCount()).isEqualTo(1);
    }

    @Test
    public void SecondVote() {
        election.processInput("1");
        election.processInput("2");

        Candidate candidate = election.getCandidates().get(1);

        assertThat(candidate.getPoints()).isEqualTo(1);
        assertThat(candidate.getMainVoteCount()).isEqualTo(0);
    }

    @Test
    public void MoreVotes() {
        election.processInput("1");
        election.processInput("3");
        election.processInput("5");
        election.processInput("2");
        election.processInput("3");

        Candidate kilian = election.getCandidates().get(1);

        assertThat(kilian.getPoints()).isEqualTo(1);
        assertThat(kilian.getMainVoteCount()).isEqualTo(0);

        Candidate niklas = election.getCandidates().get(2);

        assertThat(niklas.getPoints()).isEqualTo(3);
        assertThat(niklas.getMainVoteCount()).isEqualTo(1);
    }

    @Test
    public void UndoVote() {
        election.processInput("1");
        election.processInput("3");
        election.processInput("5");
        election.processInput("2");
        election.processInput("3");

        election.undoInput();

        Candidate niklas = election.getCandidates().get(2);

        assertThat(niklas.getPoints()).isEqualTo(1);
        assertThat(niklas.getMainVoteCount()).isEqualTo(0);
    }

    @Test
    public void NothingLeftToUndo() {
        election.processInput("1");

        election.undoInput();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);

        PrintStream stdout = System.out;

        System.setOut(ps);

        election.undoInput();

        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();

        assertThat(outputText)
                .contains("Nothing left to undo!")
                .doesNotContain("2 / 1")
                .doesNotContain("1 / 0");
    }

    @Test
    public void wrongInput() {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);

        PrintStream stdout = System.out;

        System.setOut(ps);


        election.processInput("test");

        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();

        assertThat(outputText)
                .contains("Falsche Eingabe!")
                .doesNotContain("2 / 1")
                .doesNotContain("1 / 0");
    }
}