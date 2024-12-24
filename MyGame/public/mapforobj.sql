create table mapforobj
(
    name      varchar(50) not null
        constraint mapforobj_pk
            unique,
    mapid     integer     not null,
    collision boolean     not null
);

alter table mapforobj
    owner to postgres;

