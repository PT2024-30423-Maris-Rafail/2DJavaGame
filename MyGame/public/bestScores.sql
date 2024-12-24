create table "bestScores"
(
    id       integer default nextval('mostrecentscore_id_seq'::regclass) not null
        constraint mostrecentscore_pk
            primary key,
    user_id  integer                                                     not null,
    score_id integer                                                     not null
);

alter table "bestScores"
    owner to postgres;

