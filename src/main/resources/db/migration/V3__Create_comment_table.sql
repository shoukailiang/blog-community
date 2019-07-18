create table comment
(
    id bigint auto_increment primary key,
    parent_id bigint not null comment '父类ID',
    type int not null comment '父类类型',
    commentator bigint not null comment '评论人id' ,
    content varchar(1024) null,
    gmt_create bigint not null,
    gmt_modified bigint not null,
    like_count bigint default 0,
    comment_count int default 0
);

