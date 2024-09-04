package com.gameshop.ecommerce.web.product.dao;

import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.product.model.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ProductDAO extends JpaRepository<Product, UUID>, QuerydslPredicateExecutor<Product>,
        QuerydslBinderCustomizer<QProduct> {

    @Override
    default void customize(QuerydslBindings bindings, QProduct root) {
        bindings.bind(String.class).all((StringPath path, Collection<? extends String>
                value) -> {
            BooleanBuilder predicate = new BooleanBuilder();
            for (String s : value) {
                predicate.or(path.containsIgnoreCase(s));
            }
            return Optional.of(predicate);
        });

        bindings.bind(root.price).all((path, value) -> {
                    List<? extends Integer> prices = new ArrayList<>(value);
                    if (prices.size() == 2 && prices.get(0) < prices.get(1)) {
                        return Optional.of(path.between(prices.get(0), prices.get(1)));
                    } else if (prices.size() == 2 && prices.get(0) > prices.get(1)) {
                        return Optional.of(path.between(prices.get(1), prices.get(0)));
                    } else if (prices.size() == 2 && prices.get(0) == prices.get(1)) {
                        return Optional.of(path.eq(prices.get(0)));
                    }
                    return Optional.empty();
                }
        );
    }

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> findAllByIdInOrder(@Param("ids") List<UUID> ids);

    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdCustom(UUID id);

    @Query(value = "SELECT * FROM Product ORDER BY RANDOM() LIMIT 200", nativeQuery = true)
    List<Product> findRandomProducts();
}
