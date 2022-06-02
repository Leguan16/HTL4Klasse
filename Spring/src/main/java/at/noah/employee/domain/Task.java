package at.noah.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter

@Entity
@Table(name = "Tasks")
public class Task {

    @Id
    @GeneratedValue
    Integer id;

    @Setter
    @NotNull
    String description;

    @Setter
    @Past
    @Column(name = "finished_date")
    LocalDate finishingTime;

    Integer hoursWorked;

    @ManyToOne
    @Setter
    @JoinColumn(name = "employee_id")
    Employee assignedEmployee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
