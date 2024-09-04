package com.gameshop.ecommerce.web.wishlist.model;

import com.gameshop.ecommerce.web.product.model.Product;
import com.gameshop.ecommerce.web.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "wish_list")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}