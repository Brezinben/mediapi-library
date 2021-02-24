CREATE EXTENSION citext;
CREATE TYPE "state" AS ENUM ('Neuf', 'Convenable', 'Abîmer','Inutilisable');
CREATE TYPE "loanState" AS ENUM ('Neuf', 'Convenable', 'Abîmer','Inutilisable');
CREATE TYPE "itemType" AS ENUM ('Vinyle', 'CD', 'Cassette');

create table users
(
    id         serial       not null
        constraint users_pk
            primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null
);

alter table users
    owner to postgres;

create unique index users_id_uindex
    on users (id);

create unique index users_email_uindex
    on users (email);

create table groups
(
    id         serial       not null
        constraint groups_pk
            primary key,
    name       varchar(255) not null,
    created_at date         not null
);

alter table groups
    owner to postgres;

create unique index groups_id_uindex
    on groups (id);

create table albums
(
    id       serial not null
        constraint albums_pk
            primary key,
    title    citext not null,
    release  date   not null,
    group_id integer
        constraint group_id
            references groups
);

alter table albums
    owner to postgres;

create unique index albums_id_uindex
    on albums (id);

create table items
(
    id       serial   not null
        constraint items_pk
            primary key,
    state    state    not null,
    type     itemtype not null,
    album_id integer
        constraint album_id_fk
            references albums
);

alter table items
    owner to postgres;

create unique index items_id_uindex
    on items (id);

create table loans
(
    id         serial    not null
        constraint loans_pk
            primary key,
    date_start date      not null,
    date_end   date      not null,
    status     loanstate not null,
    user_id    integer
        constraint user_id_fk
            references users,
    items_id   integer
        constraint item_id_fk
            references items
);

alter table loans
    owner to postgres;

create unique index loans_id_uindex
    on loans (id);

