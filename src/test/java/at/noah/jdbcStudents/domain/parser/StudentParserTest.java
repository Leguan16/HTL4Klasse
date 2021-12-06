package at.noah.jdbcStudents.domain.parser;

import at.noah.jdbcStudents.domain.Student;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


class StudentParserTest {

    @Test
    void reads_correct_students_from_file() throws URISyntaxException, IOException {
        var filename = getClass().getClassLoader().getResource("jdbcStudents/students.csv");
        Objects.requireNonNull(filename);
        var path = Path.of(filename.toURI());


        var students = new StudentParser().readFromCsv(path);

        assertThat(students)
                .extracting(Student::lastName)
                .containsExactly("Ertl", "Frischmann", "Gangl", "Krapfenbauer", "Limani", "Novotny", "Panic", "Pfeifer");
    }
}
