package at.noah.jpa.jpaRunner.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.util.*;

@Builder
@ToString

@Entity
public class Runner {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Gender gender;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Run> runs = new ArrayList<>();

    public Runner() {

    }

    public Runner(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public Runner(Long id, String name, Gender gender, List<Run> runs) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.runs = runs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public void addRun(Run run) {
        if (!runs.contains(run)) {
            runs.add(run);
        }

        if (!this.equals(run.getRunner())) {
            run.setRunner(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return Objects.equals(id, runner.id) && Objects.equals(name, runner.name) && gender == runner.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender);
    }
}
