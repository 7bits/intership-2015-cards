CREATE TABLE campaigns (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "store_id" BIGSERIAL NOT NULL REFERENCES stores(id),
  "name" VARCHAR(255) NOT NULL,
  "description" VARCHAR(255) NOT NULL,
  "percent" INT NOT NULL,
  "backer_percent" INT NOT NULL,
  "enabled" BOOLEAN NOT NULL DEFAULT TRUE,
  "created_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);