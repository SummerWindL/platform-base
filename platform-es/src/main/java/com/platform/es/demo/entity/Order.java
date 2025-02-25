package com.platform.es.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年02月24日 15:55
 */
@Data
@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "price")
    private Integer price;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "title")
    private String title;

    @Column(name = "order_time")
    private Date orderTime;

}

