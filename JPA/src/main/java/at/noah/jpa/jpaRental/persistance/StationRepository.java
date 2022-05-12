package at.noah.jpa.jpaRental.persistance;

import at.noah.jpa.jpaRental.domain.Station;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public record StationRepository(EntityManager entityManager) implements Repository<Station, Long> {

    @Override
    public List<Station> findAll() {
        return entityManager.createQuery("""
                select station from Station station
                """, Station.class).getResultList();
    }

    @Override
    public Optional<Station> findById(Long id) {
        var query = entityManager.createQuery("""
                select station from Station station
                where station.id = :id
                """, Station.class
        );

        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Station save(Station entity) {
        entityManager.getTransaction().begin();

        entity = entityManager.merge(entity);

        entityManager.getTransaction().commit();

        return entity;
    }
}
