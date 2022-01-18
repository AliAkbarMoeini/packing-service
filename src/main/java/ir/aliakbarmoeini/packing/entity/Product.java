package ir.aliakbarmoeini.packing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product implements Comparable<Product> {

    public Product() {
    }

    public Product(Integer id, Float weight, Integer price) {
        this.id = id;
        this.weight = weight;
        this.price = price;
    }

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "price", nullable = false)
    private Integer price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && weight.equals(product.weight) && price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, price);
    }

    @Override
    public int compareTo(Product p) {
        return weight.compareTo(p.weight);
    }
}