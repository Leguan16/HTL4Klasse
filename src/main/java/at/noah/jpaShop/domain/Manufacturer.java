package at.noah.jpaShop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Entity
@NoArgsConstructor
@ToString

public class Manufacturer {

    @Id
    @GeneratedValue
    private long id;

    @Column(
            nullable = false,
            length = 30
    )
    private String name;

    @OneToMany
    private List<Product> products = new ArrayList<>();

    public Manufacturer(String name) {
        this.name = name;
    }

   public Manufacturer(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public void addProducts(Product... products) {
        this.products.addAll(List.of(products));
    }

    public void removeProducts(Product products) {
        this.products.removeAll(List.of(products));
    }

    public Stream<Product> getProductsAsStream() {
        return products.stream();
    }
}
