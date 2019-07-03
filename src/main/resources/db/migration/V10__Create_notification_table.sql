create table notification
(
	id bigint auto_increment,
	notifier bigint not null,
	receiver bigint not null,
	outerId bigint not null,
	type int not null,
	gmt_create bigint not null,
	status int default 0 not null
);

comment on column notification.notifier is '发送通知的人';

comment on column notification.receiver is '接受消息的人';

comment on column notification.outerId is '可能是问题的id,评论的id等等';

comment on column notification.status is '0 默认未读 1已读';

create unique index notification_id_uindex
	on notification (id);

alter table notification
	add constraint notification_pk
		primary key (id);





