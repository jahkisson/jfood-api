create table state (
	id bigint not null auto_increment,
	name varchar(80) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

insert into state (name) 
select distinct state_name from city;

alter table city add column state_id bigint not null;

update city set state_id = (select id from state where name = state_name);

alter table city add constraint fk_city_state foreign key (state_id) references state (id);

alter table city drop column state_name;

alter table city add column name varchar(80) not null;
update city set name = city_name;
alter table city drop column city_name;