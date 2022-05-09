package at.noah.jpa.jpaRunner.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Builder
@ToString
public class Run {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    private Double distanceInKm;

    private Integer minutes;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Runner runner;

    public Run() {

    }

    public Run(Long id, LocalDate date, Double distanceInKm, Integer minutes, Runner runner) {
        this.id = id;
        this.date = date;
        this.distanceInKm = distanceInKm;
        this.minutes = minutes;
        this.runner = runner;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        if (!runner.getRuns().contains(this)) {
            runner.addRun(this);
        }

        this.runner = runner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Run run = (Run) o;
        return Objects.equals(id, run.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
