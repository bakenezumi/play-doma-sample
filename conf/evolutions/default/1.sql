# Users schema
 
# --- !Ups
 
create table person(
    id int not null identity primary key,
    name varchar(20),
    age int,
    city varchar(20) not null,
    street varchar(20) not null,
    version int not null
);

insert into person (id, name, age, city, street, version) values(1, 'SMITH', 10, 'Tokyo', 'Yaesu', 0);
insert into person (id, name, age, city, street, version) values(2, 'ALLEN', 20, 'Kyoto', 'Karasuma', 0);
 
# --- !Downs
 
drop table person;