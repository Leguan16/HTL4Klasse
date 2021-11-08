package at.noah.student;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        StudentStatistics studentStatistics = new StudentStatistics(Path.of("src/main/resources/students/schueler.csv"));
        System.out.println(studentStatistics.getStudentWithLongestName().toString());
    }
}
