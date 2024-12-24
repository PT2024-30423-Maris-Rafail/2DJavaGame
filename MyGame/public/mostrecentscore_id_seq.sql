create sequence mostrecentscore_id_seq
    as integer;

alter sequence mostrecentscore_id_seq owner to postgres;

alter sequence mostrecentscore_id_seq owned by "bestScores".id;

