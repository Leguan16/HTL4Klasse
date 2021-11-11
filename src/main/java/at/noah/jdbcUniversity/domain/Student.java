package at.noah.jdbcUniversity.domain;


import java.util.Objects;

public class Student {

    private final Integer id;
    private String lastName;
    private String firstName;

    public Student(Integer id, String lastName, String firstName) throws IllegalArgumentException {
        if (lastName.isBlank() || firstName.isBlank()) {
            throw new IllegalArgumentException("Name can't be blank");
        }
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Student(String lastName, String firstName) throws IllegalArgumentException {
        this(null, lastName, firstName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
