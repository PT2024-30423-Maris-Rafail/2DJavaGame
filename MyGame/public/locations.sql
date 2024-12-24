create table locations
(
    id          serial
        constraint locations_pk
            primary key,
    coordinates varchar(40)           not null
        constraint locations_pk_3
            unique,
    checked     boolean default false not null,
    country_id  integer               not null
);

alter table locations
    owner to postgres;

