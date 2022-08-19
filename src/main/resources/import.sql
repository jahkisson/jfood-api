insert into cuisine (name) values ('Tailandesa');
insert into cuisine (name) values ('Indiana');

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