INSERT INTO sys_role
(ID, ROLE_NAME, ROLE_DESC)
VALUES(1, 'ROLE_USER', '基本角色');
INSERT INTO sys_role
(ID, ROLE_NAME, ROLE_DESC)
VALUES(2, 'ROLE_ADMIN', '超级管理员');
INSERT INTO sys_role
(ID, ROLE_NAME, ROLE_DESC)
VALUES(3, 'ROLE_PRODUCT', '管理产品');
INSERT INTO sys_role
(ID, ROLE_NAME, ROLE_DESC)
VALUES(4, 'ROLE_ORDER', '管理订单');

--------------------------------------

INSERT INTO sys_user
(id, username, password, status)
VALUES(1, 'xiaoming', '$2a$10$CYX9OMv0yO8wR8rE19N2fOaXDJondci5uR68k2eQJm50q8ESsDMlC', 1);
INSERT INTO sys_user
(id, username, password, status)
VALUES(2, 'xiaoma', '$2a$10$CYX9OMv0yO8wR8rE19N2fOaXDJondci5uR68k2eQJm50q8ESsDMlC', 1);


--------------------------------------

INSERT INTO sys_user_role
(UID, RID)
VALUES(1, 1);
INSERT INTO sys_user_role
(UID, RID)
VALUES(2, 1);
INSERT INTO sys_user_role
(UID, RID)
VALUES(1, 3);
INSERT INTO sys_user_role
(UID, RID)
VALUES(2, 4);
