package at.noah.jdbcIndividuell.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

    private Integer id;
    private Employee supervisor;

    private final String name;
    private final LocalDate dateOfBirth;
    private final String job;
    private final LocalDate entryDate;
    private final Integer salary;

    public Employee(Integer id, String name, LocalDate dateOfBirth, String job, Employee supervisor, LocalDate entryDate, Integer salary) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.job = job;
        this.supervisor = supervisor;
        this.entryDate = entryDate;
        this.salary = salary;
    }

    public Employee(String name, LocalDate dateOfBirth, String job, Employee supervisor, LocalDate entryDate, Integer salary) {
        this(null, name, dateOfBirth, job, supervisor, entryDate, salary);
    }

    public Employee(Integer id, String name, LocalDate dateOfBirth, String job, LocalDate entryDate, Integer salary) {
        this(id, name, dateOfBirth, job, null, entryDate, salary);
    }

    public Employee(String name, LocalDate dateOfBirth, String job, LocalDate entryDate, Integer salary) {
        this(null, name, dateOfBirth, job, null, entryDate, salary);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getJob() {
        return job;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public Integer getSalary() {
        return salary;
    }
}
