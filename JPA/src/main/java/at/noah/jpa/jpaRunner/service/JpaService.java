package at.noah.jpa.jpaRunner.service;


import at.noah.jpa.jpaRunner.domain.Run;
import at.noah.jpa.jpaRunner.domain.Runner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public class JpaService implements Service {

    EntityManager entityManager;

    public JpaService(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Runner> findAll() {
        return entityManager.createQuery("""
                select runner from Runner runner
                """, Runner.class).getResultList();
    }


    @Override
    public OptionalDouble getAverageSpeed(Runner runner) {
        return null;
    }

    @Override
    public Optional<Runner> findById(long id) {

        return Optional.ofNullable(entityManager.find(Runner.class, id));

    }

    @Override
    public Set<Runner> getAllRunnersWithFinishedMarathon() {

        var query = entityManager.createQuery(
                """
                        select runner from Runner runner join Runner.runs as run
                        where run.distanceInKm = :distance
                        """, Runner.class);

        query.setParameter("distance", Service.MARATHON_DISTANCE);

        Set<Runner> runners = query.getResultStream().collect(Collectors.toSet());

        return runners;
    }

    @Override
    public Runner save(Runner runner) {
        entityManager.getTransaction().begin();

        entityManager.persist(runner);

        entityManager.getTransaction().commit();

        return runner;
    }

    @Override
    public Run save(Run run) {
        entityManager.getTransaction().begin();

        entityManager.persist(run);

        entityManager.getTransaction().commit();

        return run;
    }
}
