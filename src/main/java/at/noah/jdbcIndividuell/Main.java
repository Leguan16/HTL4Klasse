package at.noah.jdbcIndividuell;

import at.noah.jdbcIndividuell.domain.Device;
import at.noah.jdbcIndividuell.domain.Employee;
import at.noah.jdbcIndividuell.persistance.DeviceRepository;
import at.noah.jdbcIndividuell.persistance.EmployeeRepository;
import at.noah.jdbcIndividuell.persistance.JdbcDeviceRepository;
import at.noah.jdbcIndividuell.persistance.JdbcEmployeeRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://10.128.6.6;database=bernhard_caritas", "sa", "");

        EmployeeRepository employeeRepository = new JdbcEmployeeRepository(connection);
        DeviceRepository deviceRepository = new JdbcDeviceRepository(connection, employeeRepository);

        var device = new Device("PC", LocalDate.now());
        var employee = new Employee("name", LocalDate.now(), "IT", LocalDate.now(), 5555);

        deviceRepository.save(device);

        System.out.println(device.getId());


        connection.close();
    }
}
