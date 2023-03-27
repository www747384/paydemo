/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50541
 Source Host           : localhost:3306
 Source Schema         : paydemo

 Target Server Type    : MySQL
 Target Server Version : 50541
 File Encoding         : 65001

 Date: 24/03/2023 18:12:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_pay_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_channel`;
CREATE TABLE `t_pay_channel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pay_way` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付方式',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '0:正常 -1:禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_pay_channel
-- ----------------------------
INSERT INTO `t_pay_channel` VALUES (1, 'alipay.qrcode.pay', '0', '2023-03-20 12:12:04', NULL);
INSERT INTO `t_pay_channel` VALUES (2, 'wechat.qrcode.pay', '0', '2023-03-20 12:12:46', NULL);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `user_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '111', '2023-03-20 17:58:50');

-- ----------------------------
-- Table structure for t_wallet
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet`;
CREATE TABLE `t_wallet`  (
  `user_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `wallet_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包id',
  `account` decimal(10, 2) NOT NULL COMMENT '余额',
  `currency` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0：正常 -1：禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`wallet_id`) USING BTREE,
  UNIQUE INDEX `wallet_id_unique`(`wallet_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `id_foreignkey` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_wallet
-- ----------------------------
INSERT INTO `t_wallet` VALUES ('1', '1', 800.00, 'CNY', '0', '2023-03-20 18:05:02', '2023-03-24 17:46:52');

-- ----------------------------
-- Table structure for t_wallet_record
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet_record`;
CREATE TABLE `t_wallet_record`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `wallet_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包id',
  `o_account` decimal(10, 2) NOT NULL COMMENT '旧余额',
  `n_account` decimal(10, 2) NOT NULL COMMENT '新余额',
  `operator` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支出+ 支付-',
  `amt` decimal(10, 2) NOT NULL COMMENT '更改金额',
  `paychannel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付渠道',
  `return_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回跳转页面',
  `notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付回调页面',
  `create_time` datetime NOT NULL COMMENT '更改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_wallet_record
-- ----------------------------
INSERT INTO `t_wallet_record` VALUES (1, '1', '1', 900.00, 800.00, '-', 100.00, 'alipay.qrcode.pay', NULL, NULL, '2023-03-22 18:02:57');
INSERT INTO `t_wallet_record` VALUES (2, '1', '1', 800.00, 820.00, '+', 20.00, 'alipay.qrcode.pay', NULL, NULL, '2023-03-24 17:35:37');
INSERT INTO `t_wallet_record` VALUES (3, '1', '1', 820.00, 840.00, '+', 20.00, 'alipay.qrcode.pay', NULL, NULL, '2023-03-24 17:40:50');
INSERT INTO `t_wallet_record` VALUES (4, '1', '1', 840.00, 860.00, '+', 20.00, 'alipay.qrcode.pay', NULL, NULL, '2023-03-24 17:42:25');
INSERT INTO `t_wallet_record` VALUES (5, '1', '1', 860.00, 880.00, '+', 20.00, 'alipay.qrcode.pay', NULL, NULL, '2023-03-24 17:43:14');
INSERT INTO `t_wallet_record` VALUES (6, '1', '1', 880.00, 900.00, '+', 20.00, 'alipay.qrcode.pay', NULL, NULL, '2023-03-24 17:44:37');
INSERT INTO `t_wallet_record` VALUES (7, '1', '1', 900.00, 800.00, '-', 100.00, 'alipay.qrcode.pay', NULL, NULL, '2023-03-24 17:46:52');

SET FOREIGN_KEY_CHECKS = 1;
