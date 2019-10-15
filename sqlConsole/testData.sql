create schema if not exists babytun collate utf8mb4_general_ci;

create table if not exists t_category
(
    category_id int auto_increment comment '产品分类'
        primary key,
    category_name varchar(32) not null comment '分类名称',
    parent_id int null comment '上级分类',
    category_level int not null comment '级别',
    category_order int not null comment '排序'
)
    charset=utf8;

create table if not exists t_goods
(
    goods_id int auto_increment comment '商品编号'
        primary key,
    title varchar(128) not null comment '商品名称',
    sub_title varchar(256) null comment '子标题',
    original_cost float not null comment '原价',
    current_price float not null comment '折后价',
    discount float not null comment '折扣(0~1)',
    is_free_delivery int not null comment '是否包邮',
    category_id int default 0 not null
)
    charset=utf8;

create table if not exists t_goods_cover
(
    gc_id int auto_increment
        primary key,
    goods_id int not null,
    gc_pic_url varchar(1024) not null,
    gc_thumb_url varchar(1024) not null,
    gc_order int not null
)
    charset=utf8;

create table if not exists t_goods_detail
(
    gd_id int auto_increment
        primary key,
    goods_id int not null,
    gd_pic_url varchar(1024) not null,
    gd_order int null
)
    charset=utf8;

create table if not exists t_goods_param
(
    gp_id int auto_increment
        primary key,
    gp_param_name varchar(128) not null,
    gp_param_value varchar(128) not null,
    goods_id int not null,
    gp_order int not null
)
    charset=utf8;

