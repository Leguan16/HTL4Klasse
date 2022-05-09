package at.noah.jdbc.jdbcIndividuell.persistance;


import at.noah.jdbc.jdbcIndividuell.domain.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends DatabaseRepository<Employee> {
    Optional<Employee> findById(Integer id) throws SQLException;

    public List<Employee> getEmployeesOfSupervisor(Integer supervisor) throws SQLException;

}
