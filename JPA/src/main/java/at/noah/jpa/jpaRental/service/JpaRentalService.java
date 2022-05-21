package at.noah.jpa.jpaRental.service;


import at.noah.jpa.jpaRental.domain.Car;
import at.noah.jpa.jpaRental.domain.Rental;
import at.noah.jpa.jpaRental.domain.Station;
import at.noah.jpa.jpaRental.domain.exceptions.CarNotAvailableException;
import at.noah.jpa.jpaRental.persistance.CarRepository;
import at.noah.jpa.jpaRental.persistance.RentalRepository;
import at.noah.jpa.jpaRental.persistance.Repository;
import at.noah.jpa.jpaRental.persistance.StationRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public record JpaRentalService(EntityManagerFactory entityManagerFactory) implements RentalService {

    @Override
    public Rental save(Rental rental) {
        if (rental.getEnd() != null && rental.getEnd().isBefore(rental.getBeginning())) {
            throw new IllegalArgumentException("End cannot be before beginning.");
        }

        Repository<Rental, Long> repository = new RentalRepository(entityManagerFactory.createEntityManager());

        if (repository.findAll().stream().anyMatch(checkedRental -> {
            if (rental.getEnd() != null) {
                return checkedRental.getBeginning().isBefore(rental.getEnd());
            }

            return false;
        })) {
            throw new CarNotAvailableException();
        }

        return repository.save(rental);
    }

    @Override
    public Station save(Station station) {
        return new StationRepository(entityManagerFactory.createEntityManager()).save(station);
    }

    @Override
    public Car save(Car car) {
        return new CarRepository(entityManagerFactory.createEntityManager()).save(car);
    }

    @Override
    public List<Station> findAllStations() {
        return new StationRepository(entityManagerFactory.createEntityManager()).findAll();
    }

    @Override
    public List<Car> findAllCars() {
        return new CarRepository(entityManagerFactory.createEntityManager()).findAll();
    }

    @Override
    public List<Rental> findAllRentals() {
        return new RentalRepository(entityManagerFactory.createEntityManager()).findAll();
    }

    @Override
    public Optional<Rental> findRentalById(long id) {
        return new RentalRepository(entityManagerFactory.createEntityManager()).findById(id);
    }

    @Override
    public Set<Car> findCarsStationedAt(Station station) {
        return new CarRepository(entityManagerFactory.createEntityManager()).findCarsAtStation(station);
    }

    @Override
    public Rental finish(Rental rental, Station station, double drivenKm) {

        if (rental == null || station == null || drivenKm < 0 || Double.isNaN(drivenKm) || Double.isInfinite(drivenKm)) {
            throw new IllegalArgumentException();
        }

        if (rental.getDrivenKm() != null) {
            throw new IllegalArgumentException("Rental already finished");
        }

        var rentalRepository = new RentalRepository(entityManagerFactory.createEntityManager());

        rental.setEnd(LocalDateTime.now());
        rental.setReturnStation(station);
        rental.setDrivenKm(drivenKm);

        rental.getCar().addMileage(drivenKm);
        rental.getCar().setLocation(station);

        return rentalRepository.save(rental);
    }
}
