-- 数据库ds0,ds1 分别建立 tb_order_0，tb_order_1 两张表
CREATE TABLE `tb_order_0` (
  `order_id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` int unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `price` int unsigned NOT NULL DEFAULT '0' COMMENT '价格（单位：分）',
  `order_status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '订单状态(1:待付款,2:已付款,3:已取消)',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(100)  NOT NULL DEFAULT '' COMMENT '订单标题',
  PRIMARY KEY (`order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_time` (`order_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';