package ir.aliakbarmoeini.packing.repository;

import ir.aliakbarmoeini.packing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select AVG(p.price) from Product p where p.weight between :floor and :ceil")
    Float averagePriceByWeightBetween(Float floor, Float ceil);
}
