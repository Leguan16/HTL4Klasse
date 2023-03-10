package at.noah.jpa.jpaRunner.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter

@Entity
public class Runner {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Setter
    private Gender gender;


    @OneToMany(mappedBy = "runner")
    private List<Run> runs = new ArrayList<>();

    public Runner(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
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
