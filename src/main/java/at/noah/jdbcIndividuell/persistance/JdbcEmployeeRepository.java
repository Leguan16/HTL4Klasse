package at.noah.jdbcIndividuell.persistance;

import at.noah.jdbcIndividuell.domain.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record JdbcEmployeeRepository(Connection connection) implements EmployeeRepository{

    @Override
    public List<Employee> findAll() throws SQLException {
        String slq = """
                select *
                FROM employees
                """;

        try(PreparedStatement statement = connection.prepareStatement(slq)) {
            ResultSet resultSet = statement.executeQuery();
            List<Employee> employees = new ArrayList<>();

            while(resultSet.next()) {
                createEmployee(resultSet).ifPresent(employees::add);
            }
            return employees;
        }
    }

    @Override
    public Optional<Employee> findById(Integer id) throws SQLException {

        String sql = """
                select *
                from employees
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createEmployee(resultSet);
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(Employee toSave) throws SQLException {
        String sql = """
                insert into employees
                values ( ?, ?, ?, ?, ?, ?, ? )
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, toSave.getId());
            statement.setString(2, toSave.getName());
            statement.setDate(3, Date.valueOf(toSave.getDateOfBirth()));
            statement.setString(4, toSave.getJob());

            if (toSave.getSupervisor() != null) {
                statement.setInt(5, toSave.getSupervisor().getId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }

            statement.setDate(6, Date.valueOf(toSave.getEntryDate()));
            statement.setInt(7, toSave.getSalary());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Employee toDelete) throws SQLException {
        if (toDelete.getId() == null) {
            return;
        }

        String sql = """
                delete from employees
                where id = ?
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, toDelete.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public List<Employee> getEmployeesOfSupervisor(Integer supervisorId) throws SQLException {
        if (supervisorId == null){
            return Collections.emptyList();
        }

        String sql = """
                select *
                from employees
                where supervisor = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, supervisorId);

            ResultSet resultSet = statement.executeQuery();

            List<Employee> employees = new ArrayList<>();

            while(resultSet.next()) {
                createEmployee(resultSet).ifPresent(employees::add);
            }
            return employees;
        }
    }

    private Optional<Employee> createEmployee(ResultSet resultSet) {

        try {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("employee_name");
            LocalDate dateOfBirth = resultSet.getDate("date_of_birth").toLocalDate();
            String job = resultSet.getString("job");
            Integer supervisorId = resultSet.getInt("supervisor");
            LocalDate entryDate = resultSet.getDate("entry_date").toLocalDate();
            Integer salary = resultSet.getInt("salary");

            if (supervisorId.equals(id)) {
                Employee employee = new Employee(id, name, dateOfBirth, job, entryDate, salary);
                employee.setSupervisor(employee);

                return Optional.of(employee);
            } else {
                Optional<Employee> supervisor = findById(supervisorId);
                return supervisor
                        .map(employee -> new Employee(id, name, dateOfBirth, job, employee, entryDate, salary))
                        .or(() -> Optional.of(new Employee(id, name, dateOfBirth, job, entryDate, salary)));
            }


        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
