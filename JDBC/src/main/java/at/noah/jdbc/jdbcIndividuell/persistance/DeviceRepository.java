package at.noah.jdbc.jdbcIndividuell.persistance;

import at.noah.jdbc.jdbcIndividuell.domain.Device;
import at.noah.jdbc.jdbcIndividuell.domain.Employee;

import java.sql.SQLException;
import java.util.Optional;

public interface DeviceRepository extends DatabaseRepository<Device> {
    Optional<Device> registerDevice(Device device, Employee employee) throws SQLException;

    Optional<Device> unregisterDevice(Device device) throws SQLException;
}
