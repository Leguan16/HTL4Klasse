package at.noah.jpa.jpaRunner.service;


import at.noah.jpa.jpaRunner.domain.Run;
import at.noah.jpa.jpaRunner.domain.Runner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class JpaService implements Service {

    EntityManager entityManager;

    public JpaService(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Runner> findAll() {
        entityManager.clear();
        return entityManager.createQuery("""
                select runner from Runner runner
                """, Runner.class).getResultList();
    }


    @Override
    public OptionalDouble getAverageSpeed(Runner runner) {
        Optional<Runner> potentialRunner = findById(runner.getId());
        if (potentialRunner.isEmpty()) return OptionalDouble.empty();

        Runner fetchedRunner = potentialRunner.get();

        if (fetchedRunner.getRuns().isEmpty()) return OptionalDouble.empty();

        AtomicReference<Double> totalTime = new AtomicReference<>(0.0);
        AtomicReference<Double> totalKm = new AtomicReference<>(0.0);

        fetchedRunner.getRuns().forEach(
                run -> {
                    totalKm.updateAndGet(value -> value += run.getDistanceInKm());
                    totalTime.updateAndGet(value -> value += run.getMinutes());
                }
        );

        double hours = totalTime.get() / 60;

        return OptionalDouble.of(totalKm.get() / hours);
    }

    @Override
    public Optional<Runner> findById(long id) {
        entityManager.clear();

        var query = entityManager.createQuery("""
                select runner from Runner runner
                left join fetch runner.runs
                where runner.id = :id
                """, Runner.class);

        query.setParameter("id", id);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        } finally {

            entityManager.clear();
        }
    }

    @Override
    public Set<Runner> getAllRunnersWithFinishedMarathon() {
        entityManager.clear();

        var query = entityManager.createQuery(
                """
                        select runner from Runner runner
                        where :distance <= any (select r.distanceInKm from Run r where r member of runner.runs)
                        """, Runner.class);

        query.setParameter("distance", Service.MARATHON_DISTANCE);

        return query.getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Runner save(Runner runner) {
        entityManager.getTransaction().begin();

        runner = entityManager.merge(runner);

        entityManager.getTransaction().commit();

        entityManager.clear();
        return runner;
    }

    @Override
    public Run save(Run run) {
        entityManager.getTransaction().begin();

        run = entityManager.merge(run);

        entityManager.getTransaction().commit();

        entityManager.clear();
        return run;
    }
}
