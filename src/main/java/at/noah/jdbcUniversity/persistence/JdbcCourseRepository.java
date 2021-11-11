package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Course;
import at.noah.jdbcUniversity.domain.Professor;
import at.noah.jdbcUniversity.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public record JdbcCourseRepository(Connection connection) implements CourseRepository {

    @Override
    public List<Course> findAll() throws SQLException {
        String sql = """
                select course_id, type_id, professor_id, description, begin_date
                from courses
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.getResultSet();
            List<Course> courses = new ArrayList<>();

            while(resultSet.next()) {
                createCourse(resultSet).ifPresent(courses::add);
            }

            return courses;
        }
    }

    @Override
    public List<Course> findAllByProfessor(Professor professor) throws SQLException {
        String sql = """
                select course_id, type_id, professor_id, description, begin_date
                from courses
                where PROFESSOR_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, professor.getId());
            ResultSet resultSet = statement.getResultSet();
            List<Course> courses = new ArrayList<>();

            while(resultSet.next()) {
                createCourse(resultSet).ifPresent(courses::add);
            }

            return courses;
        }
    }

    @Override
    public Optional<Course> findById(int id) throws SQLException {
        String sql = """
                select course_id, type_id, professor_id, description, begin_date
                from courses
                where COURSE_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.getResultSet();
            List<Course> courses = new ArrayList<>();

            if (resultSet.next()) {
                return createCourse(resultSet);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Course save(Course course) throws SQLException {
        return null;
    }

    @Override
    public List<Course> findAllByStudent(Student student) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public void enrollInCourse(Student student, Course course) throws SQLException {

    }

    @Override
    public void unenrollFromCourse(Student student, Course course) throws SQLException {

    }


    private Optional<Course> createCourse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("course_id");
        char typeId = resultSet.getString("type_id").charAt(0);
        int professorId = resultSet.getInt("professor_id");
        String description = resultSet.getString("description");
        LocalDate beginDate = resultSet.getDate("begin_date").toLocalDate();

        return Optional.empty();
    }
}
