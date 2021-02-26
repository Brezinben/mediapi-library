--CREATE  EXTENSION citext;
CREATE TYPE "item_state" AS ENUM ('NEUF','CONVENABLE','ABIMER','INUTILISABLE');
CREATE TYPE "loan_state" AS ENUM ('EN_COURS','RENDU','EN_RETARD');
CREATE TYPE "item_type" AS ENUM ('CD','VINYLE','CASSETTE');

CREATE TABLE users
(
    id         SERIAL       NOT NULL
        CONSTRAINT users_pk
            PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL
);

ALTER TABLE users
    OWNER TO postgres;

CREATE UNIQUE INDEX users_id_uindex
    ON users (id);

CREATE UNIQUE INDEX users_email_uindex
    ON users (email);

CREATE TABLE groups
(
    id         SERIAL       NOT NULL
        CONSTRAINT groups_pk
            PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL
);

ALTER TABLE groups
    OWNER TO postgres;

CREATE UNIQUE INDEX groups_id_uindex
    ON groups (id);

CREATE TABLE albums
(
    id       SERIAL    NOT NULL
        CONSTRAINT albums_pk
            PRIMARY KEY,
    title    citext    NOT NULL,
    release  TIMESTAMP NOT NULL,
    group_id INTEGER
        CONSTRAINT group_id
            REFERENCES groups
);

ALTER TABLE albums
    OWNER TO postgres;

CREATE UNIQUE INDEX albums_id_uindex
    ON albums (id);

CREATE TABLE items
(
    id       SERIAL     NOT NULL
        CONSTRAINT items_pk
            PRIMARY KEY,
    state    item_state NOT NULL,
    type     item_type  NOT NULL,
    album_id INTEGER
        CONSTRAINT album_id_fk
            REFERENCES albums
);

ALTER TABLE items
    OWNER TO postgres;

CREATE UNIQUE INDEX items_id_uindex
    ON items (id);

CREATE TABLE loans
(
    id              SERIAL     NOT NULL
        CONSTRAINT loans_pk
            PRIMARY KEY,
    TIMESTAMP_start TIMESTAMP  NOT NULL,
    TIMESTAMP_end   TIMESTAMP  NOT NULL,
    status          loan_state NOT NULL,
    user_id         INTEGER
        CONSTRAINT user_id_fk
            REFERENCES users,
    items_id        INTEGER
        CONSTRAINT item_id_fk
            REFERENCES items
);

ALTER TABLE loans
    OWNER TO postgres;

CREATE UNIQUE INDEX loans_id_uindex
    ON loans (id);

