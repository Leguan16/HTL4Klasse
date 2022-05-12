package at.noah.jpa.jpaRental.persistance;

import at.noah.jpa.jpaRental.domain.Rental;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;


public record RentalRepository(EntityManager entityManager) implements Repository<Rental, Long> {

    @Override
    public List<Rental> findAll() {

        return entityManager.createQuery("""
                select rental from Rental rental
                """, Rental.class).getResultList();
    }

    @Override
    public Optional<Rental> findById(Long id) {
        var query = entityManager.createQuery("""
                select rental from Rental rental
                left join fetch rental.car
                where rental.id = :id
                """, Rental.class
        );

        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Rental save(Rental entity) {
        entityManager.getTransaction().begin();

        entity = entityManager.merge(entity);

        entityManager.getTransaction().commit();

        return entity;
    }

}
