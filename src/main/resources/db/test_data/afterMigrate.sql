set foreign_key_checks = 0;

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

insert into restaurant (name, shipping_fee, cuisine_id, address_address, address_zipcode, address_district, address_city_id, creation_Timestamp, update_Timestamp) values ('Feijó', 10.5, 2, 'Rua Santos Dumont', '85900-000', 'Centro', 1, utc_timestamp, utc_timestamp);
insert into restaurant (name, shipping_fee, cuisine_id, creation_Timestamp, update_Timestamp) values ('Central', 6, 1, utc_timestamp, utc_timestamp);
insert into restaurant (name, shipping_fee, cuisine_id, creation_Timestamp, update_Timestamp) values ('Filezão', 12, 1, utc_timestamp, utc_timestamp);

insert into product (name, description, price, active, restaurant_id) values ('Sopa Mineira', 'Sopa Mineira', 50, true, 1);
insert into product (name, description, price, active, restaurant_id) values ('Sopa Paulista', 'Sopa Paulista', 40, true, 1);
insert into product (name, description, price, active, restaurant_id) values ('Prato Feito', 'Prato Feito', 50, true, 2);
insert into product (name, description, price, active, restaurant_id) values ('Barreado', 'Barreado', 40, true, 2);
insert into product (name, description, price, active, restaurant_id) values ('Macarrão', 'Macarrão', 50, true, 3);
insert into product (name, description, price, active, restaurant_id) values ('Pizza', 'Pizza', 70, true, 3);

insert into permission (name, description) values ('Consulta Cozinhas', 'Consulta de Cozinhas');

insert into Payment_Type (description) values ('Money');
insert into Payment_Type (description) values ('Credit Card');

INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES(1, 1);
INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES(1, 2);
INSERT INTO restaurant_payment_type (restaurant_id, payment_type_id) VALUES(2, 1);