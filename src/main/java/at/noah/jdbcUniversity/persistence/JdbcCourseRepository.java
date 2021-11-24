package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Course;
import at.noah.jdbcUniversity.domain.CourseType;
import at.noah.jdbcUniversity.domain.Professor;
import at.noah.jdbcUniversity.domain.Student;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public record JdbcCourseRepository(Connection connection) implements CourseRepository {

    private static CourseTypeRepository courseTypeRepository;
    private static ProfessorRepository professorRepository;

    public JdbcCourseRepository {
        courseTypeRepository = new JdbcCourseTypeRepository(connection);
        professorRepository = new JdbcProfessorRepository(connection);
    }

    @Override
    public List<Course> findAll() throws SQLException {
        String sql = """
                select course_id, type_id, professor_id, description, begin_date
                from courses
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
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
            ResultSet resultSet = statement.executeQuery();
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
            ResultSet resultSet = statement.executeQuery();
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
        if (course.getId() != null) {
            throw new IllegalArgumentException("Course already has an ID");
        }

        if (course.getBegin().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Course cant be in the past");
        }

        String sql = """
                insert into COURSES (TYPE_ID, PROFESSOR_ID, DESCRIPTION, BEGIN_DATE) VALUES ( ?, ?, ?, ? )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, String.valueOf(course.getType().id()));

            statement.setInt(2, course.getProfessor().getId());
            statement.setString(3, course.getDescription());
            statement.setDate(4, Date.valueOf(course.getBegin()));

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int id = resultSet.getInt("course_id");
                return new Course(id, course.getType(), course.getProfessor(), course.getDescription(), course.getBegin());
            }
        }
        return null;
    }

    @Override
    public List<Course> findAllByStudent(Student student) throws SQLException {

        String sql = """
                select courses.COURSE_ID, courses.TYPE_ID, courses.PROFESSOR_ID, courses.DESCRIPTION, courses.BEGIN_DATE
                from COURSES courses inner join COURSES_STUDENTS CS on courses.COURSE_ID = CS.COURSE_ID
                where courses.COURSE_ID in (select CS.COURSE_ID from COURSES_STUDENTS where CS.STUDENT_ID = ?)
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student.getId());

            ResultSet resultSet = statement.executeQuery();

            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                createCourse(resultSet).ifPresent(courses::add);
            }
            return courses;
        }
    }

    @Override
    public void enrollInCourse(Student student, Course course) throws SQLException {
        if (student.getId() == null || course.getId() == null) {
            throw new IllegalArgumentException("Both must have an ID");
        }

        String sql = """
                insert into COURSES_STUDENTS (COURSE_ID, STUDENT_ID) values ( ?, ? )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, course.getId());
            statement.setInt(2, student.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void unenrollFromCourse(Student student, Course course) throws SQLException {
        if (student.getId() == null || course.getId() == null) {
            throw new IllegalArgumentException("Both must have an ID");
        }

        if (!findAllByStudent(student).contains(course)){
            throw new IllegalArgumentException("Student not enrolled in course!");
        }

        String sql = """
                delete from COURSES_STUDENTS
                where STUDENT_ID = ? and COURSE_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, student.getId());
            statement.setInt(2, course.getId());

            statement.executeUpdate();
        }
    }


    private Optional<Course> createCourse(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("course_id");
        char typeId = resultSet.getString("type_id").charAt(0);
        int professorId = resultSet.getInt("professor_id");
        String description = resultSet.getString("description");
        LocalDate beginDate = resultSet.getDate("begin_date").toLocalDate();

        Optional<CourseType> possibleCourseType = courseTypeRepository.findById(typeId);
        Optional<Professor> possibleProfessor = professorRepository.findById(professorId);

        if (possibleCourseType.isPresent() && possibleProfessor.isPresent()) {
            return Optional
                    .of(new Course(id, possibleCourseType.get(), possibleProfessor.get(), description, beginDate));
        }
        return Optional.empty();
    }
}
