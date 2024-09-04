package com.gameshop.ecommerce.web.order.service;

import com.gameshop.ecommerce.web.order.dao.WebOrderDAO;
import com.gameshop.ecommerce.web.order.model.WebOrder;
import com.gameshop.ecommerce.web.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final WebOrderDAO webOrderDAO;

    public List<WebOrder> getOrders(User user) {
        return webOrderDAO.findByUser(user);
    }
}
