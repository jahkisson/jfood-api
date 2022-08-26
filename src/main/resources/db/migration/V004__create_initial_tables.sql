create table payment_type (
    id bigint not null auto_increment, 
    description varchar(60) not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table permission (
    id bigint not null auto_increment, 
    description varchar(120), 
    name varchar(60) not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table product (
    id bigint not null auto_increment, 
    active bit not null, 
    description varchar(255) not null, 
    name varchar(60) not null, 
    price decimal(19,2) not null, 
    restaurant_id bigint not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant (
    id bigint not null auto_increment, 
    address_address varchar(80), 
    address_district varchar(60), 
    address_zipcode varchar(20), 
    creation_timestamp datetime not null, 
    name varchar(60) not null, 
    shipping_fee decimal(19,2) not null, 
    update_timestamp datetime not null, 
    address_city_id bigint, 
    cuisine_id bigint not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurant_payment_type (
    restaurant_id bigint not null, 
    payment_type_id bigint not null
) engine=InnoDB default charset=utf8;

create table role (
    id bigint not null auto_increment, 
    name varchar(60) not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table role_permission (
    role_id bigint not null, 
    permission_id bigint not null
) engine=InnoDB default charset=utf8;

create table user (
    id bigint not null auto_increment, 
    creation_date datetime not null, 
    email varchar(80) not null, 
    name varchar(60) not null, 
    password varchar(20) not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table user_role (
    user_id bigint not null, 
    role_id bigint not null
) engine=InnoDB default charset=utf8;

alter table product add constraint fk_product_restaurant 
    foreign key (restaurant_id) references restaurant (id);

alter table restaurant add constraint fk_restaurant_city 
    foreign key (address_city_id) references city (id);

alter table restaurant add constraint fk_restaurant_cuisine 
    foreign key (cuisine_id) references cuisine (id);

alter table restaurant_payment_type add constraint fk_restaurant_payment_type_payment_type 
    foreign key (payment_type_id) references payment_type (id);

alter table restaurant_payment_type add constraint fk_restaurant_payment_type_restaurant 
    foreign key (restaurant_id) references restaurant (id);

alter table role_permission add constraint fk_role_permission_permission 
    foreign key (permission_id) references permission (id);

alter table role_permission add constraint fk_role_permission_role
     foreign key (role_id) references role (id);

alter table user_role add constraint fk_user_role_role 
    foreign key (role_id) references role (id);

alter table user_role add constraint fk_user_role_user 
    foreign key (user_id) references user (id);