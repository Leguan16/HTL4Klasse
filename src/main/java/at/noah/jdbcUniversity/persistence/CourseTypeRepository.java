package at.noah.jdbcUniversity.persistence;

import at.noah.jdbcUniversity.domain.CourseType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseTypeRepository {

    List<CourseType> findAll() throws SQLException;

    Optional<CourseType> findById(char id) throws SQLException;
}
