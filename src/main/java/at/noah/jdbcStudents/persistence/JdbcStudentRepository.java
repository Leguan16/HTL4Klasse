package at.noah.jdbcStudents.persistence;

import at.noah.jdbcStudents.domain.Gender;
import at.noah.jdbcStudents.domain.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

public record JdbcStudentRepository(Connection connection) implements StudentRepository {

    @Override
    public void deleteAll() throws SQLException {

    }

    @Override
    public Optional<Student> findByNumberAndClass(int number, String schoolClass) throws SQLException {
        return null;
    }

    @Override
    public Stream<Student> findAll() throws SQLException {
        return null;
    }

    @Override
    public SortedSet<Student> findStudentsByClass(String schoolClass) throws SQLException {
        return null;
    }

    @Override
    public Set<Student> findStudentsByGender(Gender gender) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Integer> findClasses() throws SQLException {
        return null;
    }

    @Override
    public void save(Student student) throws SQLException {

    }
}
