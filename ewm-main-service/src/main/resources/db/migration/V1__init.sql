CREATE TABLE IF NOT EXISTS users (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(250) NOT NULL,
    email        VARCHAR(254) NOT NULL
    );
ALTER TABLE users ADD CONSTRAINT uq_users_email UNIQUE (email);

-- CATEGORIES
CREATE TABLE IF NOT EXISTS categories (
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
    );
ALTER TABLE categories ADD CONSTRAINT uq_categories_name UNIQUE (name);

-- EVENTS
CREATE TABLE IF NOT EXISTS events (
    id                  BIGSERIAL PRIMARY KEY,
    annotation          VARCHAR(2000) NOT NULL,
    description         VARCHAR(7000) NOT NULL,
    event_date          TIMESTAMP     NOT NULL,
    created_on          TIMESTAMP     NOT NULL,
    published_on        TIMESTAMP     NULL,
    category_id         BIGINT        NOT NULL REFERENCES categories(id),
    initiator_id        BIGINT        NOT NULL REFERENCES users(id),
    paid                BOOLEAN       NOT NULL DEFAULT FALSE,
    participant_limit   INTEGER       NOT NULL DEFAULT 0,
    request_moderation  BOOLEAN       NOT NULL DEFAULT TRUE,
    state               VARCHAR(16)   NOT NULL,
    title               VARCHAR(120)  NOT NULL,
    location_lat        DOUBLE PRECISION NOT NULL,
    location_lon        DOUBLE PRECISION NOT NULL
    );

-- COMPILATIONS
CREATE TABLE IF NOT EXISTS compilations (
    id     BIGSERIAL PRIMARY KEY,
    title  VARCHAR(50) NOT NULL,
    pinned BOOLEAN     NOT NULL DEFAULT FALSE
    );
ALTER TABLE compilations ADD CONSTRAINT uq_compilations_title UNIQUE (title);

-- COMPILATION_EVENTS (M:N)
CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id BIGINT NOT NULL REFERENCES compilations(id) ON DELETE CASCADE,
    event_id       BIGINT NOT NULL REFERENCES events(id)       ON DELETE CASCADE,
    PRIMARY KEY (compilation_id, event_id)
    );

-- REQUESTS
CREATE TABLE IF NOT EXISTS requests (
    id           BIGSERIAL PRIMARY KEY,
    created      TIMESTAMP   NOT NULL,
    event_id     BIGINT      NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    requester_id BIGINT      NOT NULL REFERENCES users(id)  ON DELETE CASCADE,
    status       VARCHAR(16) NOT NULL
    );
ALTER TABLE requests ADD CONSTRAINT uq_requests_event_requester UNIQUE (event_id, requester_id);
