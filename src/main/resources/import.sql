insert into cuisine (name) values ('Tailandesa');
insert into cuisine (name) values ('Indiana');

insert into restaurant (name, shipping_fee, cuisine_id) values ('Feijó', 10.5, 2);
insert into restaurant (name, shipping_fee, cuisine_id) values ('Central', 6, 1);

insert into state (name) values ('Paraná');
insert into state (name) values ('São Paulo');
insert into state (name) values ('Rio de Janeiro');

insert into city (name, state_id) values ('Toledo', 1);
insert into city (name, state_id) values ('Curitiba', 1);
insert into city (name, state_id) values ('São Paulo', 2);
insert into city (name, state_id) values ('Rio de Janeiro', 3);

insert into permission (name, description) values ('Consulta Cozinhas', 'Consulta de Cozinhas');

insert into Payment_Type (description) values ('Money');
insert into Payment_Type (description) values ('Credit Card');