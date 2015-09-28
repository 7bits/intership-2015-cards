CREATE TABLE discounts (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "key" VARCHAR(8),
  "is_hidden" BOOLEAN DEFAULT true NOT NULL,
  "user_id" BIGSERIAL NOT NULL REFERENCES users(id),
  "backer_user_id" BIGSERIAL NOT NULL REFERENCES users(id),
  "campaign_id" BIGSERIAL NOT NULL REFERENCES campaigns(id),
  "deleted" BOOLEAN DEFAULT false NOT NULL,
  "hash" VARCHAR(255),
  "created_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);

