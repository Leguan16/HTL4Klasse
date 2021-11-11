package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public record JdbcStudentRepository(Connection connection) implements StudentRepository {

    @Override
    public List<Student> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Student> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Student save(Student student) throws SQLException {
        return null;
    }

    @Override
    public void update(Student student) throws SQLException {

    }

    @Override
    public void delete(Student student) throws SQLException {

    }
}
