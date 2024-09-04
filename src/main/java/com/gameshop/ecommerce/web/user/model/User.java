package com.gameshop.ecommerce.web.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gameshop.ecommerce.web.address.Address;
import com.gameshop.ecommerce.web.cart.model.Cart;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 1000)
    private String password;

    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @Column(nullable = false, length = 25)
    private String phone;

    private String userPhoto;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @ColumnDefault("false")
    @Column(name = "email_verified", nullable = false)
    private Boolean isEmailVerified = false;

//    @JsonIgnore
//    @Column(name = "auth_type", nullable = false, length = 40)
//    private String authType;
//
//    @JsonIgnore
//    @Column(name = "auth_provider", length = 55)
//    private String authProvider;

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Cart cart;

    @Column(name = "password_reset_code", unique = true)
    @Size(max = 64)
    private String passwordResetCode;

    @Column(name = "confirmation_code", unique = true)
    @Size(max = 64)
    private String confirmationCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getIsEmailVerified();
    }
}