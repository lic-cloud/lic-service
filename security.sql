/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : boot_security2

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 16/09/2020 08:39:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (1, 0, '用户管理', 'fa-users', '', 1, '', 1);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (2, 1, '用户查询', 'fa-user', 'pages/user/userList.html', 1, '', 2);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (3, 2, '查询', '', '', 2, 'sys:user:query', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (4, 2, '新增', '', '', 2, 'sys:user:add', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (6, 0, '修改密码', 'fa-pencil-square-o', 'pages/user/changePassword.html', 1, 'sys:user:password', 4);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (7, 0, '系统设置', 'fa-gears', '', 1, '', 5);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (8, 7, '菜单', 'fa-cog', 'pages/menu/menuList.html', 1, '', 6);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (9, 8, '查询', '', '', 2, 'sys:menu:query', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (10, 8, '新增', '', '', 2, 'sys:menu:add', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (11, 8, '删除', '', '', 2, 'sys:menu:del', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (12, 7, '角色', 'fa-user-secret', 'pages/role/roleList.html', 1, '', 7);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (13, 12, '查询', '', '', 2, 'sys:role:query', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (14, 12, '新增', '', '', 2, 'sys:role:add', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (15, 12, '删除', '', '', 2, 'sys:role:del', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (19, 0, '数据源监控', 'fa-eye', 'druid/index.html', 1, '', 9);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (20, 0, '接口swagger', 'fa-file-pdf-o', 'swagger-ui.html', 1, '', 10);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (22, 0, '公告管理', 'fa-book', 'pages/notice/noticeList.html', 1, '', 12);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (23, 22, '查询', '', '', 2, 'notice:query', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (24, 22, '添加', '', '', 2, 'notice:add', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (25, 22, '删除', '', '', 2, 'notice:del', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (37, 0, '字典管理', 'fa-reddit', 'pages/dict/dictList.html', 1, '', 17);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (38, 37, '查询', '', '', 2, 'dict:query', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (39, 37, '新增', '', '', 2, 'dict:add', 100);
INSERT INTO permission(id, parent_id, name, css, href, type, permission, sort)  VALUES (40, 37, '删除', '', '', 2, 'dict:del', 100);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `role`(name, description) VALUES ('ADMIN', '管理员');
INSERT INTO `role`(name, description) VALUES ('USER', '');


-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 1);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 2);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 3);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 4);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 6);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 7);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 8);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 9);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 10);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 11);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 12);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 13);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 14);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 15);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 19);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 20);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 22);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 23);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 24);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 25);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 37);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 38);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 39);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (1, 40);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 1);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 2);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 3);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 4);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 6);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 7);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 8);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 9);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 10);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 11);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 12);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 13);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 14);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 15);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 19);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 20);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 22);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 23);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 24);
INSERT INTO `role_permission`(role_id, permission_id) VALUES (2, 25);

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `role_user`(role_id, user_id) VALUES (1, 1);
INSERT INTO `role_user`(role_id, user_id) VALUES (2, 2);

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `lic_user`(id, username, password, nickname, status, total_capacity, used_capacity)
VALUES (1, 'admin', '$2a$10$iYM/H7TrSaLs7XyIWQdGwe1xf4cdmt3nwMja6RT0wxG5YY1RjN0EK', '管理员', 1,-1, 0);
INSERT INTO `lic_user`(id, username, password, nickname, status, total_capacity, used_capacity)
VALUES (2, 'user', '$2a$10$ooGb4wjT7Hg3zgU2RhZp6eVu3jvG29i/U4L6VRwiZZ4.DZ0OOEAHu', '用户', 1,10, 0);


INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (1, 'sex', '0', '女', '2017-11-17 09:58:24', '2017-11-18 14:21:05');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (2, 'sex', '1', '男', '2017-11-17 10:03:46', '2020-09-14 20:41:15');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (3, 'userStatus', '0', '无效', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (4, 'userStatus', '1', '正常', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (5, 'userStatus', '2', '锁定', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (6, 'noticeStatus', '0', '草稿', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (7, 'noticeStatus', '1', '发布', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (8, 'isRead', '0', '未读', '2017-11-17 16:26:06', '2017-11-17 16:26:09');
INSERT INTO `dict`(id, type, k, val, create_at, update_at) VALUES (9, 'isRead', '1', '已读', '2017-11-17 16:26:06', '2017-11-17 16:26:09');



INSERT INTO `notice`(id, title, content, status, create_at, update_at) VALUES (1, '知识星球交流圈', '1232131231', 1, '2020-09-14 18:05:44', '2020-09-14 18:05:44');


INSERT INTO `lic_notice_read`(notice_id, user_id, create_at) VALUES (1, 1, '2020-09-14 19:34:11');

SET FOREIGN_KEY_CHECKS = 1;
