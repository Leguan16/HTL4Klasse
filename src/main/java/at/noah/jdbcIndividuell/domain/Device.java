package at.noah.jdbcIndividuell.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Device {
    private final String category;
    private final LocalDate dateOfAcquisition;
    private Integer id;
    private Employee deviceOwner;

    public Device(Integer id, String category, LocalDate dateOfAcquisition, Employee deviceOwner) {
        this.id = id;
        this.category = category;
        this.dateOfAcquisition = dateOfAcquisition;
        this.deviceOwner = deviceOwner;
    }

    public Device(String category, LocalDate dateOfAcquisition) {
        this(null, category, dateOfAcquisition, null);
    }

    public Device(String category, LocalDate dateOfAcquisition, Employee deviceOwner) {
        this(null, category, dateOfAcquisition, deviceOwner);
    }

    public Device(Integer id, String category, LocalDate dateOfAcquisition) {
        this(id, category, dateOfAcquisition, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDateOfAcquisition() {
        return dateOfAcquisition;
    }

    public Employee getDeviceOwner() {
        return deviceOwner;
    }

    public void setDeviceOwner(Employee deviceOwner) {
        this.deviceOwner = deviceOwner;
    }
}
