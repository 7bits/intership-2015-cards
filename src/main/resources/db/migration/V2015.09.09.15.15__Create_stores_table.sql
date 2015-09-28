CREATE TABLE stores (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "user_id" BIGSERIAL NOT NULL REFERENCES users(id),
  "name" VARCHAR(255) NOT NULL,
  "image" VARCHAR(255) NOT NULL,
  "created_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);