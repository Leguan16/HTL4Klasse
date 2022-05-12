package at.noah.jpa.jpaRental.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Builder
@Getter
@ToString

@Entity
public class Rental {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Double drivenKm;

    @Column(name = "start_date")
    private LocalDateTime beginning;

    @Setter
    @Column(name = "end_date")
    private LocalDateTime end;

    @Setter
    @ManyToOne
    @Cascade(CascadeType.MERGE)
    private Car car;

    @ManyToOne
    private Station rentalStation;

    @Setter
    @ManyToOne
    private Station returnStation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(id, rental.id);
    }

    public Rental(Long id, Double drivenKm, LocalDateTime beginning, LocalDateTime end, Car car, Station rentalStation, Station returnStation) {
        this.id = id;
        this.beginning = beginning;
        this.end = end;
        this.car = car;
        this.rentalStation = rentalStation;
        this.returnStation = returnStation;
        this.drivenKm = drivenKm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @AssertTrue
    public boolean isValid() {
        return ((end == null && returnStation == null && drivenKm == null) ||
                (end != null && returnStation != null && drivenKm != null));
    }
}
