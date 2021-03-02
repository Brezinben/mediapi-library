--CREATE EXTENSION citext;
/*CREATE TYPE "item_state" AS ENUM ('NEUF','CONVENABLE','ABIMER','INUTILISABLE');
CREATE TYPE "loan_state" AS ENUM ('EN_COURS','RENDU','EN_RETARD');
CREATE TYPE "item_type" AS ENUM ('CD','VINYLE','CASSETTE');*/

CREATE TABLE users
(
    id         SERIAL       NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    CONSTRAINT users_pk
        PRIMARY KEY (id)
);

ALTER TABLE users
    OWNER TO postgres;

CREATE UNIQUE INDEX users_id_uindex
    ON users (id);

CREATE UNIQUE INDEX users_email_uindex
    ON users (email);

CREATE TABLE groups
(
    id         SERIAL       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    CONSTRAINT groups_pk
        PRIMARY KEY (id)
);

ALTER TABLE groups
    OWNER TO postgres;

CREATE UNIQUE INDEX groups_id_uindex
    ON groups (id);

CREATE TABLE albums
(
    id       SERIAL    NOT NULL,
    title    citext    NOT NULL,
    release  TIMESTAMP NOT NULL,
    group_id INTEGER,
    CONSTRAINT albums_pk
        PRIMARY KEY (id),
    CONSTRAINT group_id
        FOREIGN KEY (group_id) REFERENCES groups
);

ALTER TABLE albums
    OWNER TO postgres;

CREATE UNIQUE INDEX albums_id_uindex
    ON albums (id);

CREATE TABLE items
(
    id             SERIAL       NOT NULL,
    state          VARCHAR(255) NOT NULL,
    type           VARCHAR(255) NOT NULL,
    album_id       INTEGER,
    created_at     TIMESTAMP,
    active_loan_id INTEGER,
    CONSTRAINT items_pk
        PRIMARY KEY (id),
    CONSTRAINT album_id_fk
        FOREIGN KEY (album_id) REFERENCES albums
);

ALTER TABLE items
    OWNER TO postgres;

CREATE UNIQUE INDEX items_id_uindex
    ON items (id);

CREATE UNIQUE INDEX items_active_loan_id_uindex
    ON items (active_loan_id);

CREATE TABLE loans
(
    id         SERIAL       NOT NULL,
    date_start TIMESTAMP    NOT NULL,
    date_end   TIMESTAMP    NOT NULL,
    status     VARCHAR(255) NOT NULL,
    user_id    INTEGER,
    item_id    INTEGER,
    CONSTRAINT loans_pk
        PRIMARY KEY (id),
    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id) REFERENCES users,
    CONSTRAINT item_id_fk
        FOREIGN KEY (item_id) REFERENCES items
);

ALTER TABLE loans
    OWNER TO postgres;

ALTER TABLE items
    ADD CONSTRAINT active_loan_id
        FOREIGN KEY (active_loan_id) REFERENCES loans;

CREATE UNIQUE INDEX loans_id_uindex
    ON loans (id);

