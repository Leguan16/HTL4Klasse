package at.noah.jdbcIndividuell.persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DatabaseRepository<T> {
    public List<T> findAll() throws SQLException;
    public Optional<T> findById(Integer id) throws SQLException;
    public void save(T toSave) throws SQLException;
    public void delete(T toDelete) throws SQLException;
}
