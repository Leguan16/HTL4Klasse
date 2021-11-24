package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.Course;
import at.noah.jdbcUniversity.domain.CourseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record JdbcCourseTypeRepository(Connection connection) implements CourseTypeRepository {

    @Override
    public List<CourseType> findAll() throws SQLException {
        String sql = """
                select TYPE_ID, DESCRIPTION
                from COURSE_TYPES
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<CourseType> courseTypes = new ArrayList<>();

            while (resultSet.next()) {
                createCourseType(resultSet).ifPresent(courseTypes::add);
            }
            return courseTypes;
        }
    }

    @Override
    public Optional<CourseType> findById(char id) throws SQLException {

        String sql = """
                select TYPE_ID, DESCRIPTION
                from COURSE_TYPES
                where TYPE_ID = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, String.valueOf(id));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createCourseType(resultSet);
            }
        }
        return Optional.empty();
    }

    private Optional<CourseType> createCourseType(ResultSet resultSet) throws SQLException {
        char id = resultSet.getString("type_id").charAt(0);
        String description = resultSet.getString("description");

        return Optional.of(new CourseType(id, description));
    }
}
