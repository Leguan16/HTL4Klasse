package at.noah.jdbc.jdbcUniversity.persistence;

import at.noah.jdbc.jdbcUniversity.domain.Professor;
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


public class JdbcProfessorRepositoryTest {

    private final TestConnectionSupplier connectionSupplier = new TestConnectionSupplier();
    private ProfessorRepository professorRepository;
    private Connection connection;

    @BeforeEach
    void createRepository() throws SQLException {
        connection = connectionSupplier.getConnection();
        professorRepository = new JdbcProfessorRepository(connection);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    void finds_all() throws SQLException {
        var professors = professorRepository.findAll();

        Assertions.assertThat(professors)
                .extracting(Professor::getId)
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7);
    }

    @Nested
    class Saving {

        @Test
        void works() throws SQLException {
            var professor = new Professor("new professor", "firstName");

            var saved = professorRepository.save(professor);

            var assignedId = saved.getId();
            Assertions.assertThat(assignedId)
                    .isNotNull();
            var optionalProfessor = professorRepository.findById(assignedId);
            Assertions.assertThat(optionalProfessor)
                    .get()
                    .extracting(Professor::getLastName)
                    .isEqualTo("new professor");
        }

        @Test
        void fails_if_professor_has_id() {
            Professor professor = new Professor(10, "lastName", "firstName");

            assertThatThrownBy(() -> professorRepository.save(professor));
        }
    }

    @Nested
    class Deleting {

        @Test
        void existing_without_assigned_courses() throws SQLException {
            var professor = new Professor(1, "lastName", "firstName");
            professorRepository.delete(professor);

            Assertions.assertThat(professorRepository.findById(1))
                    .isEmpty();
        }

        @Test
        void fails_if_assigned_to_course() {
            var professor = new Professor(2, "lastName", "firstName");

            assertThatThrownBy(() -> professorRepository.delete(professor));
        }

        @Test
        void fails_without_id() {
            var professor = new Professor(null, "lastName", "firstName");

            assertThatThrownBy(() -> professorRepository.delete(professor));
        }
    }

    @Nested
    class Finding {

        @Test
        void existing_works() throws SQLException {
            var optionalProfessor = professorRepository.findById(3);

            Assertions.assertThat(optionalProfessor)
                    .get()
                    .extracting(Professor::getLastName)
                    .isEqualTo("Weizenbaum");
        }

        @Test
        void unknown_returns_empty_optional() throws SQLException {
            var optionalProfessor = professorRepository.findById(10);

            Assertions.assertThat(optionalProfessor)
                    .isEmpty();
        }
    }
}
