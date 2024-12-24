create table messages
(
    message text,
    id      serial
);

alter table messages
    owner to postgres;

