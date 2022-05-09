package at.noah.jdbc.jdbcStudents.domain.parser;



import at.noah.jdbc.jdbcStudents.domain.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StudentParser {

    /**
     * Converts all lines of a csv-formatted file to the students. Invalid lines are skipped
     *
     * @param path the path of the csv-file
     * @return a collection of all valid read students from students.csv
     */
    public Collection<Student> readFromCsv(Path path) throws IOException {
        return Files
                .lines(path)
                .skip(1)
                .map(Student::of)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
