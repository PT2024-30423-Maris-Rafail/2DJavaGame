create table country
(
    id           serial
        constraint country_pk
            primary key,
    country_name varchar(50) not null
        constraint country_pk_3
            unique
);

alter table country
    owner to postgres;

