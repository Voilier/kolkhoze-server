# --- !Ups

create table "user" (
  "id" bigint generated by default as identity(start with 1) not null primary key,
  "email" varchar not null,
  "login" varchar not null,
  "password" varchar not null
);

create table "item" (
  "id" bigint generated by default as identity(start with 1) not null primary key,
  "name" varchar not null,
  "itemType" int not null,
  "ownerId" bigint not null,
  "lentId" bigint
);

# --- !Downs

drop table "user" if exists;
drop table "item" if exists;
