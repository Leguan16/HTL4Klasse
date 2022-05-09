package at.noah.jdbc.jdbcUniversity.persistence;

import at.noah.jdbc.jdbcUniversity.domain.Student;
import at.noah.jdbc.jdbcUniversity.persistence.setup.TestConnectionSupplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JdbcStudentRepositoryTest {

    private final TestConnectionSupplier connectionSupplier = new TestConnectionSupplier();
    private StudentRepository studentRepository;
    private Connection connection;

    @BeforeEach
    void createRepository() throws SQLException {
        connection = connectionSupplier.getConnection();
        studentRepository = new JdbcStudentRepository(connection);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.close();
    }


    @Test
    void finds_all() throws SQLException {
        var students = studentRepository.findAll();

        Assertions.assertThat(students)
                .extracting(Student::getId)
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6);
    }

    @Nested
    class Saving {

        @Test
        void works() throws SQLException {
            var student = new Student("lastName", "firstName");

            var saved = studentRepository.save(student);

            var assignedId = saved.getId();
            Assertions.assertThat(assignedId)
                    .isNotNull();
            Assertions.assertThat(studentRepository.findById(assignedId))
                    .get()
                    .extracting(Student::getLastName)
                    .isEqualTo("lastName");
        }

        @Test
        void fails_if_student_has_id() {
            var student = new Student(42, "lastName", "firstName");

            assertThatThrownBy(() -> studentRepository.save(student));
        }
    }

    @Nested
    class Deleting {

        @Test
        void existing_without_assigned_course() throws SQLException {
            var student = new Student(2, "lastName", "firstName");
            studentRepository.delete(student);

            Assertions.assertThat(studentRepository.findById(2))
                    .isEmpty();
        }

        @Test
        void existing_with_assigned_courses_unsubscribes_courses() throws SQLException {
            var student = new Student(4, "lastName", "firstName");
            studentRepository.delete(student);

            Assertions.assertThat(studentRepository.findById(4))
                    .isEmpty();
            var courseRepository = new JdbcCourseRepository(connection);
            Assertions.assertThat(courseRepository.findAllByStudent(student))
                    .isEmpty();
        }

        @Test
        void fails_without_id() {
            var student = new Student(null, "lastName", "firstName");

            assertThatThrownBy(() -> studentRepository.delete(student));
        }
    }

    @Nested
    class Updating {

        @Test
        void works() throws SQLException {
            var student = new Student("lastName", "firstName");
            var saved = studentRepository.save(student);
            saved.setLastName("changed");
            saved.setFirstName("modified");

            studentRepository.update(saved);

            Assertions.assertThat(studentRepository.findById(saved.getId()))
                    .get()
                    .extracting(Student::getLastName, Student::getFirstName)
                    .containsExactly("changed", "modified");
        }

        @Test
        void fails_for_unpersisted_student() {
            var student = new Student(200, "lastName", "firstName");

            assertThatThrownBy(() -> studentRepository.update(student));
        }

        @Test
        void fails_without_id() {
            var student = new Student(null, "lastName", "firstName");

            assertThatThrownBy(() -> studentRepository.update(student));
        }
    }

    @Nested
    class Finding {

        @Test
        void existing_works() throws SQLException {
            var optionalStudent = studentRepository.findById(1);

            Assertions.assertThat(optionalStudent)
                    .get()
                    .extracting(Student::getId)
                    .isEqualTo(1);
        }

        @Test
        void unknown_returns_empty_optional() throws SQLException {
            var optionalStudent = studentRepository.findById(404);

            Assertions.assertThat(optionalStudent).isEmpty();
        }
    }
}
