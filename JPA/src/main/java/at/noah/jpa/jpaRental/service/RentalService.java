package at.noah.jpa.jpaRental.service;


import at.noah.jpa.jpaRental.domain.Car;
import at.noah.jpa.jpaRental.domain.Rental;
import at.noah.jpa.jpaRental.domain.Station;

import java.util.*;

public interface RentalService {

    Rental save(Rental rental);

    Station save(Station station);

    Car save(Car car);

    List<Station> findAllStations();

    List<Car> findAllCars();

    List<Rental> findAllRentals();

    Optional<Rental> findRentalById(long id);

    Set<Car> findCarsStationedAt(Station station);

    Rental finish(Rental rental, Station station, double drivenKm);
}
