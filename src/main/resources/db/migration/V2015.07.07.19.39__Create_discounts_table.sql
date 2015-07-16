CREATE TABLE discounts (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "key" VARCHAR(8) NOT NULL,
  "uin" VARCHAR(8) NOT NULL,
  "is_hidden" BOOLEAN NOT NULL,
  "user_id" VARCHAR(8) NOT NULL,
  "store_name" VARCHAR(255) NOT NULL,
  "description" VARCHAR(255) NOT NULL
);

CREATE TABLE users (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "email" VARCHAR(255) NOT NULL,
  "user_id" VARCHAR(8) NOT NULL,
  "password_hash" VARCHAR(255) NOT NULL,
  "is_store" BOOLEAN NOT NULL
);
