drop schema if exists calculation CASCADE;
CREATE SCHEMA calculation;

-- create schema commission.calculation;

create table calculation.users
(
    id         bigserial
        constraint users_pk
            primary key,
    lastname   text not null,
    firstname  text not null,
    patronymic text
);

create unique index users_id_uindex
    on calculation.users (id);

create table calculation.phone_numbers
(
    id           bigserial
        constraint phone_numbers_pk
            primary key,
    phone_number bigint not null
);
create unique index phone_numbers_id_uindex
    on calculation.phone_numbers (id);


create table calculation.payments
(
    id                 bigserial
        constraint payments_pk
            primary key,
    sum_payment        decimal not null,
    date_payment       date    not null,
    comment_by_payment text,
    user_id            bigint
        constraint payments_users_id_fk
            references calculation.users
            on delete cascade
);
create unique index payments_id_uindex
    on calculation.payments (id);


create table calculation.commission
(
    id          bigserial
        constraint commission_pk
            primary key,
    tax         decimal not null,
    payments_id bigint  not null
        constraint commission_payments_id_fk
            references calculation.payments
            on delete cascade
);

create unique index commission_id_uindex
    on calculation.commission (id);

create unique index commission_payments_id_uindex
    on calculation.commission (payments_id);


create table calculation.users_join_phone_numbers
(
    user_id         bigint not null
        constraint users_join_phone_numbers_users_id_fk
            references calculation.users
            on delete cascade,
    phone_number_id bigint not null
        constraint users_join_phone_numbers_phone_numbers_id_fk
            references calculation.phone_numbers
            on delete cascade,
    constraint users_join_phone_numbers_pk
        primary key (user_id, phone_number_id)
);

create unique index phone_numbers_phone_number_uindex
    on calculation.phone_numbers (phone_number);
