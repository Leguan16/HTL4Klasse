package at.noah.jpa.jpaRental.persistance;

import at.noah.jpa.jpaRental.domain.Car;
import at.noah.jpa.jpaRental.domain.Station;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record CarRepository(EntityManager entityManager) implements Repository<Car, String> {

    @Override
    public List<Car> findAll() {
        return entityManager.createQuery("""
                select car from Car car
                """, Car.class).getResultList();
    }

    @Override
    public Optional<Car> findById(String id) {
        var query = entityManager.createQuery("""
                select car from Car car
                where car.plate = :plate
                """, Car.class
        );

        query.setParameter("plate", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Car save(Car entity) {

        entityManager.getTransaction().begin();

        entity = entityManager.merge(entity);

        entityManager.getTransaction().commit();

        return entity;
    }

    public Set<Car> findCarsAtStation(Station station) {
        var query = entityManager.createQuery("""
                select car from Car car
                where car.location = :station
                """, Car.class
        );

        query.setParameter("station", station);

        return query.getResultStream().collect(Collectors.toSet());
    }

}
