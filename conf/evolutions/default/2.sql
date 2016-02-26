# --- !Ups

INSERT INTO "people" VALUES  (1, 'thomas.ersfeld@gmail.com', 'tersfeld', 'password');
INSERT INTO "people" VALUES  (2, 'joseph.palamidesi', 'palamidesi', 'password');


INSERT INTO "item" VALUES (1 , 'Rouge et le noir - Stendhal', 1);
INSERT INTO "item" VALUES  (2, 'Apollo rift', 2);
INSERT INTO "item" VALUES  (3, 'Rift', 3);
INSERT INTO "item" VALUES  (4, 'Burnout', 3);

INSERT INTO "itemLent" VALUES(1, 2, 1, 2);
INSERT INTO "itemLent" VALUES(2, 3, 1, 2);
INSERT INTO "itemLent" VALUES(3, 4, 2, 1);

# --- !Downs

delete from "itemLent";
delete from "item";
delete from "people";

