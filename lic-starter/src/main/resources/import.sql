-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, NULL, NULL, 'fa-users', '', '用户管理', 0, '', 1, 1);
INSERT INTO `sys_permission` VALUES (2, NULL, NULL, 'fa-user', 'pages/user/userList.html', '用户查询', 1, '', 2, 1);
INSERT INTO `sys_permission` VALUES (3, NULL, NULL, '', '', '查询', 2, 'sys:user:query', 100, 2);
INSERT INTO `sys_permission` VALUES (4, NULL, NULL, '', '', '新增', 2, 'sys:user:add', 100, 2);
INSERT INTO `sys_permission` VALUES (6, NULL, NULL, 'fa-pencil-square-o', 'pages/user/changePassword.html', '修改密码', 0, 'sys:user:password', 4, 1);
INSERT INTO `sys_permission` VALUES (7, NULL, NULL, 'fa-gears', '', '系统设置', 0, '', 5, 1);
INSERT INTO `sys_permission` VALUES (8, NULL, NULL, 'fa-cog', 'pages/menu/menuList.html', '菜单', 7, '', 6, 1);
INSERT INTO `sys_permission` VALUES (9, NULL, NULL, '', '', '查询', 8, 'sys:menu:query', 100, 2);
INSERT INTO `sys_permission` VALUES (10, NULL, NULL, '', '', '新增', 8, 'sys:menu:add', 100, 2);
INSERT INTO `sys_permission` VALUES (11, NULL, NULL, '', '', '删除', 8, 'sys:menu:del', 100, 2);
INSERT INTO `sys_permission` VALUES (12, NULL, NULL, 'fa-user-secret', 'pages/role/roleList.html', '角色', 7, '', 7, 1);
INSERT INTO `sys_permission` VALUES (13, NULL, NULL, '', '', '查询', 12, 'sys:role:query', 100, 2);
INSERT INTO `sys_permission` VALUES (14, NULL, NULL, '', '', '新增', 12, 'sys:role:add', 100, 2);
INSERT INTO `sys_permission` VALUES (15, NULL, NULL, '', '', '删除', 12, 'sys:role:del', 100, 2);
INSERT INTO `sys_permission` VALUES (19, NULL, NULL, 'fa-eye', 'druid/index.html', '数据源监控', 0, '', 9, 1);
INSERT INTO `sys_permission` VALUES (20, NULL, NULL, 'fa-file-pdf-o', 'doc.html', '接口knife4j', 0, '', 10, 1);
INSERT INTO `sys_permission` VALUES (22, NULL, NULL, 'fa-book', 'pages/notice/noticeList.html', '公告管理', 0, '', 12, 1);
INSERT INTO `sys_permission` VALUES (23, NULL, NULL, '', '', '查询', 22, 'notice:query', 100, 2);
INSERT INTO `sys_permission` VALUES (24, NULL, NULL, '', '', '添加', 22, 'notice:add', 100, 2);
INSERT INTO `sys_permission` VALUES (25, NULL, NULL, '', '', '删除', 22, 'notice:del', 100, 2);
INSERT INTO `sys_permission` VALUES (37, NULL, NULL, 'fa-reddit', 'pages/dict/dictList.html', '字典管理', 0, '', 17, 1);
INSERT INTO `sys_permission` VALUES (38, NULL, NULL, '', '', '查询', 37, 'dict:query', 100, 2);
INSERT INTO `sys_permission` VALUES (39, NULL, NULL, '', '', '新增', 37, 'dict:add', 100, 2);
INSERT INTO `sys_permission` VALUES (40, NULL, NULL, '', '', '删除', 37, 'dict:del', 100, 2);
INSERT INTO `sys_permission` VALUES (41, '2020-10-11 12:10:24', '2020-10-11 12:11:05', 'fa-file', 'pages/file/test.html	', '文件列表', 0, '', 100, 1);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '2020-10-24 15:24:36', '2020-10-24 15:24:32', '管理员', 'ADMIN');
INSERT INTO `sys_role` VALUES (2, '2020-10-24 15:24:37', '2020-10-24 15:24:35', '', 'USER');

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (25, NULL, NULL, 1, 2);
INSERT INTO `sys_role_permission` VALUES (26, NULL, NULL, 2, 2);
INSERT INTO `sys_role_permission` VALUES (27, NULL, NULL, 3, 2);
INSERT INTO `sys_role_permission` VALUES (28, NULL, NULL, 4, 2);
INSERT INTO `sys_role_permission` VALUES (29, NULL, NULL, 6, 2);
INSERT INTO `sys_role_permission` VALUES (30, NULL, NULL, 7, 2);
INSERT INTO `sys_role_permission` VALUES (31, NULL, NULL, 8, 2);
INSERT INTO `sys_role_permission` VALUES (32, NULL, NULL, 9, 2);
INSERT INTO `sys_role_permission` VALUES (33, NULL, NULL, 10, 2);
INSERT INTO `sys_role_permission` VALUES (34, NULL, NULL, 11, 2);
INSERT INTO `sys_role_permission` VALUES (35, NULL, NULL, 12, 2);
INSERT INTO `sys_role_permission` VALUES (36, NULL, NULL, 13, 2);
INSERT INTO `sys_role_permission` VALUES (37, NULL, NULL, 14, 2);
INSERT INTO `sys_role_permission` VALUES (38, NULL, NULL, 15, 2);
INSERT INTO `sys_role_permission` VALUES (39, NULL, NULL, 19, 2);
INSERT INTO `sys_role_permission` VALUES (40, NULL, NULL, 20, 2);
INSERT INTO `sys_role_permission` VALUES (41, NULL, NULL, 22, 2);
INSERT INTO `sys_role_permission` VALUES (42, NULL, NULL, 23, 2);
INSERT INTO `sys_role_permission` VALUES (43, NULL, NULL, 24, 2);
INSERT INTO `sys_role_permission` VALUES (44, NULL, NULL, 25, 2);
INSERT INTO `sys_role_permission` VALUES (45, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 1, 1);
INSERT INTO `sys_role_permission` VALUES (46, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 2, 1);
INSERT INTO `sys_role_permission` VALUES (47, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 3, 1);
INSERT INTO `sys_role_permission` VALUES (48, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 4, 1);
INSERT INTO `sys_role_permission` VALUES (49, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 6, 1);
INSERT INTO `sys_role_permission` VALUES (50, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 7, 1);
INSERT INTO `sys_role_permission` VALUES (51, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 8, 1);
INSERT INTO `sys_role_permission` VALUES (52, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 9, 1);
INSERT INTO `sys_role_permission` VALUES (53, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 10, 1);
INSERT INTO `sys_role_permission` VALUES (54, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 11, 1);
INSERT INTO `sys_role_permission` VALUES (55, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 12, 1);
INSERT INTO `sys_role_permission` VALUES (56, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 13, 1);
INSERT INTO `sys_role_permission` VALUES (57, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 14, 1);
INSERT INTO `sys_role_permission` VALUES (58, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 15, 1);
INSERT INTO `sys_role_permission` VALUES (59, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 19, 1);
INSERT INTO `sys_role_permission` VALUES (60, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 20, 1);
INSERT INTO `sys_role_permission` VALUES (61, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 22, 1);
INSERT INTO `sys_role_permission` VALUES (62, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 23, 1);
INSERT INTO `sys_role_permission` VALUES (63, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 24, 1);
INSERT INTO `sys_role_permission` VALUES (64, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 25, 1);
INSERT INTO `sys_role_permission` VALUES (65, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 37, 1);
INSERT INTO `sys_role_permission` VALUES (66, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 38, 1);
INSERT INTO `sys_role_permission` VALUES (67, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 39, 1);
INSERT INTO `sys_role_permission` VALUES (68, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 40, 1);
INSERT INTO `sys_role_permission` VALUES (69, '2020-10-11 12:10:30', '2020-10-11 12:10:30', 41, 1);

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES (1, '2017-11-17 09:58:24', '2017-11-18 14:21:05', '0', 'sex', '女');
INSERT INTO `t_dict` VALUES (2, '2017-11-17 10:03:46', '2020-09-14 20:41:15', '1', 'sex', '男');
INSERT INTO `t_dict` VALUES (3, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '0', 'userStatus', '无效');
INSERT INTO `t_dict` VALUES (4, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '1', 'userStatus', '正常');
INSERT INTO `t_dict` VALUES (5, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '2', 'userStatus', '锁定');
INSERT INTO `t_dict` VALUES (6, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '0', 'noticeStatus', '草稿');
INSERT INTO `t_dict` VALUES (7, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '1', 'noticeStatus', '发布');
INSERT INTO `t_dict` VALUES (8, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '0', 'isRead', '未读');
INSERT INTO `t_dict` VALUES (9, '2017-11-17 16:26:06', '2017-11-17 16:26:09', '1', 'isRead', '已读');

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES (2, '2020-10-24 07:52:09', '2020-10-24 07:52:09', '123123', 1, '123123');

-- ----------------------------
-- Records of t_notice_read
-- ----------------------------
INSERT INTO `t_notice_read` VALUES (3, '2020-10-24 07:53:28', '2020-10-24 07:53:28', 2, 1);
