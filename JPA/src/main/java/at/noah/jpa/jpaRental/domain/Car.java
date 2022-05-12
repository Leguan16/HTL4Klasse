package at.noah.jpa.jpaRental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

@Entity
public class Car {

    @Id
    @Length(min = 4, max = 9)
    private String plate;

    @PositiveOrZero
    private double mileage;

    private String model;

    @Setter
    @ManyToOne
    private Station location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(plate, car.plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate);
    }

    public void addMileage(double drivenKm) {
        mileage += drivenKm;
    }
}
