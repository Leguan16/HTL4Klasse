package at.noah.jpa.jpaRunner;

import at.noah.jpa.jpaRunner.domain.Gender;
import at.noah.jpa.jpaRunner.domain.Run;
import at.noah.jpa.jpaRunner.domain.Runner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManager manager = Persistence.createEntityManagerFactory("at.noah.jpa.jpaRunner").createEntityManager();

        Run run = Run.builder().date(LocalDate.now()).build();

        Runner runner = Runner.builder().name("Noah").gender(Gender.MALE).runs(List.of(run)).build();

        run.setRunner(runner);

        manager.getTransaction().begin();

        //manager.persist(run);
        manager.persist(runner);

        manager.getTransaction().commit();

        manager.clear();

        System.out.println(manager.find(Runner.class, 1).toString());
        manager.close();
    }
}
