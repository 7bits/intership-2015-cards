CREATE TABLE discounts (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "key" VARCHAR(8),
  "is_hidden" BOOLEAN DEFAULT true NOT NULL,
  "email" VARCHAR(255) NOT NULL REFERENCES users(email),
  "backer_email" VARCHAR(255) NOT NULL REFERENCES users(email),
  "campaign_id" BIGSERIAL NOT NULL REFERENCES campaigns(id),
  "deleted" BOOLEAN DEFAULT false NOT NULL,
  "hash" VARCHAR(255),
  "created_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);

