create database if not exists smart_picture_storage_wenjelly default character set utf8mb4 collate utf8mb4_unicode_ci;

use smart_picture_storage_wenjelly;
-- 用户表
-- 扩展设计
-- 1）如果要实现会员功能，可以对表进行如下扩展：
-- 1.给 userRole 字段新增枚举值 vip，表示会员用户，可根据该值判断用户权限
-- 2.新增会员过期时间字段，可用于记录会员有效期
-- 3.新增会员兑换码字段，可用于记录会员的开通方式
-- 4.新增会员编号字段，可便于定位用户并提供额外服务，并增加会员归属感
--
-- 2）如果要实现用户邀请功能，可以对表进行如下扩展：
-- 1.新增 shareCode 分享码字段，用于记录每个用户的唯一邀请标识，可拼接到邀请网址后面，比如 https://mianshiya.com/?shareCode=xxx
-- 2.新增 inviteUser 字段，用于记录该用户被哪个用户邀请了，可通过这个字段查询某用户邀请的用户列表。
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin',
    editTime     datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    vipExpireTime datetime     null comment '会员过期时间',
    vipCode       varchar(128) null comment '会员兑换码',
    vipNumber     bigint       null comment '会员编号',
    shareCode     varchar(20)  DEFAULT NULL COMMENT '分享码',
    inviteUser    bigint       DEFAULT NULL COMMENT '邀请用户 id',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    UNIQUE KEY uk_userAccount (userAccount),
    INDEX idx_userName (userName)
    ) comment '用户' collate = utf8mb4_unicode_ci;
