package at.noah.jdbcUniversity.domain;


import java.util.Objects;

public class Professor {

    private final Integer id;
    private String lastName;
    private String firstName;

    public Professor(Integer id, String lastName, String firstName) {
        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("Name can't be blank");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(id, professor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Professor(String lastName, String firstName) {
        this(null, lastName, firstName);
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
