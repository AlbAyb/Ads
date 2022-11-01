CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username TEXT    NOT NULL UNIQUE,
    password TEXT    NOT NULL,
    role     TEXT    NOT NULL,
    removed  BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE categories
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE ads
(
    id            BIGSERIAL PRIMARY KEY,
    title         TEXT                              NOT NULL,
    price         INT                               NOT NULL CHECK ( price > 0 ),
    description   TEXT                              NOT NULL,
    removed       BOOLEAN                           NOT NULL DEFAULT FALSE,
    created       timestamptz                       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id       BIGINT REFERENCES users (id)      NOT NULL,
    categories_id BIGINT REFERENCES categories (id) NOT NULL
);

CREATE TABLE message
(
    id       BIGSERIAL PRIMARY KEY,
    text     TEXT        NOT NULL,
    created  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_add BIGINT REFERENCES users (id),
    user_id  BIGINT REFERENCES users (id),
    outgoing BOOLEAN
    --ads_id   BIGINT REFERENCES ads (id)
);

