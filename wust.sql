/*
 Navicat MySQL Data Transfer

 Source Server         : dev.new.10.1.20.11
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 10.1.20.11:3306
 Source Schema         : wust

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 26/06/2019 15:43:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_app_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_token`;
CREATE TABLE `sys_app_token`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `app_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `login_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `token` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_app_token
-- ----------------------------
INSERT INTO `sys_app_token` VALUES ('383fd050-8760-11e9-a68d-0050568e63cd', 'test', 'test', 'MDAyYmNjNWE=', NULL, NULL, '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', NULL, NULL, '2019-06-05 15:04:54', NULL);

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `relation_table` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `relation_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行标识',
  `relation_field` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列标识，一条数据可以关联多个字段的附件',
  `attachment_key` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attachment_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `attachment_size` int(11) NULL DEFAULT NULL COMMENT '附件大小，KB',
  `attachment_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件位置',
  `attachment_suffix` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件后缀',
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
INSERT INTO `sys_attachment` VALUES ('02967662-7deb-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000004', NULL, '655ad686-2ade-4bf7-8efc-a8ced316e2e0', NULL, 498, 'M00/00/05/CgEUWlzniluAblOaAAfIAGHwvUk1406121', 'file', NULL, NULL, '2019-05-24 14:13:12');
INSERT INTO `sys_attachment` VALUES ('040b4a76-7d3b-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000017', 'excel', '7c320c16-3be5-48a7-bd94-042267fac53e', 'EXPORT201905230000171267997519564702233.xls', 6, 'M00/00/05/CgEUWlzmYxeAJ1xzAAAYAHS_pII956.xls', 'xls', NULL, NULL, '2019-05-23 17:13:23');
INSERT INTO `sys_attachment` VALUES ('0b846499-804c-11e9-a68d-0050568e63cd', 'sys_import_export', '20190527000005', 'log', '9b4abfc1-fe7b-4c73-b408-1a9bbe52a8d9', '20190527000005.txt', 0, 'M00/00/05/CgEUWlzriCKAM5DxAAAAJB0p974132.txt', NULL, NULL, NULL, '2019-05-27 14:52:51');
INSERT INTO `sys_attachment` VALUES ('0cf7b3bc-7e01-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000022', 'log', 'f517bfe3-6c06-4451-a57a-1105eaa06f63', 'IMPORT20190524000022.txt', 0, 'M00/00/05/CgEUWlznr1WAT9MTAAAAJV2_5fU137.txt', NULL, NULL, NULL, '2019-05-24 16:50:58');
INSERT INTO `sys_attachment` VALUES ('105b11d8-7d3a-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000012', 'excel', '2e0bc2c7-1fd9-4165-9184-83b27ea7a9b4', 'EXPORT201905230000123162427296433718275.xls', 6, 'M00/00/05/CgEUWlzmYX6AcfhUAAAYAHS_pII592.xls', 'xls', NULL, NULL, '2019-05-23 17:06:34');
INSERT INTO `sys_attachment` VALUES ('11992319-7dea-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000003', NULL, '46e66c1f-1094-4592-b49d-c76abe54563e', NULL, 48, 'M00/00/05/CgEUWlzniMaAVVoCAADAfh89Ghc1321156', 'file', NULL, NULL, '2019-05-24 14:06:28');
INSERT INTO `sys_attachment` VALUES ('12151739-804b-11e9-a68d-0050568e63cd', 'sys_import_export', '20190527000002', 'log', '6a724afb-5c57-4352-829a-5c6328e6d757', '20190527000002.txt', 0, 'M00/00/05/CgEUWlzrhoCAIdJqAAAAUK1ZR4w143.txt', NULL, NULL, NULL, '2019-05-27 14:45:52');
INSERT INTO `sys_attachment` VALUES ('143307cc-7d07-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000002', NULL, '4f39c446-5939-4198-a50e-2d1e0dafb2fd', NULL, NULL, 'M00/00/05/CgEUWlzmC_SAFhp1AAAYAHS_pII150.xls', NULL, NULL, NULL, '2019-05-23 11:01:36');
INSERT INTO `sys_attachment` VALUES ('1477ead8-8044-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190527000001', 'excel', '98e82343-bcd9-40d6-9b0f-4adb4f4aa236', 'EXPORT201905270000012702575597725201504.xls', 4, 'M00/00/05/CgEUWlzresaAaOeqAAASABvHBCI161.xls', 'xls', NULL, NULL, '2019-05-27 13:55:50');
INSERT INTO `sys_attachment` VALUES ('18c9f1b8-7e01-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190524000001', 'excel', '72a2d522-96ab-4dbd-b026-f8f903fab1a2', 'EXPORT201905240000017820190213781532664.xls', 8, 'M00/00/05/CgEUWlznr2mAALkuAAAiAF-lwlE874.xls', 'xls', NULL, NULL, '2019-05-24 16:51:18');
INSERT INTO `sys_attachment` VALUES ('1abed78c-7d3a-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000013', 'excel', 'c89132d3-2dbf-4304-86bf-60189ae7cc44', 'EXPORT201905230000138055911855022112296.xls', 6, 'M00/00/05/CgEUWlzmYY-AEXOeAAAYAHS_pII649.xls', 'xls', NULL, NULL, '2019-05-23 17:06:52');
INSERT INTO `sys_attachment` VALUES ('1daeb552-7d20-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000004', NULL, '87552d0a-bee6-4df4-b799-4f54dd815a35', 'EXPORT201905230000047973110146281613203.xls', 6, 'M00/00/05/CgEUWlzmNfWAfx_7AAAYAHS_pII803.xls', 'xls', NULL, NULL, '2019-05-23 14:00:50');
INSERT INTO `sys_attachment` VALUES ('25d02d35-7dfd-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000011', 'log', '5052954a-f281-46e3-94a6-6376d3f46b21', 'IMPORT201905240000113426673920408109431.txt', 0, 'M00/00/05/CgEUWlznqLaAeeMeAAAAAAAAAAA372.txt', 'txt', NULL, NULL, '2019-05-24 16:23:02');
INSERT INTO `sys_attachment` VALUES ('29c9cc9d-7e00-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000019', 'log', '9ff5cc4e-b49b-4ef3-9e75-9d3e718f1138', 'IMPORT20190524000019.txt', 0, 'M00/00/05/CgEUWlznrdiAIU0lAAAAg-x_mAw326.txt', NULL, NULL, NULL, '2019-05-24 16:44:37');
INSERT INTO `sys_attachment` VALUES ('2d14805a-8cb1-11e9-a68d-0050568e63cd', 'sys_import_export', '20190612000006', 'excel', 'fa60b249-cf8b-465f-8c61-e763c077da56', '201906120000067126880602619014891.xls', 5, 'M00/00/06/CgEUWl0AU7WAWKduAAAUAHtECfo756.xls', 'xls', 'd0225332-875f-11e9-a68d-0050568e63cd', '10001', '2019-06-12 09:27:00');
INSERT INTO `sys_attachment` VALUES ('2ee80026-7d09-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000003', NULL, '1dcc4cb1-0a70-4ed6-8714-f01da5dfd37e', 'EXPORT201905230000037955646935577898557.xls', 6, 'M00/00/05/CgEUWlzmD3yAcTgjAAAYAHS_pII356.xls', 'xls', NULL, NULL, '2019-05-23 11:16:40');
INSERT INTO `sys_attachment` VALUES ('3305faa7-7dfb-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000008', NULL, 'dfdb4933-9ab8-4a4c-ab59-762b6dc8f519', 'user_template (1).xls', 27, 'M00/00/05/CgEUWlznpYSAZ-eAAABsALJY_D0395.xls', 'xls', NULL, NULL, '2019-05-24 16:09:05');
INSERT INTO `sys_attachment` VALUES ('3b6d6a5d-804b-11e9-a68d-0050568e63cd', 'sys_import_export', '20190527000003', 'log', 'c2bf0e2f-3475-4bc9-9468-d1fb21533d39', '20190527000003.txt', 0, 'M00/00/05/CgEUWlzrhsWAXgj2AAAAu2iejaA875.txt', NULL, NULL, NULL, '2019-05-27 14:47:01');
INSERT INTO `sys_attachment` VALUES ('4afd0d60-7d39-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000007', 'excel', 'ba650b3e-799e-49e7-8a7c-d876dfabb338', 'EXPORT201905230000075043560051379079811.xls', 6, 'M00/00/05/CgEUWlzmYDOAd7HAAAAYAHS_pII995.xls', 'xls', NULL, NULL, '2019-05-23 17:01:03');
INSERT INTO `sys_attachment` VALUES ('4b560ee8-7deb-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000005', NULL, '892f3e5f-59f4-49a5-b20f-3d2564848cc1', NULL, 498, 'M00/00/05/CgEUWlznitWAPC1aAAfIAGHwvUk1178077', 'file', NULL, NULL, '2019-05-24 14:15:14');
INSERT INTO `sys_attachment` VALUES ('4e8213f9-7dfe-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000014', 'log', '6306a59d-609c-4f90-bde4-f348f220cb4b', 'IMPORT201905240000148709546690136323959.txt', 0, 'M00/00/05/CgEUWlznqruAY_BoAAAAAAAAAAA968.txt', 'txt', NULL, NULL, '2019-05-24 16:31:20');
INSERT INTO `sys_attachment` VALUES ('5090b37d-7d39-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000008', 'excel', 'e3cbb9ba-5973-41f7-8545-7e4817cbdce0', 'EXPORT201905230000085597096862550840999.xls', 6, 'M00/00/05/CgEUWlzmYDyABYofAAAYAHS_pII237.xls', 'xls', NULL, NULL, '2019-05-23 17:01:12');
INSERT INTO `sys_attachment` VALUES ('57b1ceb3-7dfd-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000012', 'log', 'ba023675-8f84-43cd-b51c-f445c60e433d', 'IMPORT201905240000123564031614194746419.txt', 0, 'M00/00/05/CgEUWlznqRyANMtIAAAAAAAAAAA230.txt', 'txt', NULL, NULL, '2019-05-24 16:24:26');
INSERT INTO `sys_attachment` VALUES ('57c30127-7d3b-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000018', 'excel', '5ef0a242-8f6a-4654-ab37-31c88ecaeb62', 'EXPORT20190523000018588311120718670211.xls', 6, 'M00/00/05/CgEUWlzmY6OAT8wfAAAYAHS_pII897.xls', 'xls', NULL, NULL, '2019-05-23 17:15:44');
INSERT INTO `sys_attachment` VALUES ('5c8ab8fa-7d3a-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000014', 'excel', 'd5621f07-8f74-49cd-8d8c-b1a97ede27ab', 'EXPORT201905230000141688807086379401636.xls', 6, 'M00/00/05/CgEUWlzmYf6ACaIlAAAYAHS_pII564.xls', 'xls', NULL, NULL, '2019-05-23 17:08:42');
INSERT INTO `sys_attachment` VALUES ('61dac44f-7d3a-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000015', 'excel', '6ebb8cd3-6a38-4c86-816d-3cbd41398331', 'EXPORT201905230000151699296800872485802.xls', 6, 'M00/00/05/CgEUWlzmYgaAdBBpAAAYAHS_pII220.xls', 'xls', NULL, NULL, '2019-05-23 17:08:51');
INSERT INTO `sys_attachment` VALUES ('65b5830e-7d3b-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000019', 'excel', 'daabc862-b029-4114-9001-341aa8f42cab', 'EXPORT20190523000019205733345293594404.xls', 6, 'M00/00/05/CgEUWlzmY7qAdROhAAAYAHS_pII952.xls', 'xls', NULL, NULL, '2019-05-23 17:16:07');
INSERT INTO `sys_attachment` VALUES ('6b179338-7d39-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000009', 'excel', '193b0444-b7b8-45c9-abaf-37f98febc6c6', 'EXPORT201905230000094485477462263048096.xls', 6, 'M00/00/05/CgEUWlzmYGmAcH8XAAAYAHS_pII969.xls', 'xls', NULL, NULL, '2019-05-23 17:01:57');
INSERT INTO `sys_attachment` VALUES ('6d0c9018-8739-11e9-a68d-0050568e63cd', 'sys_import_export', '20190605000001', 'excel', '150d2b0f-c89c-4674-85f1-08ae14a13d99', '201906050000011635937326771413781.xls', 0, 'M00/00/06/CgEUWlz3J1eASY89AAAAAAAAAAA393.xls', 'xls', NULL, NULL, '2019-06-05 10:27:12');
INSERT INTO `sys_attachment` VALUES ('73694a13-7d3b-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000020', 'excel', '8862015c-3515-48aa-9ad9-0e01c2492855', 'EXPORT201905230000208519539049777996228.xls', 6, 'M00/00/05/CgEUWlzmY9GAGlYIAAAYAHS_pII536.xls', 'xls', NULL, NULL, '2019-05-23 17:16:30');
INSERT INTO `sys_attachment` VALUES ('7774a68a-867c-11e9-a68d-0050568e63cd', 'sys_import_export', '20190604000001', 'excel', 'd5bce682-7df2-4c4d-b64a-f957341b9f09', '201906040000015514669545908846850.xls', 5, 'M00/00/06/CgEUWlz16lOALzaYAAAUAFaJFKg944.xls', 'xls', NULL, NULL, '2019-06-04 11:54:34');
INSERT INTO `sys_attachment` VALUES ('7824550b-7d3b-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000021', 'excel', '083733b0-48d7-405f-b61a-0ccebdf0463c', 'EXPORT201905230000215338051251131137607.xls', 6, 'M00/00/05/CgEUWlzmY9mAWDuQAAAYAHS_pII230.xls', 'xls', NULL, NULL, '2019-05-23 17:16:38');
INSERT INTO `sys_attachment` VALUES ('79853207-7d3b-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000022', 'excel', 'fb47f5dc-da82-45a3-8304-6c437f758125', 'EXPORT201905230000228528489027558186959.xls', 6, 'M00/00/05/CgEUWlzmY9yARyn5AAAYAHS_pII926.xls', 'xls', NULL, NULL, '2019-05-23 17:16:40');
INSERT INTO `sys_attachment` VALUES ('7a176249-8739-11e9-a68d-0050568e63cd', 'sys_import_export', '20190605000002', 'excel', 'bd13a6e1-b054-461f-b25f-4812c456a471', '20190605000002514243554235517920.xls', 0, 'M00/00/06/CgEUWlz3J22ARMSWAAAAAAAAAAA666.xls', 'xls', NULL, NULL, '2019-06-05 10:27:34');
INSERT INTO `sys_attachment` VALUES ('7a507bda-7d30-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000006', 'excel', 'f3c4779e-7c25-4a07-90f9-901f23dce2d2', 'EXPORT201905230000066642273539588571086.xls', 6, 'M00/00/05/CgEUWlzmUWmAAsz3AAAYAHS_pII189.xls', 'xls', NULL, NULL, '2019-05-23 15:57:57');
INSERT INTO `sys_attachment` VALUES ('7c59aad5-8c18-11e9-a68d-0050568e63cd', 'sys_import_export', '20190611000012', 'excel', 'e60d7155-30b0-44e7-a71b-7c7dd0e30bc5', '201906110000126695413717848303361.xls', 4, 'M00/00/06/CgEUWlz_U4qAN9KaAAASAH_Pkw8762.xls', 'xls', '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004', '2019-06-11 15:14:00');
INSERT INTO `sys_attachment` VALUES ('7cfc25d7-7deb-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000006', NULL, '544d1664-53d7-4835-aa66-c6119abb552c', '503.xls', 165, 'M00/00/05/CgEUWlzniyiAf1ICAAKUALKHXpA732.xls', 'xls', NULL, NULL, '2019-05-24 14:16:37');
INSERT INTO `sys_attachment` VALUES ('84a1edb0-7dfe-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000015', 'log', '3fb0755f-335d-41d5-9e7a-34949ef11cf3', 'IMPORT201905240000156200786854207177817.txt', 0, 'M00/00/05/CgEUWlznqxWAd1zJAAAAAAAAAAA777.txt', 'txt', NULL, NULL, '2019-05-24 16:32:51');
INSERT INTO `sys_attachment` VALUES ('8576562f-8c18-11e9-a68d-0050568e63cd', 'sys_import_export', '20190611000013', 'excel', 'ce268fc4-1b80-48c2-9564-a0e32b53f47a', '201906110000136785239408308655778.xls', 4, 'M00/00/06/CgEUWlz_U5mAIzC6AAASADBjHTg992.xls', 'xls', '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004', '2019-06-11 15:14:15');
INSERT INTO `sys_attachment` VALUES ('891c62ce-7dff-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000017', 'log', '291ad81a-9e77-4c35-bafa-2b58bc1d4c4c', NULL, NULL, 'M00/00/05/CgEUWlznrMqAARy9AAAAg-x_mAw6757265', NULL, NULL, NULL, '2019-05-24 16:40:08');
INSERT INTO `sys_attachment` VALUES ('8de1b087-8c18-11e9-a68d-0050568e63cd', 'sys_import_export', '20190611000014', 'excel', '41bafc32-c834-4a2f-96e1-36e304f8d83e', '201906110000141164858345991812356.xls', 5, 'M00/00/06/CgEUWlz_U6eAfvmxAAAUAHtECfo524.xls', 'xls', 'd0225332-875f-11e9-a68d-0050568e63cd', '10001', '2019-06-11 15:14:29');
INSERT INTO `sys_attachment` VALUES ('9a1c2ee7-804b-11e9-a68d-0050568e63cd', 'sys_import_export', '20190527000004', 'log', 'd457db4b-cb96-4c94-8d85-0b503d158456', '20190527000004.txt', 0, 'M00/00/05/CgEUWlzrh2SART6WAAAAJB0p974704.txt', NULL, NULL, NULL, '2019-05-27 14:49:40');
INSERT INTO `sys_attachment` VALUES ('a7266c2d-7d39-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000010', 'excel', 'ed1fcead-662d-401f-b287-e8091026a2ee', 'EXPORT201905230000102502034276491270244.xls', 6, 'M00/00/05/CgEUWlzmYM2AeT01AAAYAHS_pII129.xls', 'xls', NULL, NULL, '2019-05-23 17:03:38');
INSERT INTO `sys_attachment` VALUES ('a9ee7d94-7dfd-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000013', 'log', '574b194c-9f07-45f3-94e4-f7d9c880df0a', 'IMPORT201905240000136266275516096785148.txt', 0, 'M00/00/05/CgEUWlznqaaAYP24AAAAAAAAAAA886.txt', 'txt', NULL, NULL, '2019-05-24 16:26:44');
INSERT INTO `sys_attachment` VALUES ('b6592398-7dff-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000018', 'log', '3ad3c28c-9c6c-49d7-87be-49a6f023ec21', NULL, NULL, 'M00/00/05/CgEUWlznrRaAEgDgAAAAg-x_mAw3843523', NULL, NULL, NULL, '2019-05-24 16:41:24');
INSERT INTO `sys_attachment` VALUES ('b775df25-7d39-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000011', 'excel', '568c709c-95dc-436a-8ece-b84174c26bb7', 'EXPORT201905230000112518308817725654729.xls', 6, 'M00/00/05/CgEUWlzmYOmAPyRyAAAYAHS_pII140.xls', 'xls', NULL, NULL, '2019-05-23 17:04:05');
INSERT INTO `sys_attachment` VALUES ('c552cbdd-8c15-11e9-a68d-0050568e63cd', 'sys_import_export', '20190611000001', 'excel', 'c5da11cd-5e0f-4caa-878c-00389dbe9cfb', '201906110000019016096280363747746.xls', 5, 'M00/00/06/CgEUWlz_TvyAXiHIAAAUAHdvBRA942.xls', 'xls', NULL, NULL, '2019-06-11 14:54:34');
INSERT INTO `sys_attachment` VALUES ('c7981448-804a-11e9-a68d-0050568e63cd', 'sys_import_export', '20190527000001', 'log', '624d722d-4a6c-41d3-93dc-cb530dea0112', '20190527000001.txt', 0, 'M00/00/05/CgEUWlzrhgOAC9mKAAAAUK1ZR4w244.txt', NULL, NULL, NULL, '2019-05-27 14:43:47');
INSERT INTO `sys_attachment` VALUES ('c7fe37fc-7e00-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000020', 'log', 'b16fbe99-bae4-4c6e-985d-023dcd763eae', 'IMPORT20190524000020.txt', 1, 'M00/00/05/CgEUWlznruGAZrPFAAAFLGmlL9M546.txt', NULL, NULL, NULL, '2019-05-24 16:49:03');
INSERT INTO `sys_attachment` VALUES ('c8169dd3-8c15-11e9-a68d-0050568e63cd', 'sys_import_export', '20190611000002', 'excel', '15ca27e5-2f32-40d5-87e2-58fb15b02cad', '201906110000028818336756885163410.xls', 4, 'M00/00/06/CgEUWlz_TwCAXtS-AAASAGVKxx4772.xls', 'xls', NULL, NULL, '2019-06-11 14:54:39');
INSERT INTO `sys_attachment` VALUES ('c9a00935-8c15-11e9-a68d-0050568e63cd', 'sys_import_export', '20190611000003', 'excel', 'c46a7b7c-c08c-4d86-83dc-1525037fa74a', '201906110000035319596981332041025.xls', 5, 'M00/00/06/CgEUWlz_TwOAcyWkAAAUAHtECfo185.xls', 'xls', NULL, NULL, '2019-06-11 14:54:41');
INSERT INTO `sys_attachment` VALUES ('cb988655-7d3a-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000016', 'excel', '1720bbd8-0525-42ba-aca7-8adcfff93f1a', 'EXPORT201905230000165713113012598728082.xls', 6, 'M00/00/05/CgEUWlzmYriAGJmbAAAYAHS_pII349.xls', 'xls', NULL, NULL, '2019-05-23 17:11:48');
INSERT INTO `sys_attachment` VALUES ('cc45e28a-7d3b-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000023', 'excel', 'bcf95fa8-e2bf-425c-852c-e834204afc0d', 'EXPORT201905230000232001157906723149103.xls', 6, 'M00/00/05/CgEUWlzmZGeAJKgGAAAYAHS_pII419.xls', 'xls', NULL, NULL, '2019-05-23 17:18:59');
INSERT INTO `sys_attachment` VALUES ('d0620f13-8c17-11e9-a68d-0050568e63cd', 'sys_import_export', '20190611000010', 'excel', '756ab838-189a-4879-819d-a226024d8dde', '201906110000105257296528779688053.xls', 4, 'M00/00/06/CgEUWlz_UmmAOp9LAAASAH_Pkw8243.xls', 'xls', NULL, NULL, '2019-06-11 15:09:11');
INSERT INTO `sys_attachment` VALUES ('d314d21d-7dfa-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000007', NULL, 'aca1cceb-5d29-493c-8672-b914d3ecf0aa', 'user_template (1).xls', 27, 'M00/00/05/CgEUWlznpOOAISQrAABsALJY_D0518.xls', 'xls', NULL, NULL, '2019-05-24 16:06:24');
INSERT INTO `sys_attachment` VALUES ('d829bef5-8cad-11e9-a68d-0050568e63cd', 'sys_import_export', '20190612000001', 'excel', '0b81e117-da6a-41ac-8009-7aa3302466cd', '201906120000015739258410678366718.xls', 4, 'M00/00/06/CgEUWl0ATh6AMVmJAAASAH_Pkw8805.xls', 'xls', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005', '2019-06-12 09:03:09');
INSERT INTO `sys_attachment` VALUES ('d9e200e2-8cad-11e9-a68d-0050568e63cd', 'sys_import_export', '20190612000002', 'excel', 'c42229c4-f2ac-4b02-a73d-b215570ece04', '201906120000027029211671299261450.xls', 4, 'M00/00/06/CgEUWl0ATiGAXkpbAAASADBjHTg386.xls', 'xls', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005', '2019-06-12 09:03:12');
INSERT INTO `sys_attachment` VALUES ('db738158-8cad-11e9-a68d-0050568e63cd', 'sys_import_export', '20190612000003', 'excel', '55fe822f-8bc7-4e43-bd06-b68e4c5cac88', '201906120000034294003665576701279.xls', 4, 'M00/00/06/CgEUWl0ATiSAEB_wAAASAGVKxx4926.xls', 'xls', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005', '2019-06-12 09:03:15');
INSERT INTO `sys_attachment` VALUES ('dcfb4373-8cad-11e9-a68d-0050568e63cd', 'sys_import_export', '20190612000004', 'excel', 'c7f8fa9c-a1fe-4ff4-b9a5-579fd1be4f8c', '201906120000044848960941757623837.xls', 5, 'M00/00/06/CgEUWl0ATiaAVFjxAAAUAHtECfo974.xls', 'xls', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005', '2019-06-12 09:03:17');
INSERT INTO `sys_attachment` VALUES ('df7e335b-8cad-11e9-a68d-0050568e63cd', 'sys_import_export', '20190612000005', 'excel', '92ab2145-20ed-4a1d-b26c-ce65619b37a5', '201906120000059012441174578152174.xls', 5, 'M00/00/06/CgEUWl0ATiqAVuHgAAAUAHdvBRA673.xls', 'xls', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005', '2019-06-12 09:03:21');
INSERT INTO `sys_attachment` VALUES ('e2b103d7-8739-11e9-a68d-0050568e63cd', 'sys_import_export', '20190605000003', 'excel', '9260c25a-f22e-40b3-8980-88487e1d5044', '201906050000032337877215231430364.xls', 5, 'M00/00/06/CgEUWlz3KB2ASvYqAAAUAJZhU04722.xls', 'xls', NULL, NULL, '2019-06-05 10:30:29');
INSERT INTO `sys_attachment` VALUES ('e58fc1d2-7dfe-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000016', 'log', 'dbc0bf4e-11fe-48d9-8ed0-f43f8f408165', 'IMPORT20190524000016284767680921998654.txt', 0, 'M00/00/05/CgEUWlznq7iAdpb-AAAAAAAAAAA824.txt', 'txt', NULL, NULL, '2019-05-24 16:35:33');
INSERT INTO `sys_attachment` VALUES ('e59953cc-7e00-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000021', 'log', '69abd60a-367e-4b99-96bd-aea498763e06', 'IMPORT20190524000021.txt', 1, 'M00/00/05/CgEUWlznrxOAbJvhAAAFLGmlL9M820.txt', NULL, NULL, NULL, '2019-05-24 16:49:52');
INSERT INTO `sys_attachment` VALUES ('f007f3bc-85b8-11e9-a68d-0050568e63cd', 'sys_import_export', '20190603000002', 'excel', '5ae685bf-3017-4eca-8d4d-6c0fc392bb73', '201906030000021658384131133587995.xls', 4, 'M00/00/06/CgEUWlz0okmAZjUpAAASAIN7yqg090.xls', 'xls', NULL, NULL, '2019-06-03 12:34:55');
INSERT INTO `sys_attachment` VALUES ('f762b4fa-7de9-11e9-a68d-0050568e63cd', 'sys_import_export', 'IMPORT20190524000002', NULL, '15166d63-2874-43ce-98f0-35b88e775914', NULL, 498, 'M00/00/05/CgEUWlzniJuAIuJXAAfIAGHwvUk9679993', 'file', NULL, NULL, '2019-05-24 14:05:44');
INSERT INTO `sys_attachment` VALUES ('f98f61cb-880a-11e9-a68d-0050568e63cd', 'sys_import_export', '20190606000001', 'excel', '108ac58c-0805-41a2-89eb-8a5f60c99a9f', '201906060000018494550340362088844.xls', 5, 'M00/00/06/CgEUWlz4hueAR9noAAAUAGWN7Yw194.xls', 'xls', NULL, NULL, '2019-06-06 11:27:12');
INSERT INTO `sys_attachment` VALUES ('fc585cf0-85b0-11e9-a68d-0050568e63cd', 'sys_import_export', '20190603000001', 'excel', '1442ae3b-b07d-4f4e-a145-4b9d928e9aec', '201906030000012813129392315674187.xls', 4, 'M00/00/06/CgEUWlz0lPGAB4YGAAASANSFhbA374.xls', 'xls', NULL, NULL, '2019-06-03 11:38:00');
INSERT INTO `sys_attachment` VALUES ('fc72ec0a-7d2f-11e9-a68d-0050568e63cd', 'sys_import_export', 'EXPORT20190523000005', 'excel', 'efb9bf71-faec-4949-a1ee-0904f51fea71', 'EXPORT201905230000053823660724575111992.xls', 6, 'M00/00/05/CgEUWlzmUJWAJRrAAAAYAHS_pII982.xls', 'xls', NULL, NULL, '2019-05-23 15:54:26');

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级公司编码',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `leader` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司负责人',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES ('63ecca81-85de-11e9-a68d-0050568e63cd', '007', NULL, '华为技术有限公司', '', '', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-03 17:03:01', NULL, NULL, NULL);
INSERT INTO `sys_company` VALUES ('6f60e913-85de-11e9-a68d-0050568e63cd', '008', NULL, '腾讯科技有限公司', '', '', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-03 17:03:20', NULL, NULL, NULL);
INSERT INTO `sys_company` VALUES ('a65cbd8e-85ac-11e9-a68d-0050568e63cd', '003', NULL, '联想（中国）有限公司', '', '', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-03 11:06:58', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_data_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_privilege`;
CREATE TABLE `sys_data_privilege`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `business_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限定义表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_privilege
-- ----------------------------
INSERT INTO `sys_data_privilege` VALUES ('a6d1c14b-8b47-11e9-a68d-0050568e63cd', '我的导入导出', NULL, NULL, NULL, NULL, '2019-06-26 11:32:07', NULL);
INSERT INTO `sys_data_privilege` VALUES ('ae5c122c-8cb5-11e9-a68d-0050568e63cd', '操作日志', NULL, NULL, NULL, NULL, '2019-06-26 11:32:07', NULL);

-- ----------------------------
-- Table structure for sys_data_privilege_rules
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_privilege_rules`;
CREATE TABLE `sys_data_privilege_rules`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_privilege_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `expression` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_privilege_rules
-- ----------------------------
INSERT INTO `sys_data_privilege_rules` VALUES ('048e116d-8cf5-11e9-a68d-0050568e63cd', 'ae5c122c-8cb5-11e9-a68d-0050568e63cd', '100910', 'creater_id IN(SELECT u1.relation_id FROM sys_organization role LEFT JOIN sys_organization u ON role.id = u.pid  LEFT JOIN sys_organization u1 ON role.id = u1.pid WHERE u.relation_id = #currentUserId)', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000', NULL, NULL, '2019-06-12 17:32:38', NULL);
INSERT INTO `sys_data_privilege_rules` VALUES ('6692cb75-93f3-11e9-a68d-0050568e63cd', 'a6d1c14b-8b47-11e9-a68d-0050568e63cd', '100905', 'creater_id = #currentUserId', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000', NULL, NULL, '2019-06-21 15:08:41', NULL);

-- ----------------------------
-- Table structure for sys_data_source
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source`;
CREATE TABLE `sys_data_source`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `jdbc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `jdbc_username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `jdbc_password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `jdbc_driver` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_source
-- ----------------------------
INSERT INTO `sys_data_source` VALUES ('2152ee1a-93f2-11e9-a68d-0050568e63cd', '63ecca81-85de-11e9-a68d-0050568e63cd', 'jdbc:mysql://10.1.20.11:3306/wust_hwjsyxgs_8d07c?useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true', 'wust_hwjsyxgs_8d07c', 'f7b48', 'com.mysql.cj.jdbc.Driver', '华为技术有限公司的数据源', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000', '2019-06-21 14:59:36', NULL, NULL, NULL);
INSERT INTO `sys_data_source` VALUES ('29646e44-93f2-11e9-a68d-0050568e63cd', '6f60e913-85de-11e9-a68d-0050568e63cd', 'jdbc:mysql://10.1.20.11:3306/wust_txkjyxgs_cd61e?useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true', 'wust_txkjyxgs_cd61e', '76fd2', 'com.mysql.cj.jdbc.Driver', '腾讯科技有限公司的数据源', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000', '2019-06-21 14:59:49', NULL, NULL, NULL);
INSERT INTO `sys_data_source` VALUES ('2a4eb869-93f2-11e9-a68d-0050568e63cd', 'a65cbd8e-85ac-11e9-a68d-0050568e63cd', 'jdbc:mysql://10.1.20.11:3306/wust_lxzgyxgs_c3e4a?useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true', 'wust_lxzgyxgs_c3e4a', 'f2830', 'com.mysql.cj.jdbc.Driver', '联想（中国）有限公司的数据源', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000', '2019-06-21 14:59:51', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pcode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级部门编码',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `leader` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门负责人',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES ('21e4b18f-85b8-11e9-a68d-0050568e63cd', '003', NULL, '云技术开发部', '', '', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-03 12:29:10', NULL, NULL, NULL);
INSERT INTO `sys_department` VALUES ('cab49a51-85b7-11e9-a68d-0050568e63cd', '001', NULL, '人事部', '', '', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-03 12:26:43', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_fields
-- ----------------------------
DROP TABLE IF EXISTS `sys_fields`;
CREATE TABLE `sys_fields`  (
  `id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `table_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `field_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `field_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `field_length` tinyint(1) NULL DEFAULT NULL COMMENT '字段长度',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `display_flag` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `required` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `order_index` tinyint(1) NULL DEFAULT NULL COMMENT '排序索引',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '垂直表结构，用于存储字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_import_export
-- ----------------------------
DROP TABLE IF EXISTS `sys_import_export`;
CREATE TABLE `sys_import_export`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `batch_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `module_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operation_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_batch_no`(`batch_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_import_export
-- ----------------------------
INSERT INTO `sys_import_export` VALUES ('028b8eda-7deb-11e9-a68d-0050568e63cd', 'IMPORT20190524000004', 'user', NULL, '100601', '100501', NULL, '2019-05-24 14:13:12', NULL, NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('03ff85b6-7d3b-11e9-a68d-0050568e63cd', 'EXPORT20190523000017', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:13:23', '2019-05-23 17:13:23', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('08d54e33-8c16-11e9-a68d-0050568e63cd', '20190611000004', 'company', NULL, '100602', '100501', NULL, '2019-06-11 14:56:27', NULL, '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('0a4eab01-8c16-11e9-a68d-0050568e63cd', '20190611000005', 'role', NULL, '100602', '100501', NULL, '2019-06-11 14:56:30', NULL, '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('0b4dbeb8-804c-11e9-a68d-0050568e63cd', '20190527000005', 'role', NULL, '100601', '100502', NULL, '2019-05-27 14:52:50', '2019-05-27 14:52:50', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('0c60b6f7-8c16-11e9-a68d-0050568e63cd', '20190611000006', 'user', NULL, '100602', '100501', NULL, '2019-06-11 14:56:33', NULL, '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('0cdb4630-7e01-11e9-a68d-0050568e63cd', 'IMPORT20190524000022', 'user', NULL, '100601', '100502', NULL, '2019-05-24 16:50:58', '2019-05-24 16:50:58', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('104f4875-7d3a-11e9-a68d-0050568e63cd', 'EXPORT20190523000012', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:06:34', '2019-05-23 17:06:34', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('1203c5ee-804b-11e9-a68d-0050568e63cd', '20190527000002', 'role', NULL, '100601', '100504', '找不到[classpath:import.xsd]或[importExport/import/xml/admin_role.xml]文件', '2019-05-27 14:45:52', '2019-05-27 14:45:52', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('13f87e39-7d07-11e9-a68d-0050568e63cd', 'EXPORT20190523000002', 'user', NULL, '2', 'doing', NULL, '2019-05-23 11:01:36', '2019-05-23 11:01:36', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('143adebf-8044-11e9-a68d-0050568e63cd', 'EXPORT20190527000001', 'role', NULL, '100602', '100502', NULL, '2019-05-27 13:55:49', '2019-05-27 13:55:50', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('18ab49b6-7e01-11e9-a68d-0050568e63cd', 'EXPORT20190524000001', 'user', NULL, '100602', '100502', NULL, '2019-05-24 16:51:18', '2019-05-24 16:51:18', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('1a434853-7dfd-11e9-a68d-0050568e63cd', 'IMPORT20190524000011', 'user', NULL, '100601', 'null', NULL, '2019-05-24 16:22:43', '2019-05-24 16:22:43', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('1ab418fc-7d3a-11e9-a68d-0050568e63cd', 'EXPORT20190523000013', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:06:52', '2019-05-23 17:06:52', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('1d765ef1-7d20-11e9-a68d-0050568e63cd', 'EXPORT20190523000004', 'user', NULL, '2', 'succeed', NULL, '2019-05-23 14:00:49', '2019-05-23 14:00:50', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('299114e2-7e00-11e9-a68d-0050568e63cd', 'IMPORT20190524000019', 'user', NULL, '100601', '100503', NULL, '2019-05-24 16:44:37', '2019-05-24 16:44:37', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('2cdabfc1-8cb1-11e9-a68d-0050568e63cd', '20190612000006', 'user', NULL, '100602', '100502', NULL, '2019-06-12 09:27:00', '2019-06-12 09:27:00', 'd0225332-875f-11e9-a68d-0050568e63cd', '10001');
INSERT INTO `sys_import_export` VALUES ('2eab9208-7d09-11e9-a68d-0050568e63cd', 'EXPORT20190523000003', 'user', NULL, '2', 'succeed', NULL, '2019-05-23 11:16:40', '2019-05-23 11:16:40', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('32fee3a1-7dfb-11e9-a68d-0050568e63cd', 'IMPORT20190524000008', 'user', NULL, '100601', '100501', NULL, '2019-05-24 16:09:05', NULL, NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('3b2ff76d-804b-11e9-a68d-0050568e63cd', '20190527000003', 'role', NULL, '100601', '100504', 'nested exception is org.apache.ibatis.reflection.ReflectionException: There is no getter for property named \'roleName\' in \'class com.wust.springcloud.common.entity.sys.role.SysRoleSearch\'', '2019-05-27 14:47:01', '2019-05-27 14:47:01', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('443db59b-7dd6-11e9-a68d-0050568e63cd', 'IMPORT20190524000001', 'user', NULL, '100601', '100501', NULL, '2019-05-24 11:44:43', NULL, NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('46723d1f-7dfd-11e9-a68d-0050568e63cd', 'IMPORT20190524000012', 'user', NULL, '100601', 'null', NULL, '2019-05-24 16:23:57', '2019-05-24 16:24:26', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('4af13dbd-7d39-11e9-a68d-0050568e63cd', 'EXPORT20190523000007', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:01:03', '2019-05-23 17:01:03', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('4b4d8cf5-7deb-11e9-a68d-0050568e63cd', 'IMPORT20190524000005', 'user', NULL, '100601', '100501', NULL, '2019-05-24 14:15:14', NULL, NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('4e3eefbc-7dfe-11e9-a68d-0050568e63cd', 'IMPORT20190524000014', 'user', NULL, '100601', '100504', 'nested exception is org.apache.ibatis.binding.BindingException: Parameter \'id\' not found. Available parameters are [collection, list]', '2019-05-24 16:31:19', '2019-05-24 16:31:20', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('5085377c-7d39-11e9-a68d-0050568e63cd', 'EXPORT20190523000008', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:01:12', '2019-05-23 17:01:12', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('57b7f7d6-7d3b-11e9-a68d-0050568e63cd', 'EXPORT20190523000018', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:15:43', '2019-05-23 17:15:44', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('5c7edc1d-7d3a-11e9-a68d-0050568e63cd', 'EXPORT20190523000014', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:08:42', '2019-05-23 17:08:42', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('5ce67ac4-8c16-11e9-a68d-0050568e63cd', '20190611000007', 'user', NULL, '100602', '100501', NULL, '2019-06-11 14:58:48', NULL, '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('5e66bc75-8739-11e9-a68d-0050568e63cd', '20190605000001', 'apptoken', NULL, '100602', '100502', NULL, '2019-06-05 10:26:47', '2019-06-05 10:27:12', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin');
INSERT INTO `sys_import_export` VALUES ('61c6dd91-7d3a-11e9-a68d-0050568e63cd', 'EXPORT20190523000015', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:08:51', '2019-05-23 17:08:51', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('65aa8528-7d3b-11e9-a68d-0050568e63cd', 'EXPORT20190523000019', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:16:07', '2019-05-23 17:16:07', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('6976f690-7dfe-11e9-a68d-0050568e63cd', 'IMPORT20190524000015', 'user', NULL, '100601', '100504', 'nested exception is org.apache.ibatis.binding.BindingException: Parameter \'id\' not found. Available parameters are [collection, list]', '2019-05-24 16:32:05', '2019-05-24 16:32:51', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('6b0a4ebc-7d39-11e9-a68d-0050568e63cd', 'EXPORT20190523000009', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:01:57', '2019-05-23 17:01:57', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('6f786713-8c16-11e9-a68d-0050568e63cd', '20190611000008', 'user', NULL, '100602', '100501', NULL, '2019-06-11 14:59:19', NULL, '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('735edd45-7d3b-11e9-a68d-0050568e63cd', 'EXPORT20190523000020', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:16:30', '2019-05-23 17:16:30', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('771cfe81-867c-11e9-a68d-0050568e63cd', '20190604000001', 'organization', NULL, '100602', '100502', NULL, '2019-06-04 11:54:34', '2019-06-04 11:54:34', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin');
INSERT INTO `sys_import_export` VALUES ('7818d20a-7d3b-11e9-a68d-0050568e63cd', 'EXPORT20190523000021', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:16:38', '2019-05-23 17:16:38', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('797b1b7a-7d3b-11e9-a68d-0050568e63cd', 'EXPORT20190523000022', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:16:40', '2019-05-23 17:16:40', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('7a0d642f-8739-11e9-a68d-0050568e63cd', '20190605000002', 'apptoken', NULL, '100602', '100502', NULL, '2019-06-05 10:27:34', '2019-06-05 10:27:34', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin');
INSERT INTO `sys_import_export` VALUES ('7a17e382-7d30-11e9-a68d-0050568e63cd', 'EXPORT20190523000006', 'user', NULL, '100602', '100502', NULL, '2019-05-23 15:57:57', '2019-05-23 15:57:57', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('7a98b3c5-8c18-11e9-a68d-0050568e63cd', '20190611000012', 'company', NULL, '100602', '100502', NULL, '2019-06-11 15:13:57', '2019-06-11 15:14:00', '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('7cf1c8ff-7deb-11e9-a68d-0050568e63cd', 'IMPORT20190524000006', 'user', NULL, '100601', '100501', NULL, '2019-05-24 14:16:37', NULL, NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('856b9cb4-8c18-11e9-a68d-0050568e63cd', '20190611000013', 'department', NULL, '100602', '100502', NULL, '2019-06-11 15:14:15', '2019-06-11 15:14:15', '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('88e03a21-7dff-11e9-a68d-0050568e63cd', 'IMPORT20190524000017', 'user', NULL, '100601', '100503', NULL, '2019-05-24 16:40:07', '2019-05-24 16:40:08', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('8c815f77-7dfc-11e9-a68d-0050568e63cd', 'IMPORT20190524000009', 'user', NULL, '100601', '100503', NULL, '2019-05-24 16:18:45', '2019-05-24 16:18:45', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('8dd6ef9e-8c18-11e9-a68d-0050568e63cd', '20190611000014', 'user', NULL, '100602', '100502', NULL, '2019-06-11 15:14:29', '2019-06-11 15:14:29', 'd0225332-875f-11e9-a68d-0050568e63cd', '10001');
INSERT INTO `sys_import_export` VALUES ('99e26141-804b-11e9-a68d-0050568e63cd', '20190527000004', 'role', NULL, '100601', '100502', NULL, '2019-05-27 14:49:40', '2019-05-27 14:49:40', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('9a96f2ba-7dfd-11e9-a68d-0050568e63cd', 'IMPORT20190524000013', 'user', NULL, '100601', 'null', NULL, '2019-05-24 16:26:18', '2019-05-24 16:26:44', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('9dd8c405-7dff-11e9-a68d-0050568e63cd', 'IMPORT20190524000018', 'user', NULL, '100601', '100503', NULL, '2019-05-24 16:40:42', '2019-05-24 16:40:43', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('a71a2406-7d39-11e9-a68d-0050568e63cd', 'EXPORT20190523000010', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:03:38', '2019-05-23 17:03:38', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('b2014bde-8c17-11e9-a68d-0050568e63cd', '20190611000009', 'company', NULL, '100602', '100501', NULL, '2019-06-11 15:08:21', NULL, '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('b722ae41-7dfc-11e9-a68d-0050568e63cd', 'IMPORT20190524000010', 'user', NULL, '100601', 'null', NULL, '2019-05-24 16:19:56', '2019-05-24 16:19:56', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('b76b6e2a-7d39-11e9-a68d-0050568e63cd', 'EXPORT20190523000011', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:04:05', '2019-05-23 17:04:05', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('c51855d6-8c15-11e9-a68d-0050568e63cd', '20190611000001', 'organization', NULL, '100602', '100502', NULL, '2019-06-11 14:54:34', '2019-06-11 14:54:34', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000');
INSERT INTO `sys_import_export` VALUES ('c7784762-804a-11e9-a68d-0050568e63cd', '20190527000001', 'role', NULL, '100601', '100504', '找不到[classpath:import.xsd]或[importExport/import/xml/admin_role.xml]文件', '2019-05-27 14:43:47', '2019-05-27 14:43:47', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('c7e748f3-7e00-11e9-a68d-0050568e63cd', 'IMPORT20190524000020', 'user', NULL, '100601', '100503', NULL, '2019-05-24 16:49:03', '2019-05-24 16:49:03', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('c8080001-8c15-11e9-a68d-0050568e63cd', '20190611000002', 'role', NULL, '100602', '100502', NULL, '2019-06-11 14:54:38', '2019-06-11 14:54:39', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000');
INSERT INTO `sys_import_export` VALUES ('c9957ccf-8c15-11e9-a68d-0050568e63cd', '20190611000003', 'user', NULL, '100602', '100502', NULL, '2019-06-11 14:54:41', '2019-06-11 14:54:41', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000');
INSERT INTO `sys_import_export` VALUES ('cb8caee4-7d3a-11e9-a68d-0050568e63cd', 'EXPORT20190523000016', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:11:48', '2019-05-23 17:11:48', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('cc3abdd8-7d3b-11e9-a68d-0050568e63cd', 'EXPORT20190523000023', 'user', NULL, '100602', '100502', NULL, '2019-05-23 17:18:59', '2019-05-23 17:18:59', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('d057499d-8c17-11e9-a68d-0050568e63cd', '20190611000010', 'company', NULL, '100602', '100502', NULL, '2019-06-11 15:09:11', '2019-06-11 15:09:11', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000');
INSERT INTO `sys_import_export` VALUES ('d288513d-7dfe-11e9-a68d-0050568e63cd', 'IMPORT20190524000016', 'user', NULL, '100601', '100503', NULL, '2019-05-24 16:35:01', '2019-05-24 16:35:22', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('d307c296-7dfa-11e9-a68d-0050568e63cd', 'IMPORT20190524000007', 'user', NULL, '100601', '100501', NULL, '2019-05-24 16:06:24', NULL, NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('d7ebabf7-8cad-11e9-a68d-0050568e63cd', '20190612000001', 'company', NULL, '100602', '100502', NULL, '2019-06-12 09:03:09', '2019-06-12 09:03:09', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005');
INSERT INTO `sys_import_export` VALUES ('d9d3d1bc-8cad-11e9-a68d-0050568e63cd', '20190612000002', 'department', NULL, '100602', '100502', NULL, '2019-06-12 09:03:12', '2019-06-12 09:03:12', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005');
INSERT INTO `sys_import_export` VALUES ('db67ca0d-8cad-11e9-a68d-0050568e63cd', '20190612000003', 'role', NULL, '100602', '100502', NULL, '2019-06-12 09:03:14', '2019-06-12 09:03:15', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005');
INSERT INTO `sys_import_export` VALUES ('dcee81b8-8cad-11e9-a68d-0050568e63cd', '20190612000004', 'user', NULL, '100602', '100502', NULL, '2019-06-12 09:03:17', '2019-06-12 09:03:17', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005');
INSERT INTO `sys_import_export` VALUES ('df7434fb-8cad-11e9-a68d-0050568e63cd', '20190612000005', 'organization', NULL, '100602', '100502', NULL, '2019-06-12 09:03:21', '2019-06-12 09:03:21', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005');
INSERT INTO `sys_import_export` VALUES ('e27b1010-8739-11e9-a68d-0050568e63cd', '20190605000003', 'apptoken', NULL, '100602', '100502', NULL, '2019-06-05 10:30:29', '2019-06-05 10:30:29', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin');
INSERT INTO `sys_import_export` VALUES ('e5877984-7e00-11e9-a68d-0050568e63cd', 'IMPORT20190524000021', 'user', NULL, '100601', '100503', NULL, '2019-05-24 16:49:52', '2019-05-24 16:49:52', NULL, NULL);
INSERT INTO `sys_import_export` VALUES ('efd13035-85b8-11e9-a68d-0050568e63cd', '20190603000002', 'department', NULL, '100602', '100502', NULL, '2019-06-03 12:34:55', '2019-06-03 12:34:55', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin');
INSERT INTO `sys_import_export` VALUES ('f8b8ed1c-8c17-11e9-a68d-0050568e63cd', '20190611000011', 'company', NULL, '100602', '100501', NULL, '2019-06-11 15:10:19', NULL, '8e55d855-87f2-11e9-a68d-0050568e63cd', '10004');
INSERT INTO `sys_import_export` VALUES ('f950d498-880a-11e9-a68d-0050568e63cd', '20190606000001', 'organization', NULL, '100602', '100502', NULL, '2019-06-06 11:27:12', '2019-06-06 11:27:12', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin');
INSERT INTO `sys_import_export` VALUES ('fc1762fa-85b0-11e9-a68d-0050568e63cd', '20190603000001', 'role', NULL, '100602', '100502', NULL, '2019-06-03 11:38:00', '2019-06-03 11:38:00', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin');
INSERT INTO `sys_import_export` VALUES ('fc392b1f-7d2f-11e9-a68d-0050568e63cd', 'EXPORT20190523000005', 'user', NULL, '2', '100502', NULL, '2019-05-23 15:54:26', '2019-05-23 15:54:26', NULL, NULL);

-- ----------------------------
-- Table structure for sys_lookup
-- ----------------------------
DROP TABLE IF EXISTS `sys_lookup`;
CREATE TABLE `sys_lookup`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `root_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `lan` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `visible` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` tinyint(4) NULL DEFAULT 0,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_lookup
-- ----------------------------
INSERT INTO `sys_lookup` VALUES ('f90d96e1-97c2-11e9-a68d-0050568e63cd', '1001', '0000', '0000', '', '一级数据', 'zh_CN', '一级数据', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90d9b19-97c2-11e9-a68d-0050568e63cd', '100101', '1001', '1001', '', '二级数据1', 'zh_CN', '二级数据1', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90d9d5d-97c2-11e9-a68d-0050568e63cd', '100102', '1001', '1001', '', '二级数据2', 'zh_CN', '二级数据2', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90d9f22-97c2-11e9-a68d-0050568e63cd', '10010101', '100101', '1001', '', '三级数据1', 'zh_CN', '三级数据1', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90da0c8-97c2-11e9-a68d-0050568e63cd', '10010102', '100101', '1001', '', '三级数据2', 'zh_CN', '三级数据2', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90da265-97c2-11e9-a68d-0050568e63cd', '10010201', '100102', '1001', '', '三级数据3', 'zh_CN', '三级数据3', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90da3ff-97c2-11e9-a68d-0050568e63cd', '10010202', '100102', '1001', '', '三级数据4', 'zh_CN', '三级数据4', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90da5ce-97c2-11e9-a68d-0050568e63cd', '1002', '0000', '0000', '', '启用禁用状态', 'zh_CN', '启用禁用状态', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90da77f-97c2-11e9-a68d-0050568e63cd', '100201', '1002', '1002', '1', '启用', 'zh_CN', '启用', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90da955-97c2-11e9-a68d-0050568e63cd', '100202', '1002', '1002', '0', '禁用', 'zh_CN', '禁用', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dab48-97c2-11e9-a68d-0050568e63cd', '1003', '0000', '0000', '', '性别', 'zh_CN', '性别', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dacd7-97c2-11e9-a68d-0050568e63cd', '100301', '1003', '1003', 'M', '男', 'zh_CN', '男', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dae6d-97c2-11e9-a68d-0050568e63cd', '100302', '1003', '1003', 'F', '女', 'zh_CN', '女', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90db050-97c2-11e9-a68d-0050568e63cd', '1004', '0000', '0000', '', '用户类型', 'zh_CN', '用户类型', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90db28e-97c2-11e9-a68d-0050568e63cd', '100401', '1004', '1004', '0', '平台管理员', 'zh_CN', '平台管理员', '100201', '100702', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90db42c-97c2-11e9-a68d-0050568e63cd', '100402', '1004', '1004', '1', '公司管理员', 'zh_CN', '公司管理员', '100201', '100702', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90db600-97c2-11e9-a68d-0050568e63cd', '100404', '1004', '1004', '3', '普通用户', 'zh_CN', '普通用户', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90db7af-97c2-11e9-a68d-0050568e63cd', '1005', '0000', '0000', '', '我的导入导出状态', 'zh_CN', '我的导入导出状态', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90db954-97c2-11e9-a68d-0050568e63cd', '100501', '1005', '1005', '1', '执行中', 'zh_CN', '执行中', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dbae9-97c2-11e9-a68d-0050568e63cd', '100502', '1005', '1005', '2', '全部成功', 'zh_CN', '全部成功', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dbc75-97c2-11e9-a68d-0050568e63cd', '100503', '1005', '1005', '3', '部分成功', 'zh_CN', '部分成功', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dbe3e-97c2-11e9-a68d-0050568e63cd', '100504', '1005', '1005', '4', '全部失败', 'zh_CN', '全部失败', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dc119-97c2-11e9-a68d-0050568e63cd', '1006', '0000', '0000', '', '我的导入导出操作类型', 'zh_CN', '我的导入导出操作类型', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dc314-97c2-11e9-a68d-0050568e63cd', '100601', '1006', '1006', '1', '导入', 'zh_CN', '导入', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dc4a0-97c2-11e9-a68d-0050568e63cd', '100602', '1006', '1006', '2', '导出', 'zh_CN', '导出', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dc632-97c2-11e9-a68d-0050568e63cd', '1007', '0000', '0000', '', '是否', 'zh_CN', '是否', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dc7c0-97c2-11e9-a68d-0050568e63cd', '100701', '1007', '1007', 'Y', '是', 'zh_CN', '是', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dc949-97c2-11e9-a68d-0050568e63cd', '100702', '1007', '1007', 'N', '否', 'zh_CN', '否', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dcad5-97c2-11e9-a68d-0050568e63cd', '1008', '0000', '0000', '', '语言', 'zh_CN', '语言', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dcc6b-97c2-11e9-a68d-0050568e63cd', '100801', '1008', '1008', 'zh_CN', 'zh_CN', 'zh_CN', 'zh_CN', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dcdfc-97c2-11e9-a68d-0050568e63cd', '100802', '1008', '1008', 'en_US', 'en_US', 'zh_CN', 'en_US', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dcf8a-97c2-11e9-a68d-0050568e63cd', '1009', '0000', '0000', '', '数据权限类型', 'zh_CN', '数据权限类型', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dd119-97c2-11e9-a68d-0050568e63cd', '100905', '1009', '1009', '1', '本人可见', 'zh_CN', '本人可见', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dd2ed-97c2-11e9-a68d-0050568e63cd', '100910', '1009', '1009', '2', '角色可见', 'zh_CN', '角色可见', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dd48c-97c2-11e9-a68d-0050568e63cd', '100915', '1009', '1009', '3', '部门可见', 'zh_CN', '部门可见', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dd621-97c2-11e9-a68d-0050568e63cd', '100920', '1009', '1009', '4', '公司可见', 'zh_CN', '公司可见', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dd7ec-97c2-11e9-a68d-0050568e63cd', '100925', '1009', '1009', '4', '领导可见', 'zh_CN', '领导可见', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90dd99c-97c2-11e9-a68d-0050568e63cd', '1010', '0000', '0000', '', '用户在线状态', 'zh_CN', '用户在线状态', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90ddb34-97c2-11e9-a68d-0050568e63cd', '101001', '1010', '1010', '1', '在线', 'zh_CN', '在线', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);
INSERT INTO `sys_lookup` VALUES ('f90ddcbe-97c2-11e9-a68d-0050568e63cd', '101002', '1010', '1010', '0', '离线', 'zh_CN', '离线', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-06-26 11:32:06', NULL);

-- ----------------------------
-- Table structure for sys_lookup_private
-- ----------------------------
DROP TABLE IF EXISTS `sys_lookup_private`;
CREATE TABLE `sys_lookup_private`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `root_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `lan` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `visible` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` tinyint(4) NULL DEFAULT 0,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_code_lan`(`code`, `lan`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_lookup_private
-- ----------------------------
INSERT INTO `sys_lookup_private` VALUES ('9c15816f-8368-11e9-a68d-0050568e63cd', '1001', '', '0000', '', '一级数据', 'zh_CN', '一级数据', '100201', '100701', 0, 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', NULL, NULL, '2019-05-31 13:54:53', NULL);
INSERT INTO `sys_lookup_private` VALUES ('9c158401-8368-11e9-a68d-0050568e63cd', '100101', '1001', '1001', '', '二级数据1', 'zh_CN', '二级数据1', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-05-31 13:54:53', NULL);
INSERT INTO `sys_lookup_private` VALUES ('9c158542-8368-11e9-a68d-0050568e63cd', '10010101', '100101', '1001', '', '三级数据1', 'zh_CN', '三级数据1', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-05-31 13:54:53', NULL);
INSERT INTO `sys_lookup_private` VALUES ('9c158640-8368-11e9-a68d-0050568e63cd', '10010102', '100101', '1001', '', '三级数据2', 'zh_CN', '三级数据2', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-05-31 13:54:53', NULL);
INSERT INTO `sys_lookup_private` VALUES ('9c15872e-8368-11e9-a68d-0050568e63cd', '100102', '1001', '1001', '', '二级数据2', 'zh_CN', '二级数据2', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-05-31 13:54:53', NULL);
INSERT INTO `sys_lookup_private` VALUES ('9c158817-8368-11e9-a68d-0050568e63cd', '10010201', '100102', '1001', '', '三级数据3', 'zh_CN', '三级数据3', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-05-31 13:54:53', NULL);
INSERT INTO `sys_lookup_private` VALUES ('9c1588fb-8368-11e9-a68d-0050568e63cd', '10010202', '100102', '1001', '', '三级数据4', 'zh_CN', '三级数据4', '100201', '100701', 0, NULL, NULL, NULL, NULL, '2019-05-31 13:54:53', NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `level` tinyint(4) NULL DEFAULT NULL COMMENT '菜单层级 , 在同一个pid下 , 值越小层级越高',
  `sort` int(11) NULL DEFAULT NULL COMMENT '菜单排序 , 在同一个pid下可重新排序',
  `img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(135) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单类型，如topbar,title,menu等等',
  `visible` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否可见',
  `create_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1a133ce5-8977-4c82-9973-d134a002ecd2', 'Operation Log', '操作日志', './OperationLogList', 'OperationLogList', -1, 2000102, '', '8972b850-804e-11e9-a68d-0050568e63cd', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'Setting', '系统配置', '', '', 1, 30001, '', '20356848-80a5-4ecb-945e-964242c368c5', 'menuGroup', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('1e9a509f-8d9f-11e9-a68d-0050568e63cd', 'Job', '作业管理', './JobList', 'JobList', -1, 2000105, '', '8972b850-804e-11e9-a68d-0050568e63cd', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('20356848-80a5-4ecb-945e-964242c368c5', 'Wust', '企业基础平台管理系统', '', '', 0, -1, NULL, NULL, 'subSystem', NULL, NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('28e4fa02-12e3-4148-8dc2-2267e7e51817', 'anon', '公共模块白名单集合', '', 'anon', -1, -1, '', '35ce0d97-f123-4715-b4e0-cb14c0292a31', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('35ce0d97-f123-4715-b4e0-cb14c0292a31', 'anon', '公共模块白名单集合', '', 'anon', 1, -1, '', '20356848-80a5-4ecb-945e-964242c368c5', 'menuGroup', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'User Management', '用户管理', './UserList', 'UserList', -1, 3000111, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('8972b850-804e-11e9-a68d-0050568e63cd', 'Query Management', '查询管理', '', '', 1, 20001, '', '20356848-80a5-4ecb-945e-964242c368c5', 'menuGroup', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('94fcc15c-85a4-11e9-a68d-0050568e63cd', 'Company Management', '公司管理', './CompanyList', 'CompanyList', -1, 3000101, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('9b472b74-85a4-11e9-a68d-0050568e63cd', 'Department Management', '部门管理', './DepartmentList', 'DepartmentList', -1, 3000105, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('ab849b58-2113-46bc-b805-6287715cca88', 'My Import and Export', '我的导入导出', './MyImportExportList', 'MyImportExportList', -1, 2000101, '', '8972b850-804e-11e9-a68d-0050568e63cd', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('b485b58c-716f-11e9-a68d-0050568e63cd', 'Role Management', '角色管理', './RoleList', 'RoleList', -1, 3000108, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('ba01ce93-716f-11e9-a68d-0050568e63cd', 'Data Permission', '数据权限', './DataPrivilegeRulesList', 'DataPrivilegeRulesList', -1, 3000119, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('bfa76d77-716f-11e9-a68d-0050568e63cd', 'Lookup', '数据字典', './LookupList', 'LookupList', -1, 3000123, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'Organization', '组织架构', './OrganizationList', 'OrganizationList', -1, 3000115, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('cc6d9577-8cc1-11e9-a68d-0050568e63cd', 'Data Source', '数据源管理', './DataSourceList', 'DataSourceList', 2, 3000124, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_menu` VALUES ('d376619f-8732-11e9-a68d-0050568e63cd', 'App Token', '外部应用访问管理', './AppTokenList', 'AppTokenList', -1, 3000126, '', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'menu', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `module_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `business_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operation_role` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operation_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `operation_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `operation_ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源',
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '非必填',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `relation_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('11ead3e4-875d-11e9-a68d-0050568e63cd', '-1', 'sys_company', '63ecca81-85de-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:42:21', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('13ac75d1-87f5-11e9-a68d-0050568e63cd', '7cb89f3b-875d-11e9-a68d-0050568e63cd', 'sys_user', 'd0225332-875f-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-06 08:50:27', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('13d62acc-875d-11e9-a68d-0050568e63cd', '-1', 'sys_company', 'a65cbd8e-85ac-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:42:24', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('159e592a-875d-11e9-a68d-0050568e63cd', '-1', 'sys_company', '6f60e913-85de-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:42:27', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('1c18701b-875d-11e9-a68d-0050568e63cd', '11ead3e4-875d-11e9-a68d-0050568e63cd', 'sys_department', 'cab49a51-85b7-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:42:38', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('2008d3dd-875d-11e9-a68d-0050568e63cd', '11ead3e4-875d-11e9-a68d-0050568e63cd', 'sys_department', '21e4b18f-85b8-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:42:45', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('375139f9-880f-11e9-a68d-0050568e63cd', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'sys_user', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-06 11:57:34', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('4a83f5d5-87f4-11e9-a68d-0050568e63cd', '6245afe1-875d-11e9-a68d-0050568e63cd', 'sys_user', 'c6fdf4db-875f-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-06 08:44:50', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('6245afe1-875d-11e9-a68d-0050568e63cd', '1c18701b-875d-11e9-a68d-0050568e63cd', 'sys_role', '3e4a2e2c-875d-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:44:36', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('66de716c-875d-11e9-a68d-0050568e63cd', '1c18701b-875d-11e9-a68d-0050568e63cd', 'sys_role', '42c7c1e5-875d-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:44:43', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('7cb89f3b-875d-11e9-a68d-0050568e63cd', '2008d3dd-875d-11e9-a68d-0050568e63cd', 'sys_role', '4bf25702-875d-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:45:20', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('8d9f2fde-875d-11e9-a68d-0050568e63cd', '2008d3dd-875d-11e9-a68d-0050568e63cd', 'sys_role', '4817ee68-875d-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:45:48', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('94ea0f33-875d-11e9-a68d-0050568e63cd', '2008d3dd-875d-11e9-a68d-0050568e63cd', 'sys_role', '539a3393-875d-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:46:01', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('a80eed46-8760-11e9-a68d-0050568e63cd', '66de716c-875d-11e9-a68d-0050568e63cd', 'sys_user', '16b4b2f2-8760-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 15:08:01', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('b215a297-87f3-11e9-a68d-0050568e63cd', '8d9f2fde-875d-11e9-a68d-0050568e63cd', 'sys_user', '627dc2c8-87f2-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-06 08:40:34', NULL, NULL, NULL);
INSERT INTO `sys_organization` VALUES ('b950c34c-87f3-11e9-a68d-0050568e63cd', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'sys_user', '8e55d855-87f2-11e9-a68d-0050568e63cd', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-06 08:40:46', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_quill_editor
-- ----------------------------
DROP TABLE IF EXISTS `sys_quill_editor`;
CREATE TABLE `sys_quill_editor`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `table_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `relation_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行标识',
  `relation_field_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列标识，一条数据可以有多个富文本',
  `html_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(108) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '富文本表，用于存储富文本数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `menu_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('05f29473-87fc-11e9-a68d-0050568e63cd', '94fcc15c-85a4-11e9-a68d-0050568e63cd', 'Export', '导出', 'CompanyList.export', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('0afe16b5-87fc-11e9-a68d-0050568e63cd', '94fcc15c-85a4-11e9-a68d-0050568e63cd', 'Create', '新建', 'CompanyList.create', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('0faac691-8da1-11e9-a68d-0050568e63cd', '1e9a509f-8d9f-11e9-a68d-0050568e63cd', 'Delete', '删除', 'JobList.delete', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('102c04b8-87fc-11e9-a68d-0050568e63cd', '94fcc15c-85a4-11e9-a68d-0050568e63cd', 'Update', '修改', 'CompanyList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('14e266b6-87fc-11e9-a68d-0050568e63cd', '94fcc15c-85a4-11e9-a68d-0050568e63cd', 'Delete', '删除', 'CompanyList.delete', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('1ba1d62f-87fc-11e9-a68d-0050568e63cd', '9b472b74-85a4-11e9-a68d-0050568e63cd', 'Search', '查询', 'DepartmentList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('20a16a14-87fc-11e9-a68d-0050568e63cd', '9b472b74-85a4-11e9-a68d-0050568e63cd', 'Export', '导出', 'DepartmentList.export', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('22517901-8b67-4670-9089-cf04a8c8e3d9', '28e4fa02-12e3-4148-8dc2-2267e7e51817', 'anon', 'anon', 'anon', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('2446df53-8d9f-11e9-a68d-0050568e63cd', '1e9a509f-8d9f-11e9-a68d-0050568e63cd', 'Search', '查询', 'JobList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('25350db7-87fd-11e9-a68d-0050568e63cd', 'ab849b58-2113-46bc-b805-6287715cca88', 'Search', '查询', 'MyImportExportList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('2545f350-87fc-11e9-a68d-0050568e63cd', '9b472b74-85a4-11e9-a68d-0050568e63cd', 'Create', '新建', 'DepartmentList.create', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('299dceb8-8d9f-11e9-a68d-0050568e63cd', '1e9a509f-8d9f-11e9-a68d-0050568e63cd', 'Update', '修改', 'JobList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('29a60157-87fd-11e9-a68d-0050568e63cd', 'ab849b58-2113-46bc-b805-6287715cca88', 'Download', '下载', 'MyImportExportList.download', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('2b5dff8d-87fc-11e9-a68d-0050568e63cd', '9b472b74-85a4-11e9-a68d-0050568e63cd', 'Update', '修改', 'DepartmentList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('2dda74e9-87fd-11e9-a68d-0050568e63cd', '1a133ce5-8977-4c82-9973-d134a002ecd2', 'Search', '查询', 'OperationLogList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('2f9573ad-87fc-11e9-a68d-0050568e63cd', '9b472b74-85a4-11e9-a68d-0050568e63cd', 'Delete', '删除', 'DepartmentList.delete', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('30fa1beb-7f3b-4385-9961-320c251aa65e', '28e4fa02-12e3-4148-8dc2-2267e7e51817', 'anon', 'anon', 'anon', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('5b4b0e30-f90d-4c4d-97a1-32a79fb270a3', '28e4fa02-12e3-4148-8dc2-2267e7e51817', 'anon', 'anon', 'anon', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('5c7fd964-87fc-11e9-a68d-0050568e63cd', 'b485b58c-716f-11e9-a68d-0050568e63cd', 'Search', '查询', 'RoleList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('62628da1-87fc-11e9-a68d-0050568e63cd', 'b485b58c-716f-11e9-a68d-0050568e63cd', 'Export', '导出', 'RoleList.export', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('62e5b2df-9933-4c59-904c-52a2d3f11c21', '28e4fa02-12e3-4148-8dc2-2267e7e51817', 'anon', 'anon', 'anon', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('67a72433-87fc-11e9-a68d-0050568e63cd', 'b485b58c-716f-11e9-a68d-0050568e63cd', 'Import', '导入', 'RoleList.import', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('6ca3c101-87fc-11e9-a68d-0050568e63cd', 'b485b58c-716f-11e9-a68d-0050568e63cd', 'Create', '新建', 'RoleList.create', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('720b2a9a-87fc-11e9-a68d-0050568e63cd', 'b485b58c-716f-11e9-a68d-0050568e63cd', 'Update', '修改', 'RoleList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('764500da-87fc-11e9-a68d-0050568e63cd', 'b485b58c-716f-11e9-a68d-0050568e63cd', 'Delete', '删除', 'RoleList.delete', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('7c4bec3d-87fc-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'Search', '查询', 'UserList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('8199c503-87fc-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'Export', '导出', 'UserList.export', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('863fc793-87fc-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'Import', '导入', 'UserList.import', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('8b67ce73-87fc-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'Create', '新建', 'UserList.create', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('90436815-87fc-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'Update', '修改', 'UserList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('95a383df-87fc-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'Delete', '删除', 'UserList.delete', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('9ac2c28e-87fc-11e9-a68d-0050568e63cd', 'c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'AddCompany', '添加公司', 'OrganizationList.addCompany', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('9f2ce4d5-87fc-11e9-a68d-0050568e63cd', 'c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'AddDepartment', '添加部门', 'OrganizationList.addDepartment', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('a3b5d3d6-87fc-11e9-a68d-0050568e63cd', 'c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'AddRole', '添加角色', 'OrganizationList.addRole', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('a8aa5a7e-87fc-11e9-a68d-0050568e63cd', 'c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'AddUser', '添加用户', 'OrganizationList.addUser', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('ad2fdb46-87fc-11e9-a68d-0050568e63cd', 'c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'Export', '导出', 'OrganizationList.export', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('b18e6ece-87fc-11e9-a68d-0050568e63cd', 'c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'Delete', '移除', 'OrganizationList.delete', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('b8cb00c4-87fc-11e9-a68d-0050568e63cd', 'bfa76d77-716f-11e9-a68d-0050568e63cd', 'Search', '查询', 'LookupList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('bab3685d-fd51-43db-8d73-590bfbaa2c2b', '28e4fa02-12e3-4148-8dc2-2267e7e51817', 'anon', 'anon', 'anon', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('bd14e7dd-87fc-11e9-a68d-0050568e63cd', 'bfa76d77-716f-11e9-a68d-0050568e63cd', 'Update', '修改', 'LookupList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('c1d74556-87fc-11e9-a68d-0050568e63cd', 'd376619f-8732-11e9-a68d-0050568e63cd', 'Search', '查询', 'AppTokenList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('c65a0685-87fc-11e9-a68d-0050568e63cd', 'd376619f-8732-11e9-a68d-0050568e63cd', 'Export', '导出', 'AppTokenList.export', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('ca87fdb1-87fc-11e9-a68d-0050568e63cd', 'd376619f-8732-11e9-a68d-0050568e63cd', 'Create', '新建', 'AppTokenList.create', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('cf8bdeeb-87fc-11e9-a68d-0050568e63cd', 'd376619f-8732-11e9-a68d-0050568e63cd', 'Update', '修改', 'AppTokenList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('d43b85e4-87fc-11e9-a68d-0050568e63cd', 'd376619f-8732-11e9-a68d-0050568e63cd', 'Delete', '删除', 'AppTokenList.delete', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('d80d413b-ce87-4053-a568-8d937de1baee', '28e4fa02-12e3-4148-8dc2-2267e7e51817', 'anon', 'anon', 'anon', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('ee4fe2fa-87fc-11e9-a68d-0050568e63cd', 'ba01ce93-716f-11e9-a68d-0050568e63cd', 'Search', '查询', 'DataPrivilegeRulesList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('f7166026-87fc-11e9-a68d-0050568e63cd', 'ba01ce93-716f-11e9-a68d-0050568e63cd', 'Update', '修改', 'DataPrivilegeRulesList.update', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('fabc4560-8da3-11e9-a68d-0050568e63cd', '1e9a509f-8d9f-11e9-a68d-0050568e63cd', 'Create', '新建', 'JobList.create', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');
INSERT INTO `sys_resource` VALUES ('ffec31aa-87fb-11e9-a68d-0050568e63cd', '94fcc15c-85a4-11e9-a68d-0050568e63cd', 'Search', '查询', 'CompanyList.search', '', NULL, '2019-06-26 11:32:06', NULL, '2019-06-26 11:32:06');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('3e4a2e2c-875d-11e9-a68d-0050568e63cd', '001', '人事部经理', '', '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:43:35', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('42c7c1e5-875d-11e9-a68d-0050568e63cd', '002', '人事专员', '', '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:43:43', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('4817ee68-875d-11e9-a68d-0050568e63cd', '003', '技术总监', '', '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:43:52', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('4bf25702-875d-11e9-a68d-0050568e63cd', '004', 'Java开发工程师', '', '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:43:58', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('539a3393-875d-11e9-a68d-0050568e63cd', '005', '测试工程师', '', '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:44:11', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('58116699-875d-11e9-a68d-0050568e63cd', '006', '前端工程师', '', '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:44:19', NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES ('5b40e55f-875d-11e9-a68d-0050568e63cd', '007', '美工', '', '100201', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 14:44:24', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `organization_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色在组织架构中的标识',
  `resource_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('0ba10d7e-3fd9-4e3a-92df-640e993b3261', '94ea0f33-875d-11e9-a68d-0050568e63cd', '764500da-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('0c58b16b-52e8-4dc9-9d3d-0369403680f6', '94ea0f33-875d-11e9-a68d-0050568e63cd', '25350db7-87fd-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('0f26da87-1ad6-4ca8-b8b6-17a6cfb6b81d', '94ea0f33-875d-11e9-a68d-0050568e63cd', '102c04b8-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('12e0f3f5-b422-4671-974d-5b005cdebbd0', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '8972b850-804e-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('147945ec-4318-4119-be0c-481a209e00cd', '94ea0f33-875d-11e9-a68d-0050568e63cd', '0afe16b5-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('18351a29-1a68-4a31-a36a-50979d5f224d', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '7c4bec3d-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('1cb6cae5-d1d2-4495-8977-644e55b828b6', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'm');
INSERT INTO `sys_role_resource` VALUES ('28524ffe-06e0-4ccf-a455-95c5b550ec29', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'b18e6ece-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('2b075555-9a97-4950-8b59-4355853624a7', '94ea0f33-875d-11e9-a68d-0050568e63cd', '6ca3c101-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('2f29b0aa-a404-4beb-a58d-662d90784354', '94ea0f33-875d-11e9-a68d-0050568e63cd', '7c4bec3d-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('32c4a532-ec0b-4429-beca-b59648834b31', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'cf8bdeeb-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('3573cb42-0182-4635-bfcf-2c9dac2a9359', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'ca87fdb1-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('3c270487-a805-44d6-b330-d832d4265287', '94ea0f33-875d-11e9-a68d-0050568e63cd', '2545f350-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('3d36c12d-e1f7-4b15-baea-6fa7d75c4c53', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'm');
INSERT INTO `sys_role_resource` VALUES ('3eec6ccb-deda-4f96-ae66-33d4267166cb', '94ea0f33-875d-11e9-a68d-0050568e63cd', '9f2ce4d5-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('4189b349-72bf-45e3-97b7-9ef6d61a57c9', '94ea0f33-875d-11e9-a68d-0050568e63cd', '1a6debab-f513-4b3f-9d21-2b5249c3ec11', 'm');
INSERT INTO `sys_role_resource` VALUES ('42265af2-45d6-49f5-a286-735edf7875fe', '94ea0f33-875d-11e9-a68d-0050568e63cd', '2dda74e9-87fd-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('4293d01b-ee67-40a2-80bd-1e9216e93eef', '94ea0f33-875d-11e9-a68d-0050568e63cd', '2b5dff8d-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('43406b60-8446-4c61-a59c-c8397a23b5fa', '94ea0f33-875d-11e9-a68d-0050568e63cd', '863fc793-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('47526890-ae88-420a-95a3-974d1103afd1', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'ab849b58-2113-46bc-b805-6287715cca88', 'm');
INSERT INTO `sys_role_resource` VALUES ('4a3f9d53-2559-4308-a37e-616bbda1debb', '94ea0f33-875d-11e9-a68d-0050568e63cd', '720b2a9a-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('4ff625ed-1faa-4a70-93b2-1f68bb7a3590', '94ea0f33-875d-11e9-a68d-0050568e63cd', '94fcc15c-85a4-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('50633e72-1a44-4634-8c13-dd5913e5e21a', '94ea0f33-875d-11e9-a68d-0050568e63cd', '67a72433-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('5cfa95e3-f051-4053-958d-9c8f548a71b5', '94ea0f33-875d-11e9-a68d-0050568e63cd', '1a133ce5-8977-4c82-9973-d134a002ecd2', 'm');
INSERT INTO `sys_role_resource` VALUES ('5da3e98f-87fa-43e2-85dc-17fc9e1087af', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'b485b58c-716f-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('5dcc630a-4888-4ebc-a47e-4fd34bf9ac72', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'bd14e7dd-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('60299347-4d43-49d5-9fbe-fab7b37bde1c', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'ba01ce93-716f-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('6631ff72-e936-4b54-bbe4-ec7358badfdb', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '8b67ce73-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('69461ea9-0f6a-4830-820f-414aefd7fb7e', '94ea0f33-875d-11e9-a68d-0050568e63cd', '29a60157-87fd-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('6e9c5957-d6af-4bcc-8c67-46156ed60226', '94ea0f33-875d-11e9-a68d-0050568e63cd', '90436815-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('74febf0c-8525-49ed-b28d-270ecf410c56', '94ea0f33-875d-11e9-a68d-0050568e63cd', '8b67ce73-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('769abd2b-b403-4394-ad46-8860a9296c0b', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'a3b5d3d6-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('79d737cc-0600-4eb9-b567-0fdec847e4b1', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'd376619f-8732-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('7af64e8e-f086-48c9-bcb1-12c48c36b157', '94ea0f33-875d-11e9-a68d-0050568e63cd', '8972b850-804e-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('83252aa5-7b8b-4e60-8de0-00b06a571e5a', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'c8c0d04e-85a7-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('869e7287-d95b-4a9f-9764-a17090727819', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'bfa76d77-716f-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('8b7e37ed-d174-4c2f-843e-1f81980df797', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'd43b85e4-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('94ce519a-2ebb-4848-b648-1688907278eb', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '25350db7-87fd-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('951aee6d-43c5-4f58-9772-600c20cff129', '94ea0f33-875d-11e9-a68d-0050568e63cd', '62628da1-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('9ebbc1d5-bf68-4c65-b194-12a819fce089', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '8199c503-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('a0e85745-4b00-481b-9d9b-a4b00b63ebaa', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'c65a0685-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('a31c97a9-ea19-4464-ace5-1c948c38323f', '94ea0f33-875d-11e9-a68d-0050568e63cd', '5c7fd964-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('a7e3e7d6-2d3e-4be8-a18b-95aca407094e', '94ea0f33-875d-11e9-a68d-0050568e63cd', '8199c503-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('b0abfcb5-1565-45c5-9181-24af8205b7cf', '94ea0f33-875d-11e9-a68d-0050568e63cd', '14e266b6-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('b37b1402-cab8-4685-b82d-8a2baa3319bb', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'ee4fe2fa-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('be50a369-a1b4-4f09-9465-2bd874a058e3', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '29a60157-87fd-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('bf2fbfce-a9e8-4fa9-b3a1-0500db833b75', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'a8aa5a7e-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('c10a2035-003a-426d-b977-63c6602ac9c0', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'ffec31aa-87fb-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('c51b0156-089b-4fac-9889-4eedfcfdf029', '94ea0f33-875d-11e9-a68d-0050568e63cd', '9ac2c28e-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('c781551d-b1ca-4a62-b479-11a25a8d5147', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '20356848-80a5-4ecb-945e-964242c368c5', 'm');
INSERT INTO `sys_role_resource` VALUES ('c8c27299-5245-4469-8118-a3d11803a4dd', '94ea0f33-875d-11e9-a68d-0050568e63cd', '05f29473-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('cf57e244-b1e0-4b8e-acee-d0a5bbd2e595', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'c1d74556-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('d193bff1-d065-42c1-86e0-5ac0377e6c55', '7cb89f3b-875d-11e9-a68d-0050568e63cd', 'ab849b58-2113-46bc-b805-6287715cca88', 'm');
INSERT INTO `sys_role_resource` VALUES ('d8224686-15f7-4b7e-8ff9-355ba85a8137', '94ea0f33-875d-11e9-a68d-0050568e63cd', '2f9573ad-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('d99a819e-47ea-432f-9308-d49b447102b4', '94ea0f33-875d-11e9-a68d-0050568e63cd', '6c73459c-fcd6-4fc8-b606-9dedc5d87c23', 'm');
INSERT INTO `sys_role_resource` VALUES ('da9f1119-162e-4296-93b0-58c2ffe4a2f4', '94ea0f33-875d-11e9-a68d-0050568e63cd', '1ba1d62f-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('db8fc666-c6e2-4899-856f-b8493f5766ad', '94ea0f33-875d-11e9-a68d-0050568e63cd', '95a383df-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('df9c9b68-e60d-46c0-8875-37f8d61885a1', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'b8cb00c4-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('e48aabe3-60a5-43d8-a1ec-6d882ca0f5b3', '94ea0f33-875d-11e9-a68d-0050568e63cd', '20a16a14-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('e6ce0a9b-e112-44ab-99cf-8ac77cf3e4f0', '94ea0f33-875d-11e9-a68d-0050568e63cd', '9b472b74-85a4-11e9-a68d-0050568e63cd', 'm');
INSERT INTO `sys_role_resource` VALUES ('ebfb3ed7-1d74-40a6-b0e7-c21cb3bb86c7', '94ea0f33-875d-11e9-a68d-0050568e63cd', 'ad2fdb46-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('f3281f1d-9691-4fb5-ae16-65f51fa74f4b', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '95a383df-87fc-11e9-a68d-0050568e63cd', 'r');
INSERT INTO `sys_role_resource` VALUES ('f92f3431-5934-4264-a852-73f30839bc7c', '94ea0f33-875d-11e9-a68d-0050568e63cd', '20356848-80a5-4ecb-945e-964242c368c5', 'm');
INSERT INTO `sys_role_resource` VALUES ('fabecda6-7a0f-4fc6-806c-314ba99abb43', '7cb89f3b-875d-11e9-a68d-0050568e63cd', '863fc793-87fc-11e9-a68d-0050568e63cd', 'r');

-- ----------------------------
-- Table structure for sys_synchrodata
-- ----------------------------
DROP TABLE IF EXISTS `sys_synchrodata`;
CREATE TABLE `sys_synchrodata`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编码',
  `table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名字，用于查询',
  `table_field_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表字段，一定要用主键，用于查询',
  `table_field_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表字段值',
  `destination_queue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目的队列',
  `operation` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型：create,modify,delete',
  `priority_level` tinyint(1) NOT NULL COMMENT '同步优先级',
  `state` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '同步状态：draft,done,error',
  `description` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `error_message` varchar(550) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误消息',
  `synchro_time` datetime(0) NULL DEFAULT NULL COMMENT '同步时间',
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户id',
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户名称',
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新用户id',
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后更新用户名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据同步记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `login_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creater_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `modify_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('16b4b2f2-8760-11e9-a68d-0050568e63cd', '10002', '测试账号3', '100301', '', '', 'NDU3YzhjMWE0MWQ2', '100204', '100404', NULL, 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 15:03:57', '2019-06-06 08:30:58');
INSERT INTO `sys_user` VALUES ('21535604-93f2-11e9-a68d-0050568e63cd', '10006', '华为技术有限公司系统管理员', NULL, NULL, NULL, 'NDU3YzhjMWE0MWQ2', '100201', '100403', '63ecca81-85de-11e9-a68d-0050568e63cd', NULL, NULL, NULL, NULL, '2019-06-21 14:59:36', NULL);
INSERT INTO `sys_user` VALUES ('2964cb23-93f2-11e9-a68d-0050568e63cd', '10007', '腾讯科技有限公司系统管理员', NULL, NULL, NULL, 'NDU3YzhjMWE0MWQ2', '100201', '100402', '6f60e913-85de-11e9-a68d-0050568e63cd', NULL, NULL, NULL, NULL, '2019-06-21 14:59:49', NULL);
INSERT INTO `sys_user` VALUES ('2a4efb5b-93f2-11e9-a68d-0050568e63cd', '10008', '联想（中国）有限公司系统管理员', NULL, NULL, NULL, 'NDU3YzhjMWE0MWQ2', '100201', '100402', 'a65cbd8e-85ac-11e9-a68d-0050568e63cd', NULL, NULL, NULL, NULL, '2019-06-21 14:59:51', NULL);
INSERT INTO `sys_user` VALUES ('627dc2c8-87f2-11e9-a68d-0050568e63cd', '10003', '测试账号4', '100302', '', '', 'NDU3YzhjMWE0MWQ2', '100204', '100404', NULL, 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', NULL, NULL, '2019-06-06 08:31:11', NULL);
INSERT INTO `sys_user` VALUES ('8e55d855-87f2-11e9-a68d-0050568e63cd', '10004', '测试账号5', '100302', '10004@163.com', '17788810004', 'NDU3YzhjMWE0MWQ2', '100204', '100404', NULL, 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', NULL, NULL, '2019-06-06 08:32:25', NULL);
INSERT INTO `sys_user` VALUES ('c6fdf4db-875f-11e9-a68d-0050568e63cd', '10005', '测试账号1', '100301', '', '', 'NDU3YzhjMWE0MWQ2', '100204', '100404', NULL, 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 15:01:44', '2019-06-06 08:30:36');
INSERT INTO `sys_user` VALUES ('c7a1d29b-6191-11e9-a68d-0050568e63cd', '10000', '管理员', '100302', NULL, NULL, 'NDU3YzhjMWE0MWQ2', '100201', '100401', NULL, NULL, NULL, NULL, NULL, '2019-05-23 16:36:42', NULL);
INSERT INTO `sys_user` VALUES ('d0225332-875f-11e9-a68d-0050568e63cd', '10001', '测试账号2', '100302', '', '', 'NDU3YzhjMWE0MWQ2', '100204', '100404', NULL, 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', 'c7a1d29b-6191-11e9-a68d-0050568e63cd', 'admin', '2019-06-05 15:01:59', '2019-06-06 08:30:47');

-- ----------------------------
-- Function structure for test
-- ----------------------------
DROP FUNCTION IF EXISTS `test`;
delimiter ;;
CREATE FUNCTION `test`()
 RETURNS int(11)
  DETERMINISTIC
BEGIN
	#Routine body goes here...
    
		
	RETURN 0;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for test1
-- ----------------------------
DROP FUNCTION IF EXISTS `test1`;
delimiter ;;
CREATE FUNCTION `test1`()
 RETURNS int(11)
  DETERMINISTIC
BEGIN
	#Routine body goes here...

	RETURN 0;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for test2
-- ----------------------------
DROP PROCEDURE IF EXISTS `test2`;
delimiter ;;
CREATE PROCEDURE `test2`()
BEGIN
	#Routine body goes here...

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
