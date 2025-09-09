CREATE TABLE IF NOT EXISTS comments (
    id         BIGSERIAL PRIMARY KEY,
    event_id   BIGINT        NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    author_id  BIGINT        NOT NULL REFERENCES users(id)  ON DELETE CASCADE,
    text       VARCHAR(1000) NOT NULL,
    created    TIMESTAMP     NOT NULL DEFAULT NOW()
    );
