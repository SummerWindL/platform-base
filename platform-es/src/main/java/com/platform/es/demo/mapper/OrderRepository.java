package com.platform.es.demo.mapper;

import com.platform.es.demo.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 15:56
 */
@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    Order findOrderByOrderId(Long orderId);

    /**
     * 根据订单id和用户id查询订单
     * @param orderId
     * @param userId
     * @return
     */
    Order findOrderByOrderIdAndUserId(Long orderId,Integer userId);
}

