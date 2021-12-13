package at.noah.jdbcIndividuell.persistance;

import at.noah.jdbcIndividuell.domain.Device;
import at.noah.jdbcIndividuell.domain.Employee;

public interface DeviceRepository extends DatabaseRepository<Device>{
    public boolean registerDevice(Device device, Employee employee);

    public boolean unregisterDevice(Device device, Employee employee);
}
