-- ds0.user_0 definition
-- 数据库ds0,ds1 分别建立 user_0，user_1 两张表
CREATE TABLE `user_0` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `user_head` varchar(1000) DEFAULT NULL COMMENT '头像',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1100807672061493252 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;