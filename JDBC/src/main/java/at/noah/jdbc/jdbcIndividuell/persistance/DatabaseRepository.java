package at.noah.jdbc.jdbcIndividuell.persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DatabaseRepository<T> {
    public List<T> findAll() throws SQLException;

    public Optional<T> findById(Integer id) throws SQLException;

    public Optional<T> save(T toSave) throws SQLException;

    public boolean delete(T toDelete) throws SQLException;
}
