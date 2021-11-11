package at.noah.jdbcUniversity.persistence.setup;

import java.sql.*;

public class TestConnectionSupplier {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("""
                jdbc:h2:mem:test;\
                init=runscript from 'classpath:/schema.sql'\\;
                runscript from 'classpath:/data.sql'
                """);
    }
}
