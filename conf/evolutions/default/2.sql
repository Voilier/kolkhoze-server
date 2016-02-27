# --- !Ups

INSERT INTO "user" VALUES  (1, 'thomas.ersfeld@gmail.com', 'tersfeld', 'password');
INSERT INTO "user" VALUES  (2, 'joseph.palamidesi@gmail.com', 'pallamidessi', 'password');
INSERT INTO "user" VALUES  (3, 'simon.andreux@gmail.com', 'simon', 'password');


INSERT INTO "item" VALUES (1 , 'Rouge et le noir - Stendhal', 1, 1, -1);
INSERT INTO "item" VALUES  (2, 'Apollo rift', 2, 1, 2);
INSERT INTO "item" VALUES  (3, 'Rift', 3, 1, 2);
INSERT INTO "item" VALUES  (4, 'Burnout', 3, 2, 1);

# --- !Downs

delete from "user";
delete from "item";
