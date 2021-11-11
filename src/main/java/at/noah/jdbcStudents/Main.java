package at.noah.jdbcStudents;

import at.noah.jdbcStudents.domain.parser.StudentParser;
import at.noah.jdbcStudents.persistence.JdbcStudentRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:Databases/master.mv.db");

        JdbcStudentRepository repository = new JdbcStudentRepository(connection);

    }
}
