package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JdbcProfessorRepository(Connection connection) implements ProfessorRepository {

    @Override
    public List<Professor> findAll() throws SQLException {
        String sql = """
                select PROFESSOR_ID, LAST_NAME, FIRST_NAME
                from PROFESSORS
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Professor> professors = new ArrayList<>();

            while (resultSet.next()) {
                createProfessor(resultSet).ifPresent(professors::add);
            }

            return professors;
        }
    }

    @Override
    public Optional<Professor> findById(int id) throws SQLException {
        String sql = """
                select PROFESSOR_ID, LAST_NAME, FIRST_NAME
                from PROFESSORS
                where PROFESSOR_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createProfessor(resultSet);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Professor save(Professor professor) throws SQLException {
        if (professor.getId() != null) {
            throw new IllegalArgumentException("Professor already has an ID!");
        }

        String sql = """
                INSERT into PROFESSORS (LAST_NAME, FIRST_NAME) values ( ?, ? )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, professor.getLastName());
            statement.setString(2, professor.getFirstName());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt("professor_id");
                return new Professor(id, professor.getLastName(), professor.getFirstName());
            }
        }

        return null;
    }

    @Override
    public void delete(Professor professor) throws SQLException {
        String sql = """
                delete from PROFESSORS
                where PROFESSOR_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, professor.getId());

            statement.executeUpdate();
        }
    }

    private Optional<Professor> createProfessor(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("professor_id");
        String lastName = resultSet.getString("last_name");
        String firstName = resultSet.getString("first_name");

        return Optional.of(new Professor(id, lastName, firstName));
    }
}
