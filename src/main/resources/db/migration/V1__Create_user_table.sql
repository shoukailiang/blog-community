create table user
(
    id bigint auto_increment primary key not null,
    account_id varchar(100),
    name varchar(50),
    token varchar(36),
    bio varchar(256) null,
    avatar_url varchar(100) null,
    gmt_create bigint,
    gmt_modified bigint
)