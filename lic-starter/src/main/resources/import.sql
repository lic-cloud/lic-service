-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, NULL, NULL, 'fa-users', '', '用户管理', 0, '', 1, 1);
INSERT INTO `permission` VALUES (2, NULL, NULL, 'fa-user', 'pages/user/userList.html', '用户查询', 1, '', 2, 1);
INSERT INTO `permission` VALUES (3, NULL, NULL, '', '', '查询', 2, 'sys:user:query', 100, 2);
INSERT INTO `permission` VALUES (4, NULL, NULL, '', '', '新增', 2, 'sys:user:add', 100, 2);
INSERT INTO `permission` VALUES (6, NULL, NULL, 'fa-pencil-square-o', 'pages/user/changePassword.html', '修改密码', 0, 'sys:user:password', 4, 1);
INSERT INTO `permission` VALUES (7, NULL, NULL, 'fa-gears', '', '系统设置', 0, '', 5, 1);
INSERT INTO `permission` VALUES (8, NULL, NULL, 'fa-cog', 'pages/menu/menuList.html', '菜单', 7, '', 6, 1);
INSERT INTO `permission` VALUES (9, NULL, NULL, '', '', '查询', 8, 'sys:menu:query', 100, 2);
INSERT INTO `permission` VALUES (10, NULL, NULL, '', '', '新增', 8, 'sys:menu:add', 100, 2);
INSERT INTO `permission` VALUES (11, NULL, NULL, '', '', '删除', 8, 'sys:menu:del', 100, 2);
INSERT INTO `permission` VALUES (12, NULL, NULL, 'fa-user-secret', 'pages/role/roleList.html', '角色', 7, '', 7, 1);
INSERT INTO `permission` VALUES (13, NULL, NULL, '', '', '查询', 12, 'sys:role:query', 100, 2);
INSERT INTO `permission` VALUES (14, NULL, NULL, '', '', '新增', 12, 'sys:role:add', 100, 2);
INSERT INTO `permission` VALUES (15, NULL, NULL, '', '', '删除', 12, 'sys:role:del', 100, 2);
INSERT INTO `permission` VALUES (19, NULL, NULL, 'fa-eye', 'druid/index.html', '数据源监控', 0, '', 9, 1);
INSERT INTO `permission` VALUES (20, NULL, NULL, 'fa-file-pdf-o', 'doc.html', '接口knife4j', 0, '', 10, 1);
INSERT INTO `permission` VALUES (22, NULL, NULL, 'fa-book', 'pages/notice/noticeList.html', '公告管理', 0, '', 12, 1);
INSERT INTO `permission` VALUES (23, NULL, NULL, '', '', '查询', 22, 'notice:query', 100, 2);
INSERT INTO `permission` VALUES (24, NULL, NULL, '', '', '添加', 22, 'notice:add', 100, 2);
INSERT INTO `permission` VALUES (25, NULL, NULL, '', '', '删除', 22, 'notice:del', 100, 2);
INSERT INTO `permission` VALUES (37, NULL, NULL, 'fa-reddit', 'pages/dict/dictList.html', '字典管理', 0, '', 17, 1);
INSERT INTO `permission` VALUES (38, NULL, NULL, '', '', '查询', 37, 'dict:query', 100, 2);
INSERT INTO `permission` VALUES (39, NULL, NULL, '', '', '新增', 37, 'dict:add', 100, 2);
INSERT INTO `permission` VALUES (40, NULL, NULL, '', '', '删除', 37, 'dict:del', 100, 2);
INSERT INTO `permission` VALUES (41, '2020-10-11 12:10:24', '2020-10-11 12:11:05', 'fa-file', 'pages/file/file-list.html	', '文件列表', 0, '', 100, 1);
INSERT INTO `permission` VALUES (42, '2020-10-11 12:10:24', '2020-10-11 12:11:05', 'fa-file', 'pages/file/recycle.html	', '回收站', 0, '', 100, 1);

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '2020-10-24 15:24:36', '2020-10-24 15:24:32', '管理员所拥有的权限', 'ADMIN');
INSERT INTO `role` VALUES (2, '2020-10-24 15:24:37', '2020-10-24 15:24:35', '用户所拥有的权限', 'USER');

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (70, '2021-01-08 12:37:55', '2021-01-08 12:37:55', 41, 2);
INSERT INTO `role_permission` VALUES (71, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 1, 1);
INSERT INTO `role_permission` VALUES (72, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 2, 1);
INSERT INTO `role_permission` VALUES (73, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 3, 1);
INSERT INTO `role_permission` VALUES (74, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 4, 1);
INSERT INTO `role_permission` VALUES (75, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 6, 1);
INSERT INTO `role_permission` VALUES (76, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 7, 1);
INSERT INTO `role_permission` VALUES (77, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 8, 1);
INSERT INTO `role_permission` VALUES (78, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 9, 1);
INSERT INTO `role_permission` VALUES (79, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 10, 1);
INSERT INTO `role_permission` VALUES (80, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 11, 1);
INSERT INTO `role_permission` VALUES (81, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 12, 1);
INSERT INTO `role_permission` VALUES (82, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 13, 1);
INSERT INTO `role_permission` VALUES (83, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 14, 1);
INSERT INTO `role_permission` VALUES (84, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 15, 1);
INSERT INTO `role_permission` VALUES (85, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 19, 1);
INSERT INTO `role_permission` VALUES (86, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 20, 1);
INSERT INTO `role_permission` VALUES (87, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 22, 1);
INSERT INTO `role_permission` VALUES (88, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 23, 1);
INSERT INTO `role_permission` VALUES (89, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 24, 1);
INSERT INTO `role_permission` VALUES (90, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 25, 1);
INSERT INTO `role_permission` VALUES (91, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 37, 1);
INSERT INTO `role_permission` VALUES (92, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 38, 1);
INSERT INTO `role_permission` VALUES (93, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 39, 1);
INSERT INTO `role_permission` VALUES (94, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 40, 1);
INSERT INTO `role_permission` VALUES (95, '2021-01-08 12:38:01', '2021-01-08 12:38:01', 41, 1);

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES (1, '2017-11-17 09:58:24', '2017-11-18 14:21:05', '0', 'sex', '女');
INSERT INTO `dict` VALUES (2, '2017-11-17 10:03:46', '2020-09-14 20:41:15', '1', 'sex', '男');
INSERT INTO `dict` VALUES (3, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '0', 'userStatus', '无效');
INSERT INTO `dict` VALUES (4, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '1', 'userStatus', '正常');
INSERT INTO `dict` VALUES (5, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '2', 'userStatus', '锁定');
INSERT INTO `dict` VALUES (6, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '0', 'noticeStatus', '草稿');
INSERT INTO `dict` VALUES (7, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '1', 'noticeStatus', '发布');
INSERT INTO `dict` VALUES (8, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '0', 'isRead', '未读');
INSERT INTO `dict` VALUES (9, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '1', 'isRead', '已读');

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (2, '2020-10-24 07:52:09', '2020-10-24 07:52:09', '通知示例', 1, '通知示例');

