CREATE TABLE users (
    id SERIAL,
    login VARCHAR(50) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,

    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE tasks (
    id SERIAL,
    id_user INT NOT NULL,
    title VARCHAR(100),
    description VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_task PRIMARY KEY (id),
    CONSTRAINT fk_task FOREIGN KEY (id_user) REFERENCES users(id)
);