package at.noah.jdbcIndividuell;

import at.noah.jdbcIndividuell.domain.Employee;
import at.noah.jdbcIndividuell.persistance.EmployeeRepository;
import at.noah.jdbcIndividuell.persistance.JdbcEmployeeRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://10.128.6.6;database=bernhard_caritas", "sa", "");

        EmployeeRepository employeeRepository = new JdbcEmployeeRepository(connection);

        List<Employee> employeeList = employeeRepository.getEmployeesOfSupervisor(1);

        employeeList.forEach(employee -> System.out.println(employee.getName()));

    }
}
