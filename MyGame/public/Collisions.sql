create table "Collisions"
(
    name      varchar(100) not null
        constraint table_name_pk
            primary key,
    collision boolean      not null
);

alter table "Collisions"
    owner to postgres;

