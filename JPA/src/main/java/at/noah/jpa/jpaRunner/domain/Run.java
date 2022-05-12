package at.noah.jpa.jpaRunner.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@Entity
public class Run {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    @Positive
    private Double distanceInKm;

    @Positive
    private Integer minutes;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Runner runner;

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
