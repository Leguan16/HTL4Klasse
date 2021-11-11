package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Course;
import at.noah.jdbcUniversity.domain.Professor;
import at.noah.jdbcUniversity.domain.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public record JdbcCourseRepository(Connection connection) implements CourseRepository {

    @Override
    public List<Course> findAll() throws SQLException {
        return null;
    }

    @Override
    public List<Course> findAllByProfessor(Professor professor) throws SQLException {
        return null;
    }

    @Override
    public Optional<Course> findById(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Course save(Course course) throws SQLException {
        return null;
    }

    @Override
    public List<Course> findAllByStudent(Student student) throws SQLException {
        return null;
    }

    @Override
    public void enrollInCourse(Student student, Course course) throws SQLException {

    }

    @Override
    public void unenrollFromCourse(Student student, Course course) throws SQLException {

    }
}
