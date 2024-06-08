socal_shopping_commoditysocal_shopping_ordersocal_shopping_userDROP schema IF EXISTS `socal_shopping`;
CREATE schema socal_shopping;
USE socal_shopping;
DROP TABLE IF EXISTS `socal_shopping_user`;
CREATE TABLE `socal_shopping_user` (
 `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
 `user_type` int NOT NULL COMMENT '用户类型 1 bsocal_shopping_orderuyer 2 seller',
 `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
COMMENT '用户名',
 `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
COMMENT '用户名',
 `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT
NULL COMMENT '地址',
 `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL
COMMENT '手机号',
 PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci 
ROW_FORMAT = Dynamic;
USE socal_shopping;
DROP TABLE IF EXISTS `socal_shopping_commodity`;
CREATE TABLE `socal_shopping_commodity` (
 `commodity_id` bigint NOT NULL AUTO_INCREMENT,
 `commodity_name` varchar(50) CHARACTER SET utf8 COLLATE
utf8_general_ci NOT NULL,
 `commodity_desc` varchar(1000) CHARACTER SET utf8 COLLATE
utf8_general_ci NOT NULL,
 `price` int NOT NULL,
 `available_stock` int NOT NULL,
 `creator_user_id` bigint NOT NULL,
 `total_stock` int NOT NULL DEFAULT '0',
 `lock_stock` int NOT NULL DEFAULT '0',
 FOREIGN KEY (`creator_user_id`)
 REFERENCES socal_shopping_user(user_id),
 PRIMARY KEY (`commodity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8 COLLATE = 
utf8_general_ci ROW_FORMAT = Dynamic;
USE socal_shopping;
DROP TABLE IF EXISTS `socal_shopping_order`;
CREATE TABLE `socal_shopping_order` (
 `order_id` bigint NOT NULL AUTO_INCREMENT,
 `order_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT
NULL,
 `order_status` int NOT NULL, -- 0 invalid 1 pending payment 2 success
 `commodity_id` bigint NOT NULL,
 `user_id` bigint NOT NULL,
 `order_amount` decimal(10, 0) UNSIGNED NOT NULL,
 `create_time` datetime(0) NOT NULL,
 `pay_time` datetime(0) NULL DEFAULT NULL,
 FOREIGN KEY (`commodity_id`)
 REFERENCES socal_shopping_commodity(commodity_id),
 FOREIGN KEY (`user_id`)
 REFERENCES socal_shopping_user(user_id),
 PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = 
utf8_general_ci ROW_FORMAT = Dynamic;