package at.noah.jdbc.jdbcUniversity.persistence.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionSupplier {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("""
                jdbc:h2:mem:test;\
                init=runscript from 'classpath:/jdbcUniversity/schema.sql'\\;
                runscript from 'classpath:/jdbcUniversity/data.sql'
                """);
    }
}
