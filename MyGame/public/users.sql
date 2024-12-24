create table users
(
    id       serial
        constraint users_pk
            primary key,
    username varchar(50)  not null
        constraint users_pk_3
            unique,
    password varchar(20)  not null,
    email    varchar(100) not null
        constraint users_email_check
            check ((email)::text ~~ '%_@_%.__%'::text)
);

alter table users
    owner to postgres;

