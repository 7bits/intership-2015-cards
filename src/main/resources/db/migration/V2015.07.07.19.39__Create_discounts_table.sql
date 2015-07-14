CREATE TABLE discounts (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "key" VARCHAR(8) NOT NULL,
  "uin" VARCHAR(8) NOT NULL,
  "is_hidden" BOOLEAN NOT NULL,
  "user_id" VARCHAR(8) NOT NULL
);

CREATE TABLE users (
  "id" BIGSERIAL NOT NULL PRIMARY KEY,
  "email" VARCHAR(255) NOT NULL,
  "user_id" VARCHAR(8) NOT NULL,
  "password" VARCHAR(255) NOT NULL,
  "is_store" BOOLEAN NOT NULL
);

INSERT INTO users("id","email","user_id","password","is_store")
values
  (1,'user@user.user','user','user',false),
  (2,'store@store.store','store','store',true);
