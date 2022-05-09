package at.noah.jpa.jpaShop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@AllArgsConstructor

public class Product {

    @Id
    @GeneratedValue
    private long id;

    @Column(
            nullable = false,
            length = 30
    )
    private String title;

    @Column(
            length = 30
    )
    private String description;

    @Column(
            length = 300
    )
    @Positive
    private BigDecimal price;

    @ManyToMany
    private Collection<Category> categories = new ArrayList<>();

    public Product(String title, String description, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public Product(String title, String description, BigDecimal price, Collection<Category> categories) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.categories = categories;
    }

    public Product(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    public void addCategories(Category... categories) {
        this.categories.addAll(List.of(categories));

        for (Category category : categories) {
            if(category.getProductsAsStream().noneMatch(this::equals)) {
                category.addProducts(this);
            }
        }
    }

    public void removeCategories(Category... categories) {
        this.categories.removeAll(List.of(categories));
    }

    public Stream<Category> getCategoriesAsStream() {
        return categories.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(title, product.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
