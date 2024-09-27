package com.gameshop.ecommerce.web.product.model;

import com.gameshop.ecommerce.web.review.model.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "image_url", length = 800)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ProductImage> images;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @ElementCollection
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> characteristics;

    @Column(name = "price_with_sale")
    private Integer priceWithSale;

    @OneToOne(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, optional = false, orphanRemoval = true)
    private Inventory inventory;

    @Column(name = "is_sale")
    private Boolean isSale;

    @Column(name = "is_present")
    private Boolean isPresent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<Feature> features = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "average_rate")
    private Double averageRate;

    public void updateAvgRate() {
        this.averageRate = reviews.stream()
                .mapToInt(Review::getRate)
                .average()
                .orElse(0.0);
    }
}