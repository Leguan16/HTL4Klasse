package at.noah.jdbcIndividuell.persistance;

import at.noah.jdbcIndividuell.domain.Device;
import at.noah.jdbcIndividuell.domain.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcDeviceRepository implements DeviceRepository{

    private Connection connection;
    private EmployeeRepository employeeRepository;

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

        try (PreparedStatement statement = connection.prepareStatement(sql)){
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

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createDevice(resultSet);
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(Device toSave) throws SQLException {
        if (toSave.getId() == null) {
            return;
        }

        String sql = """
                insert into devices(category, date_of_acquisition, device_owner)
                values ( ?, ?, ? )
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, toSave.getCategory());
            statement.setDate(2, Date.valueOf(toSave.getDateOfAcquisition()));

            if (toSave.getDeviceOwner() != null) {
                if (toSave.getDeviceOwner().getId() != null) {
                    statement.setInt(1, toSave.getDeviceOwner().getId());
                } else {
                    return;
                }
            } else {
                statement.setNull(3, Types.INTEGER);
            }

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {

            }
        }
    }

    @Override
    public void delete(Device toDelete) throws SQLException {
        if (toDelete.getId() == null) {
            return;
        }

        String sql = """
                delete from devices
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, toDelete.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public boolean registerDevice(Device device, Employee employee) {
        return false;
    }

    @Override
    public boolean unregisterDevice(Device device, Employee employee) {
        return false;
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
