create table notification
(
    id bigint auto_increment primary key,
    notifier bigint not null comment '发送通知的人',
    receiver bigint not null comment '接受通知的人',
    outerid bigint not null comment '可能是问题的id,评论的id等等',
    type int not null,
    gmt_create bigint not null,
    status int default 0 not null comment '0 默认未读 1已读',
    notifier_name varchar(100) null,
    outer_title varchar(256) null
);




