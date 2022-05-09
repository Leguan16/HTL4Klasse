package at.noah.jdbc.jdbcIndividuell.persistance;

import at.noah.jdbc.jdbcIndividuell.domain.Device;
import at.noah.jdbc.jdbcIndividuell.domain.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDeviceRepository implements DeviceRepository {

    private final Connection connection;
    private final EmployeeRepository employeeRepository;

    public JdbcDeviceRepository(Connection connection, EmployeeRepository employeeRepository) {
        this.connection = connection;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Device> findAll() throws SQLException {
        String sql = """
                select *
                from devices
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            List<Device> devices = new ArrayList<>();

            while (resultSet.next()) {
                createDevice(resultSet).ifPresent(devices::add);
            }
            return devices;
        }
    }

    @Override
    public Optional<Device> findById(Integer id) throws SQLException {
        String sql = """
                select *
                from devices
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createDevice(resultSet);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Device> save(Device toSave) throws SQLException {
        if (toSave.getId() != null) {
            return Optional.empty();
        }

        String sql = """
                insert into devices(id, category, date_of_acquisition, device_owner)
                values (?, ?, ?, ? )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, toSave.getCategory());
            statement.setDate(2, Date.valueOf(toSave.getDateOfAcquisition()));

            if (toSave.getDeviceOwner() != null) {
                if (toSave.getDeviceOwner().getId() != null) {
                    statement.setInt(3, toSave.getDeviceOwner().getId());
                } else {
                    return Optional.empty();
                }
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);

                toSave.setId(id);
                return Optional.of(toSave);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean delete(Device toDelete) throws SQLException {
        if (toDelete.getDeviceOwner() != null) {
            return false;
        }

        if (toDelete.getId() == null) {
            return false;
        }

        String sql = """
                delete from devices
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, toDelete.getId());

            statement.executeUpdate();
        }

        return true;
    }

    @Override
    public Optional<Device> registerDevice(Device device, Employee employee) throws SQLException {
        if (device.getDeviceOwner() != null) {
            return Optional.empty();
        }

        if (device.getId() == null || employee.getId() == null) {
            return Optional.empty();
        }

        String sql = """
                update devices
                set device_owner = ?
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employee.getId());
            statement.setInt(2, device.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                device.setDeviceOwner(employee);
                return Optional.of(device);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Device> unregisterDevice(Device device) throws SQLException {
        if (device.getId() == null) {
            return Optional.empty();
        }

        String sql = """
                update devices
                set device_owner = ?
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setNull(1, Types.INTEGER);
            statement.setInt(2, device.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                device.setDeviceOwner(null);
                return Optional.of(device);
            }
        }

        return Optional.empty();
    }

    private Optional<Device> createDevice(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String category = resultSet.getString("category");
        LocalDate dateOfAcquisition = resultSet.getDate("date_of_acquisition").toLocalDate();
        Integer deviceOwnerId = resultSet.getInt("device_owner");

        Optional<Employee> possibleEmployee = employeeRepository.findById(deviceOwnerId);

        return possibleEmployee
                .map(employee -> new Device(id, category, dateOfAcquisition, employee))
                .or(() -> Optional.of(new Device(id, category, dateOfAcquisition)));

    }
}
