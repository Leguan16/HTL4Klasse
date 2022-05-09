package at.noah.jdbc.jdbcUniversity.domain;


import java.util.Objects;

public record CourseType(char id, String description) {

    public CourseType {
        if (description.isBlank())
            throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseType that = (CourseType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
