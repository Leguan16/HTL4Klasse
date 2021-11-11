package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JdbcStudentRepository(Connection connection) implements StudentRepository {

    @Override
    public List<Student> findAll() throws SQLException {
        String sql = """
                select STUDENT_ID, LAST_NAME, FIRST_NAME
                from STUDENTS
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            List<Student> students = new ArrayList<>();

            while (resultSet.next()) {
                createStudent(resultSet).ifPresent(students::add);
            }

            return students;
        }
    }

    @Override
    public Optional<Student> findById(int id) throws SQLException {

        String sql= """
                select STUDENT_ID, LAST_NAME, FIRST_NAME
                from STUDENTS
                where STUDENT_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createStudent(resultSet);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Student save(Student student) throws SQLException {
        if (student.getId() != null) {
            throw new IllegalArgumentException("Student already has an ID!");
        }

        String sql= """
                INSERT into STUDENTS (LAST_NAME, FIRST_NAME) values ( ?, ? )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, student.getLastName());
            statement.setString(2, student.getFirstName());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                return new Student(id, student.getLastName(), student.getFirstName());
            }
        }

        return null;
    }

    @Override
    public void update(Student student) throws SQLException {

        String sql = """
                select STUDENT_ID, LAST_NAME, FIRST_NAME
                from STUDENTS
                where STUDENT_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, student.getId());

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new IllegalArgumentException("No student found with id " + student.getId());
            }
        }

        sql = """
                update STUDENTS
                SET LAST_NAME = ?, FIRST_NAME = ?
                where STUDENT_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, student.getLastName());
            statement.setString(2, student.getFirstName());
            statement.setInt(3, student.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Student student) throws SQLException {
        String sql= """
                delete from STUDENTS
                where STUDENT_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, student.getId());

            statement.executeUpdate();
        }
    }

    private Optional<Student> createStudent(ResultSet resultSet) throws SQLException {
        int studentID = resultSet.getInt("student_id");
        String lastName = resultSet.getString("last_name");
        String firstName = resultSet.getString("first_name");


        return Optional.of(new Student(studentID, lastName, firstName));
    }
}
