package at.noah.jdbc.persistance;

import at.noah.jdbc.jdbcIndividuell.domain.Device;
import at.noah.jdbc.jdbcIndividuell.domain.Employee;
import at.noah.jdbc.jdbcIndividuell.persistance.DeviceRepository;
import at.noah.jdbc.jdbcIndividuell.persistance.EmployeeRepository;
import at.noah.jdbc.jdbcIndividuell.persistance.JdbcDeviceRepository;
import at.noah.jdbc.jdbcIndividuell.persistance.JdbcEmployeeRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JdbcDeviceRepositoryTest {
    public static final String JDBC_URL = "jdbc:sqlserver://10.128.6.6;database=bernhard_caritas";
    private static Connection connection;
    private static DeviceRepository deviceRepository;
    private static EmployeeRepository employeeRepository;
    private static Savepoint savepoint;

    @BeforeAll
    static void init() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, "sa", "");
        employeeRepository = new JdbcEmployeeRepository(connection);
        deviceRepository = new JdbcDeviceRepository(connection, employeeRepository);

        connection.setAutoCommit(false);
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        connection.close();
    }

    @BeforeEach
    void setSavepoint() throws SQLException {
        savepoint = connection.setSavepoint();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback(savepoint);
    }

    @Nested
    class Saving {

        @Test
        void works() throws SQLException {
            Employee owner = employeeRepository.findById(1).orElseThrow();
            var device = new Device("Notebook", LocalDate.of(2021, 7, 17), owner);

            deviceRepository.save(device);

            AssertionsForInterfaceTypes.assertThat(deviceRepository.findAll())
                    .contains(device);
        }

        @Test
        void returns_empty_if_device_already_in_database() throws SQLException {
            var device = new Device(1, "PC", LocalDate.of(2021, 7, 17));

            Optional<Device> possibleDevice = deviceRepository.save(device);

            AssertionsForClassTypes.assertThat(possibleDevice)
                    .isEmpty();
        }

        @Test
        void returns_empty_if_device_owner_does_not_have_an_id() throws SQLException {
            var owner = new Employee("Hans", LocalDate.of(2021, 7, 17), "IT", LocalDate.of(2021, 7, 17), 4000);
            var device = new Device(1, "PC", LocalDate.of(2021, 7, 17));

            Optional<Device> possibleDevice = deviceRepository.save(device);

            AssertionsForClassTypes.assertThat(possibleDevice)
                    .isEmpty();
        }
    }

    @Nested
    class find {

        @Test
        void find_all() throws SQLException {
            List<Device> expected = new ArrayList<>();

            expected.add(new Device(1, "PC", LocalDate.parse("2021-07-17")));
            expected.add(new Device(2, "PC", LocalDate.parse("2021-07-17")));
            expected.add(new Device(3, "PC", LocalDate.parse("2021-07-17")));
            expected.add(new Device(4, "PC", LocalDate.parse("2021-07-17")));
            expected.add(new Device(5, "Notebook", LocalDate.parse("2021-07-17")));
            expected.add(new Device(6, "Notebook", LocalDate.parse("2021-07-17")));
            expected.add(new Device(7, "Notebook", LocalDate.parse("2021-07-17")));
            expected.add(new Device(8, "Notebook", LocalDate.parse("2021-07-17")));
            expected.add(new Device(9, "Notebook", LocalDate.parse("2021-07-17")));
            expected.add(new Device(10, "Notebook", LocalDate.parse("2021-07-17")));

            List<Device> result = deviceRepository.findAll();

            AssertionsForInterfaceTypes.assertThat(result).containsExactlyElementsOf(expected);
        }

        @Test
        void works() throws SQLException {
            var device = new Device(7, "Notebook", LocalDate.parse("2021-07-17"));

            Optional<Device> result = deviceRepository.findById(7);

            AssertionsForClassTypes.assertThat(result)
                    .isPresent()
                    .contains(device);
        }

        @Test
        void returns_empty_if_not_in_table() throws SQLException {
            Optional<Device> result = deviceRepository.findById(7777);

            AssertionsForClassTypes.assertThat(result).isEmpty();
        }
    }

    @Nested
    class Deleting {

        @Test
        void works() throws SQLException {
            Device device = new Device("PC", LocalDate.now());

            AssertionsForClassTypes.assertThat(deviceRepository.save(device))
                    .isPresent();


            AssertionsForClassTypes.assertThat(deviceRepository.delete(device))
                    .isTrue();
        }

        @Test
        void device_owned() throws SQLException {
            var possibleDevice = deviceRepository.findById(1);

            AssertionsForClassTypes.assertThat(possibleDevice)
                    .isPresent();

            AssertionsForClassTypes.assertThat(deviceRepository.delete(possibleDevice.get()))
                    .isFalse();
        }

        @Test
        void device_has_no_id() throws SQLException {
            var newDevice = new Device("PC", LocalDate.now());

            AssertionsForClassTypes.assertThat(deviceRepository.delete(newDevice))
                    .isFalse();
        }
    }

    @Nested
    class Registering_and_unregistering {

        @Test
        void works() throws SQLException {
            var device = new Device("PC", LocalDate.now());
            var employee = new Employee("name", LocalDate.now(), "IT", LocalDate.now(), 5555);

            AssertionsForClassTypes.assertThat(deviceRepository.save(device))
                    .isPresent();

            AssertionsForClassTypes.assertThat(employeeRepository.save(employee))
                    .isPresent();

            AssertionsForClassTypes.assertThat(deviceRepository.registerDevice(device, employee))
                    .isPresent();
        }

        @Test
        void device_already_owned_by_someone() throws SQLException {
            var employee = new Employee("name", LocalDate.now(), "IT", LocalDate.now(), 5555);
            var device = new Device("PC", LocalDate.now(), employee);

            AssertionsForClassTypes.assertThat(employeeRepository.save(employee))
                    .isPresent();

            AssertionsForClassTypes.assertThat(deviceRepository.save(device))
                    .isPresent();

            AssertionsForClassTypes.assertThat(deviceRepository.registerDevice(device, employee))
                    .isEmpty();
        }

        @Test
        void device_and_or_employee_has_no_id() throws SQLException {
            var employee = new Employee("name", LocalDate.now(), "IT", LocalDate.now(), 5555);
            var device = new Device("PC", LocalDate.now(), employee);

            AssertionsForClassTypes.assertThat(deviceRepository.registerDevice(device, employee))
                    .isEmpty();
        }

        @Test
        void device_not_in_list_but_has_id() throws SQLException {
            var employee = new Employee("name", LocalDate.now(), "IT", LocalDate.now(), 5555);
            var device = new Device(2244, "PC", LocalDate.now(), employee);

            AssertionsForClassTypes.assertThat(deviceRepository.registerDevice(device, employee))
                    .isEmpty();
        }

    }
}