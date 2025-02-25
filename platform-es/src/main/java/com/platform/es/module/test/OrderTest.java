package com.platform.es.module.test;

import com.alibaba.fastjson.JSON;
import com.platform.es.demo.entity.Order;
import com.platform.es.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 16:01
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderTest {
    @Autowired
    private OrderService orderService;

    @GetMapping("/insert")
    public void saveOrder() {
        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            // 随机生成1000到1009的用户id
            int userId = (int) Math.round(Math.random() * (1009 - 1000) + 1000);
            order.setUserId(userId);
            // 随机生成50到100的金额
            int price = (int) Math.round(Math.random() * (10000 - 5000) + 5000);
            order.setPrice(price);
            order.setOrderStatus(2);
            order.setOrderTime(new Date());
            order.setTitle("");
            orderService.saveOrder(order);
        }
    }
    @GetMapping("/queryOrder")
    public String queryOrder() {
        Long orderId = 1100814415454158848L;
        Order order = orderService.queryOrder(orderId);
        log.info("查询的结果：{}", order);
        return JSON.toJSONString(order);
    }
    @GetMapping("/findOrderByOrderIdAndUserId")
    public String findOrderByOrderIdAndUserId() {
        Long orderId = 1100814415454158848L;
        Integer userId=1009;
        Order order = orderService.findOrderByOrderIdAndUserId(orderId,userId);
        log.info("查询的结果：{}", order);
        return JSON.toJSONString(order);
    }


}
