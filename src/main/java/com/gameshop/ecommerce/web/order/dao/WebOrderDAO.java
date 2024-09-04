package com.gameshop.ecommerce.web.order.dao;

import com.gameshop.ecommerce.web.order.model.WebOrder;
import com.gameshop.ecommerce.web.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WebOrderDAO extends JpaRepository<WebOrder, UUID> {
    List<WebOrder> findByUser(User user);


}
