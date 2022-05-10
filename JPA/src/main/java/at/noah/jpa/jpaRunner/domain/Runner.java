package at.noah.jpa.jpaRunner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Runner {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Gender gender;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Run> runs = new ArrayList<>();

    public Runner(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runner runner = (Runner) o;
        return Objects.equals(id, runner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
