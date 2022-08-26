create table order_ (
    id bigint not null auto_increment,
    subtotal decimal(10,2) not null,
    shipping_fee decimal(10,2) not null,
    total_value decimal(10,2) not null,
    restaurant_id bigint not null,
    customer_id bigint not null,
    payment_type_id bigint not null,
    address_address varchar(80) not null, 
    address_district varchar(60) not null, 
    address_zipcode varchar(20) not null,
    address_city_id bigint not null, 
    status varchar(10) not null,
    creation_date datetime not null,
    confirmation_date datetime null,
    cancel_date datetime null,
    delivery_date datetime null,
    
    primary key (id),
    constraint fk_order_address_city foreign key (address_city_id) references city (id),
    constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
    constraint fk_order_user_customer foreign key (customer_id) references user (id),
    constraint fk_order_payment_type foreign key (payment_type_id) references payment_type (id)
) engine=InnoDB default charset=utf8;

create table order_item (
    id bigint not null auto_increment,
    quantity smallint(6) not null,
    unit_price decimal(10,2) not null,
    total_price decimal(10,2) not null,
    additional_info varchar(255) null,
    order_id bigint not null,
    product_id bigint not null,
    
    primary key (id),
    unique key uk_order_item_product (order_id, product_id),

    constraint fk_order_item_order foreign key (order_id) references order_ (id),
    constraint fk_order_item_product foreign key (product_id) references product (id)
) engine=InnoDB default charset=utf8;