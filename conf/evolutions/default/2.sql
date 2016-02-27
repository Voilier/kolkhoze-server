# --- !Ups

INSERT INTO "user" VALUES  (1, 'thomas.ersfeld@gmail.com', 'tersfeld', 'password');
INSERT INTO "user" VALUES  (2, 'joseph.palamidesi@gmail.com', 'pallamidessi', 'password');
INSERT INTO "user" VALUES  (3, 'simon.andreux@gmail.com', 'simon', 'password');


INSERT INTO "item" VALUES (1 , 'Rouge et le noir - Stendhal', 1);
INSERT INTO "item" VALUES  (2, 'Apollo rift', 2);
INSERT INTO "item" VALUES  (3, 'Rift', 3);
INSERT INTO "item" VALUES  (4, 'Burnout', 3);

INSERT INTO "itemLent" VALUES(1, 2, 1, 2);
INSERT INTO "itemLent" VALUES(2, 3, 1, 2);
INSERT INTO "itemLent" VALUES(3, 4, 2, 1);

# --- !Downs

delete from "user";
delete from "itemLent";
delete from "item";
