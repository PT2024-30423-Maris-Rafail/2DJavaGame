create table tips
(
    id             serial,
    tips           text not null,
    active_seconds integer
);

alter table tips
    owner to postgres;

