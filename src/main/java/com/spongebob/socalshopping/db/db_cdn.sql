socal_shopping_commoditysocal_shopping_commodityDROP schema IF EXISTS `socal_shopping_1`;
CREATE schema socal_shopping_1;
USE socal_shopping_1;
DROP TABLE IF EXISTS `socal_shopping_sharding_order_1`;
CREATE TABLE `socal_shopping_sharding_order_1` (
 `order_id` bigint NOT NULL AUTO_INCREMENT,
 `order_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci 
NOT NULL,
 `order_status` int NOT NULL,
 `commodity_id` bigint NOT NULL,
 `user_id` bigint NOT NULL,
 `order_amount` decimal(10, 0) UNSIGNED NOT NULL,
 `create_time` datetime(0) NOT NULL,
 `pay_time` datetime(0) NULL DEFAULT NULL,
 PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = 
utf8_general_ci ROW_FORMAT = Dynamic;
DROP TABLE IF EXISTS `socal_shopping_sharding_order_2`;
CREATE TABLE `socal_shopping_sharding_order_2` (
 `order_id` bigint NOT NULL AUTO_INCREMENT,
 `order_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci 
NOT NULL,
 `order_status` int NOT NULL,
 `commodity_id` bigint NOT NULL,
 `user_id` bigint NOT NULL,
 `order_amount` decimal(10, 0) UNSIGNED NOT NULL,
 `create_time` datetime(0) NOT NULL,
 `pay_time` datetime(0) NULL DEFAULT NULL,
 PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = 
utf8_general_ci ROW_FORMAT = Dynamic;
DROP TABLE IF EXISTS `socal_shopping_user`;
CREATE TABLE `socal_shopping_user` (
 `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
 `user_type` int NOT NULL COMMENT '用户类型 1 buyer 2 seller',
 `name` varchar(20) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '用户名',
 `email` varchar(20) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '用户名',
 `address` varchar(100) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '地址',
 `phone` varchar(50) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '手机号',
 PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb3 
ROW_FORMAT=DYNAMIC;
DROP TABLE IF EXISTS `socal_shopping_commodity`;
CREATE TABLE `socal_shopping_commodity` (
 `commodity_id` bigint NOT NULL AUTO_INCREMENT,
 `commodity_name` varchar(50) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL,
 `commodity_desc` varchar(1000) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL,
 `price` int NOT NULL,
 `available_stock` int NOT NULL,
 `creator_user_id` bigint NOT NULL,
 `total_stock` int NOT NULL DEFAULT '0',
 `lock_stock` int NOT NULL DEFAULT '0',
 PRIMARY KEY (`commodity_id`) USING BTREE,
 KEY `creator_user_id` (`creator_user_id`),
 CONSTRAINT `socal_shopping_commodity_ibfk_1` FOREIGN KEY 
(`creator_user_id`) REFERENCES `socal_shopping_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2353 DEFAULT CHARSET=utf8mb3 
ROW_FORMAT=DYNAMIC;
DROP schema IF EXISTS `socal_shopping_2`;
CREATE schema socal_shopping_2;
USE socal_shopping_2;
DROP TABLE IF EXISTS `socal_shopping_sharding_order_1`;
CREATE TABLE `socal_shopping_sharding_order_1` (
 `order_id` bigint NOT NULL AUTO_INCREMENT,
 `order_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci 
NOT NULL,
 `order_status` int NOT NULL,
 `commodity_id` bigint NOT NULL,
 `user_id` bigint NOT NULL,
 `order_amount` decimal(10, 0) UNSIGNED NOT NULL,
 `create_time` datetime(0) NOT NULL,
 `pay_time` datetime(0) NULL DEFAULT NULL,
 PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = 
utf8_general_ci ROW_FORMAT = Dynamic;
DROP TABLE IF EXISTS `socal_shopping_sharding_order_2`;
CREATE TABLE `socal_shopping_sharding_order_2` (
 `order_id` bigint NOT NULL AUTO_INCREMENT,
 `order_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci 
NOT NULL,
 `order_status` int NOT NULL,
 `commodity_id` bigint NOT NULL,
 `user_id` bigint NOT NULL,
 `order_amount` decimal(10, 0) UNSIGNED NOT NULL,
 `create_time` datetime(0) NOT NULL,
 `pay_time` datetime(0) NULL DEFAULT NULL,
 PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = 
utf8_general_ci ROW_FORMAT = Dynamic;
DROP TABLE IF EXISTS `socal_shopping_user`;
CREATE TABLE `socal_shopping_user` (
 `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
 `user_type` int NOT NULL COMMENT '用户类型 1 buyer 2 seller',
 `name` varchar(20) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '用户名',
 `email` varchar(20) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '用户名',
 `address` varchar(100) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '地址',
 `phone` varchar(50) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL COMMENT '手机号',
 PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb3 
ROW_FORMAT=DYNAMIC;
DROP TABLE IF EXISTS `socal_shopping_commodity`;
CREATE TABLE `socal_shopping_commodity` (
 `commodity_id` bigint NOT NULL AUTO_INCREMENT,
 `commodity_name` varchar(50) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL,
 `commodity_desc` varchar(1000) CHARACTER SET utf8mb3 COLLATE 
utf8mb3_general_ci NOT NULL,
 `price` int NOT NULL,
 `available_stock` int NOT NULL,
 `creator_user_id` bigint NOT NULL,
 `total_stock` int NOT NULL DEFAULT '0',
 `lock_stock` int NOT NULL DEFAULT '0',
 PRIMARY KEY (`commodity_id`) USING BTREE,
 KEY `creator_user_id` (`creator_user_id`),
 CONSTRAINT `socal_shopping_commodity_ibfk_1` FOREIGN KEY 
(`creator_user_id`) REFERENCES `socal_shopping_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2353 DEFAULT CHARSET=utf8mb3 
ROW_FORMAT=DYNAMIC;