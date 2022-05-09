package at.noah.jdbc.jdbcStudents.persistence;

import at.noah.jdbc.jdbcStudents.domain.Gender;
import at.noah.jdbc.jdbcStudents.domain.Student;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class StudentRepositoryTest {

    public static final String JDBC_URL = "jdbc:h2:mem:students-test;INIT=RUNSCRIPT FROM 'classpath:/jdbcStudents/schema.sql'";
    private Connection connection;
    private StudentRepository repository;

    @BeforeEach
    void createRepository() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL);
        repository = new JdbcStudentRepository(connection);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.close();
    }

    @Nested
    class Saving {

        @Test
        void works() throws SQLException {
            var student = new Student("lastName", "firstName", Gender.MALE, 1, "1AHIF");

            repository.save(student);

            AssertionsForInterfaceTypes.assertThat(repository.findAll())
                    .containsExactly(student);
        }

        @Test
        void fails_if_existing_student_has_same_class_and_number() throws SQLException {
            var adler = new Student("Adler", "Alfred", Gender.MALE, 1, "1AHIF");
            var bilic = new Student("Bilic", "Blib", Gender.FEMALE, 1, "1AHIF");
            repository.save(adler);

            assertThatThrownBy(() -> repository.save(bilic));
            AssertionsForInterfaceTypes.assertThat(repository.findAll())
                    .containsExactly(adler);
        }
    }

    @Nested
    class FindingAllOfGivenGender {

        @Test
        void works_for_male() throws SQLException {
            var maleStudents = List.of(
                    new Student("Adler", "Alfred", Gender.MALE, 1, "1AHIF"),
                    new Student("Dumpf", "Daniel", Gender.MALE, 3, "1AHIF"),
                    new Student("Aas", "Thomas", Gender.MALE, 1, "4AHIF"));
            repository.save(new Student("Dan", "Som", Gender.FEMALE, 13, "3CHIF"));
            repository.save(new Student("Xim", "Som", Gender.DIVERSE, 2, "3CHIF"));
            for (var maleStudent : maleStudents)
                repository.save(maleStudent);

            var males = repository.findStudentsByGender(Gender.MALE);

            AssertionsForInterfaceTypes.assertThat(males)
                    .containsExactlyElementsOf(maleStudents);
        }

        @Test
        void works_for_female() throws SQLException {
            var femaleStudents = List.of(
                    new Student("Adler", "Anna", Gender.FEMALE, 1, "1AHIF"),
                    new Student("Dumpf", "Daniela", Gender.FEMALE, 3, "1AHIF"));
            repository.save(new Student("Aas", "Thomas", Gender.MALE, 1, "4AHIF"));
            repository.save(new Student("Dan", "Som", Gender.MALE, 13, "3CHIF"));
            repository.save(new Student("Xim", "Som", Gender.DIVERSE, 2, "3CHIF"));
            for (var femaleStudent : femaleStudents)
                repository.save(femaleStudent);

            var females = repository.findStudentsByGender(Gender.FEMALE);

            AssertionsForInterfaceTypes.assertThat(females)
                    .containsExactlyElementsOf(femaleStudents);
        }

        @Test
        void works_for_diverse() throws SQLException {
            var diverseStudents = List.of(
                    new Student("Adler", "Aop", Gender.DIVERSE, 1, "1AHIF"),
                    new Student("Dumpf", "Dela", Gender.DIVERSE, 3, "1AHIF"));
            repository.save(new Student("Aas", "Thomas", Gender.MALE, 1, "4AHIF"));
            repository.save(new Student("Dan", "Som", Gender.MALE, 13, "3CHIF"));
            repository.save(new Student("Xim", "Som", Gender.FEMALE, 2, "3CHIF"));
            for (var diverseStudent : diverseStudents)
                repository.save(diverseStudent);

            var diverse = repository.findStudentsByGender(Gender.DIVERSE);

            AssertionsForInterfaceTypes.assertThat(diverse)
                    .containsExactlyElementsOf(diverseStudents);
        }
    }

    @Nested
    class FindingClasses {

        @Test
        void returns_empty_map_if_no_classes_persisted() throws SQLException {
            assertThat(repository.findClasses())
                    .isEmpty();
        }

        @Test
        void returns_all_classes_with_count_of_students() throws SQLException {
            var students = List.of(
                    new Student("Baar", "Simon", Gender.MALE, 1, "1AHIF"),
                    new Student("Dohnal", "Matthias Markus Edwin", Gender.MALE, 2, "1AHIF"),
                    new Student("Ertl", "Maxima", Gender.FEMALE, 36, "1AHIF"),
                    new Student("Schally", "Martin", Gender.MALE, 17, "3BHIF"),
                    new Student("Vesely", "Flora", Gender.FEMALE, 19, "3BHIF"),
                    new Student("Winkler", "Philipp Josef", Gender.MALE, 20, "3BHIF"),
                    new Student("Hofmann", "Dominik", Gender.MALE, 21, "3BHIF"),
                    new Student("Filipa", "Fec", Gender.DIVERSE, 15, "1CHIF")
            );
            for (var student : students)
                repository.save(student);

            assertThat(repository.findClasses())
                    .containsExactly(
                            Map.entry("1AHIF", 3),
                            Map.entry("1CHIF", 1),
                            Map.entry("3BHIF", 4)
                    );
        }
    }

    @Nested
    class FindingStudentByNumberAndClass {

        @Test
        void works() throws SQLException {
            var students = List.of(
                    new Student("Baar", "Simon", Gender.MALE, 1, "1AHIF"),
                    new Student("Winkler", "Philipp Josef", Gender.MALE, 20, "3BHIF"),
                    new Student("Hofmann", "Dominik", Gender.MALE, 21, "3BHIF"),
                    new Student("Filipa", "Fec", Gender.DIVERSE, 15, "1CHIF")
            );
            for (var student : students)
                repository.save(student);

            var expected = new Student("Hofmann", "Dominik", Gender.MALE, 21, "3BHIF");

            Optional<Student> result = repository.findByNumberAndClass(21, "3BHIF");

            AssertionsForClassTypes.assertThat(result).isPresent();

            AssertionsForInterfaceTypes.assertThat(result.get()).isEqualTo(expected);
        }

        @Test
        void empty_optional_if_no_result() throws SQLException {
            var students = List.of(
                    new Student("Baar", "Simon", Gender.MALE, 1, "1AHIF"),
                    new Student("Winkler", "Philipp Josef", Gender.MALE, 20, "3BHIF"),
                    new Student("Hofmann", "Dominik", Gender.MALE, 21, "3BHIF"),
                    new Student("Filipa", "Fec", Gender.DIVERSE, 15, "1CHIF")
            );
            for (var student : students)
                repository.save(student);

            AssertionsForClassTypes.assertThat(repository.findByNumberAndClass(10, "4AHIF")).isNotPresent();
        }
    }

    @Nested
    class FindingStudentsByClass {
        @Test
        void works() throws SQLException {
            var expected = List.of(
                    new Student("Baar", "Simon", Gender.MALE, 1, "1AHIF"),
                    new Student("Winkler", "Philipp Josef", Gender.MALE, 2, "1AHIF"),
                    new Student("Hofmann", "Dominik", Gender.MALE, 3, "1AHIF")
            );
            for (var student : expected)
                repository.save(student);
            repository.save(new Student("Filipa", "Fec", Gender.DIVERSE, 15, "1CHIF"));
            repository.save(new Student("Filipa", "Fec", Gender.DIVERSE, 14, "1BHIF"));
            repository.save(new Student("Filipa", "Fec", Gender.DIVERSE, 1, "5BHIF"));

            AssertionsForInterfaceTypes.assertThat(repository.findStudentsByClass("1AHIF")).containsExactlyElementsOf(expected);
        }
    }

}
