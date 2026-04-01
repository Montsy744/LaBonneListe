CREATE TABLE users (
    id SERIAL,
    login VARCHAR(50) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,

    CONSTRAINT pk_user PRIMARY KEY (id)
);