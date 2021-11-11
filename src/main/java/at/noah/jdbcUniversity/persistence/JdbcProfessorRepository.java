package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Professor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public record JdbcProfessorRepository(Connection connection) implements ProfessorRepository {

    @Override
    public List<Professor> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Professor> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Professor save(Professor professor) throws SQLException {
        return null;
    }

    @Override
    public void delete(Professor professor) throws SQLException {

    }
}
