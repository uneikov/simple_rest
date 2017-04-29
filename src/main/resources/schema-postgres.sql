DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS stakes;
DROP TABLE IF EXISTS races;
DROP TABLE IF EXISTS horses;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;

CREATE TABLE horses (
  id SERIAL,
  age INTEGER NOT NULL ,
  name VARCHAR(255) NOT NULL ,
  ready BOOLEAN NOT NULL ,
  ru_name VARCHAR(255) NOT NULL ,
  wins INTEGER NOT NULL , PRIMARY KEY (id),
  CONSTRAINT horses_unique_name_idx UNIQUE (name)
);
--create unique index horses_unique_name_idx on horses (name);

CREATE TABLE users (
  id SERIAL,
  email varchar(255) not null,
  enabled boolean not null,
  name varchar(255) not null,
  password varchar(255) not null,
  registered timestamp default now(), primary key (id),
  CONSTRAINT users_unique_email_idx UNIQUE (email)
);
--create unique index users_unique_email_idx on users (email);

CREATE TABLE user_roles (
  user_id bigint not null,
  role varchar(255),
  constraint user_roles_idx unique (user_id, role),
  foreign key (user_id) references users (id) on delete cascade
);

CREATE TABLE accounts (
  id SERIAL,
  balance DOUBLE PRECISION not null,
  card_number varchar(255),
  user_id bigint not null, primary key (id),
  CONSTRAINT accounts_unique_user_id_idx UNIQUE (user_id),
  FOREIGN KEY (user_id) references users (id) on delete cascade
);
--CREATE UNIQUE INDEX accounts_unique_user_id_idx ON accounts (user_id);

CREATE TABLE races (
  id SERIAL,
  finish timestamp,
  horses varchar(255) not null,
  start timestamp not null,
  winning varchar(255) not null, primary key (id),
  CONSTRAINT races_unique_id_idx UNIQUE (start)
);
--CREATE UNIQUE INDEX races_unique_id_idx ON races (start);

CREATE TABLE stakes (
  id SERIAL,
  amount DOUBLE PRECISION not null,
  date_time timestamp default now() not null,
  editable boolean not null,
  stake_value DOUBLE PRECISION null,
  wins boolean not null,
  horse_id bigint not null,
  race_id bigint not null,
  user_id bigint not null, primary key (id),
  CONSTRAINT stakes_unique_user_id_idx UNIQUE (user_id, date_time),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (horse_id) REFERENCES horses (id) ON DELETE CASCADE,
  FOREIGN KEY (race_id) REFERENCES races (id) ON DELETE CASCADE
);
--CREATE UNIQUE INDEX stakes_unique_user_id_idx ON stakes (user_id, date_time);
