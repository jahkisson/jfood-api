set foreign_key_checks = 0;

delete from order_item;
delete from order_;
delete from restaurant_user_manager;
delete from restaurant_payment_type;
delete from payment_type;
delete from permission;
delete from product;
delete from restaurant;
delete from cuisine;
delete from city;
delete from state;
delete from role_permission;
delete from user_role;
delete from user;
delete from role;

set foreign_key_checks = 1;

alter table payment_type auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table cuisine auto_increment = 1;
alter table city auto_increment = 1;
alter table state auto_increment = 1;
alter table user auto_increment = 1;
alter table role auto_increment = 1;
alter table order_ auto_increment = 1;
alter table order_item auto_increment = 1;

insert into cuisine (id, name) values (1, 'Tailandesa');
insert into cuisine (id, name) values (2, 'Indiana');
insert into cuisine (id, name) values (3, 'Brasileira');
insert into cuisine (id, name) values (4, 'Mexicana');

insert into state (name) values ('Paraná');
insert into state (name) values ('São Paulo');
insert into state (name) values ('Rio de Janeiro');

insert into city (name, state_id) values ('Toledo', 1);
insert into city (name, state_id) values ('Curitiba', 1);
insert into city (name, state_id) values ('São Paulo', 2);
insert into city (name, state_id) values ('Rio de Janeiro', 3);

insert into restaurant (name, shipping_fee, cuisine_id, address_address, address_zipcode, address_district, address_city_id, creation_Timestamp, update_Timestamp, active, open) values ('Feijó', 10.5, 2, 'Rua Santos Dumont', '85900-000', 'Centro', 1, utc_timestamp, utc_timestamp, 1, 1);
insert into restaurant (name, shipping_fee, cuisine_id, creation_Timestamp, update_Timestamp, active, open) values ('Central', 6, 1, utc_timestamp, utc_timestamp, 1, 1);
insert into restaurant (name, shipping_fee, cuisine_id, creation_Timestamp, update_Timestamp, active, open) values ('Filezão', 12, 1, utc_timestamp, utc_timestamp, 1, 1);

insert into product (name, description, price, active, restaurant_id) values ('Sopa Mineira', 'Sopa Mineira', 50, true, 1);
insert into product (name, description, price, active, restaurant_id) values ('Sopa Paulista', 'Sopa Paulista', 40, true, 1);
insert into product (name, description, price, active, restaurant_id) values ('Prato Feito', 'Prato Feito', 50, true, 2);
insert into product (name, description, price, active, restaurant_id) values ('Barreado', 'Barreado', 40, true, 2);
insert into product (name, description, price, active, restaurant_id) values ('Macarrão', 'Macarrão', 50, true, 3);
insert into product (name, description, price, active, restaurant_id) values ('Pizza', 'Pizza', 70, true, 3);

insert into permission (name, description) values ('Consulta_Cozinhas', 'Consulta de Cozinhas');
insert into permission (name, description) values ('Consulta_Restaurantes', 'Consulta de Restaurantes');

insert into Payment_Type (description) values ('Money');
insert into Payment_Type (description) values ('Credit Card');

INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES(1, 1);
INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES(1, 2);
INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES(2, 1);

INSERT INTO role (id, name) values (1, 'Manager'), (2, 'Salesman'), (3, 'Secretary'), (4, 'Registerman');

INSERT INTO role_permission (role_id, permission_id) values (1, 1), (1, 2), (2, 1), (3, 2), (4, 1), (4, 2);

INSERT INTO user (name, email, password, creation_date) values ('Jackson', 'jackson@email.com', 'password', utc_timestamp);
INSERT INTO user (name, email, password, creation_date) values ('Mary', 'mary@email.com', 'password', utc_timestamp);
INSERT INTO user (name, email, password, creation_date) values ('Denise', 'denise@email.com', 'password', utc_timestamp);

INSERT INTO user_role (user_id, role_id) values (1, 1), (1, 2), (2, 2);

INSERT INTO restaurant_user_manager (restaurant_id, user_id) values (1, 1);

INSERT INTO order_ (id, code, subtotal, shipping_fee, total_value, restaurant_id, customer_id, payment_type_id, address_address, address_district, address_zipcode, address_city_id, status, creation_date) 
VALUES(1, '9247f660-87d6-4856-8c84-c58d3f2254f3', 100, 10, 110, 1, 1, 1, 'Rua das couves', 'Centro', '85968586', 1, 'Created', utc_timestamp);
INSERT INTO order_ (id, code, subtotal, shipping_fee, total_value, restaurant_id, customer_id, payment_type_id, address_address, address_district, address_zipcode, address_city_id, status, creation_date) 
VALUES(2, 'a5d9797d-1fbd-441f-8f5e-74d8c56bbcf7', 215, 15, 230, 2, 2, 2, 'Avenida Principal', 'Bairro', '12345678', 2, 'Created', utc_timestamp);

INSERT INTO order_item (quantity, unit_price, total_price, additional_info, order_id, product_id) 
VALUES(2, 50, 100, 'Sem cebola', 1, 1);
INSERT INTO order_item (quantity, unit_price, total_price, additional_info, order_id, product_id) 
VALUES(3, 50, 150, 'Sem queijo', 2, 1);
INSERT INTO order_item (quantity, unit_price, total_price, additional_info, order_id, product_id) 
VALUES(1, 65, 65, 'Sem orégano', 2, 2);
