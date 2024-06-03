create table bucket_config
(
    id    bigint primary key not null,
    code  varchar(255) not null,
    value varchar(255) not null
);

create sequence seq_bucket_config_id
    minvalue 1
    maxvalue 99999999
    start with 1
    increment by 1 cache 1;

insert into bucket_config(id, code, value) values (nextval('seq_bucket_config_id'), 'dp.le.contract', 'cabinet');
