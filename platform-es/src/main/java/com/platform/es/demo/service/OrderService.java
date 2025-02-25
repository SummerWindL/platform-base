package com.platform.es.demo.service;

import com.platform.es.demo.entity.Order;
import com.platform.es.demo.mapper.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 15:59
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order queryOrder(Long orderId) {
        return orderRepository.findOrderByOrderId(orderId);
    }

    public Order findOrderByOrderIdAndUserId(Long orderId, Integer userId) {
        return orderRepository.findOrderByOrderIdAndUserId(orderId, userId);
    }
}

