package at.noah.jpa.jpaShop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @Column(
            unique = true,
            nullable = false,
            length = 30
    )
    @Id
    @Getter
    private String title;

    @ManyToMany
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    public Category(String title) {
        this.title = title;
    }

    public void addProducts(Product... products) {

        this.products.addAll(List.of(products));

        for (Product product : this.products) {
            if (product.getCategoriesAsStream().noneMatch(this::equals)) {
                product.addCategories(this);
            }
        }
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Stream<Product> getProductsAsStream() {
        return products.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
