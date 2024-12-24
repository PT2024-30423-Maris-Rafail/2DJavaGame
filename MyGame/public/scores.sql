create table scores
(
    score_id     serial
        constraint scores_pk
            primary key,
    hours        integer default 0 not null,
    minutes      integer default 0 not null,
    seconds      integer default 0 not null,
    milliseconds integer default 0 not null,
    user_id      integer           not null
        constraint scores_users_id_fk
            references users
);

alter table scores
    owner to postgres;

