ALTER TABLE ONLY users ALTER COLUMN "enabled" SET DEFAULT FALSE;

CREATE TABLE account_activation (
  "email" VARCHAR(255) NOT NULL UNIQUE,
  "hash" VARCHAR(255) NOT NULL UNIQUE
);
