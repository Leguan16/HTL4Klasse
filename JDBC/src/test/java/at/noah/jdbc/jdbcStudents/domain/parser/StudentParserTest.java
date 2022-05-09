package at.noah.jdbc.jdbcStudents.domain.parser;

import at.noah.jdbc.jdbcStudents.domain.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

class StudentParserTest {

    @Test
    void reads_correct_students_from_file() throws URISyntaxException, IOException {
        var filename = getClass().getClassLoader().getResource("jdbcStudents/students.csv");
        Objects.requireNonNull(filename);
        var path = Path.of(filename.toURI());


        var students = new StudentParser().readFromCsv(path);

        Assertions.assertThat(students)
                .extracting(Student::lastName)
                .containsExactly("Ertl", "Frischmann", "Gangl", "Krapfenbauer", "Limani", "Novotny", "Panic", "Pfeifer");
    }
}
