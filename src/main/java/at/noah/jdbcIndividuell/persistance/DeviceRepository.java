package at.noah.jdbcIndividuell.persistance;

import at.noah.jdbcIndividuell.domain.Device;
import at.noah.jdbcIndividuell.domain.Employee;

import java.sql.SQLException;
import java.util.Optional;

public interface DeviceRepository extends DatabaseRepository<Device> {
    Optional<Device> registerDevice(Device device, Employee employee) throws SQLException;

    Optional<Device> unregisterDevice(Device device) throws SQLException;
}
