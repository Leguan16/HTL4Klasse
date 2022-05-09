package at.noah.jpa.jpaShop;

import at.noah.jpa.jpaShop.domain.Category;
import at.noah.jpa.jpaShop.domain.Manufacturer;
import at.noah.jpa.jpaShop.domain.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManager manager = Persistence.createEntityManagerFactory("at.noah.jpa.jpaShop").createEntityManager();

        Category phoneCategory = new Category("phone");
        Category kitchenCategory = new Category("kitchen");
        Category toolCategory = new Category("tool");

        Manufacturer samsung = new Manufacturer("Samsung");
        Manufacturer nintendo = new Manufacturer("Nintendo");
        Manufacturer kitchenAid = new Manufacturer("Kitchen Aid");

        Product s22Ultra = new Product("S22 Ultra 5G", "A cool phone", BigDecimal.valueOf(1300));
        Product s23Ultra = new Product("S23 Ultra 5G", "A cooler phone", BigDecimal.valueOf(1500));
        Product nintendoSwitch = new Product("Nintendo Switch", BigDecimal.valueOf(500));
        Product wiiU = new Product("Wii U", BigDecimal.valueOf(350));
        Product mixer = new Product("Mixer", "Nice Mixer", BigDecimal.valueOf(140));

        s22Ultra.addCategories(phoneCategory);
        mixer.addCategories(kitchenCategory, toolCategory);

        samsung.addProducts(s22Ultra, s23Ultra);
        nintendo.addProducts(nintendoSwitch, wiiU);
        kitchenAid.addProducts(mixer);

        manager.getTransaction().begin();

        manager.persist(phoneCategory);
        manager.persist(kitchenCategory);
        manager.persist(toolCategory);

        manager.persist(kitchenAid);
        manager.persist(nintendo);
        manager.persist(samsung);

        manager.persist(s22Ultra);
        manager.persist(s23Ultra);
        manager.persist(nintendoSwitch);
        manager.persist(wiiU);
        manager.persist(mixer);

        manager.getTransaction().commit();
        manager.clear();

        List<Manufacturer> manufacturers = manager.createQuery(
                """
                        Select manufacturer from Manufacturer manufacturer
                        """, Manufacturer.class
        ).getResultList();

        manufacturers.forEach(System.out::println);

        manager.close();

    }
}
