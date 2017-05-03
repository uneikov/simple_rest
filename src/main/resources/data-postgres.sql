DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM horses;
DELETE FROM stakes;
DELETE FROM accounts;
DELETE FROM races;


INSERT INTO users (name, email, password, enabled)
VALUES ('User1', 'user1@yandex.ru', '$2a$10$Gk4kXU24ryWq66oKA0f8AeaguhyMNx7.pR2x/euooqK0NG.IyK1jC', TRUE);
-- password::password1
INSERT INTO users (name, email, password, enabled)
VALUES ('User2', 'user2@yandex.ru', '$2a$10$3Om1jqFdbcLtir5XsQbS2uiBG/7kv2twth/3BGg1jPTjbqgaqplw.', TRUE);
-- password::password2
INSERT INTO users (name, email, password, enabled)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju', TRUE);
-- password:admin
INSERT INTO users (name, email, password, enabled)
VALUES ('Station', 'station@gamblingstation.com', '$2a$10$4BB/oW0v.c54SFiRB47gue22MjbPnG88iSRSo//uESs1T1NMrnpUK', TRUE);
-- password:stationpass

-- Accounts
INSERT INTO accounts (card_number, balance, user_id) VALUES ('2273114861198385', 10.0, 1);
INSERT INTO accounts (card_number, balance, user_id) VALUES ('2500842215393147', 15.0, 2);
INSERT INTO accounts (card_number, balance, user_id) VALUES ('5570902209270285', 0.0, 3);
INSERT INTO accounts (card_number, balance, user_id) VALUES ('2229681069525770', 100.0, 4);

-- Roles
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 1);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 2);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_ADMIN', 3);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_STATION', 4);

-- Horses
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Black Ghost', 'Черный призрак', 5, 0, TRUE ); -- 1
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('White Ghost', 'Белый призрак', 5, 0, TRUE );  -- 2
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Enisei', 'Енисей', 3, 0, FALSE ) ;            -- 3
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Thunderbird', 'Гром', 5, 0, TRUE );           -- 4
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Ruby Rose', 'Рубироуз', 4, 0, TRUE );         -- 5
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Predator', 'Хищник', 5, 0, TRUE );            -- 6
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Alien', 'Чужой', 6, 0, TRUE );               -- 7
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Gulfstream', 'Гольфстрим', 3, 0, FALSE );     -- 8
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Rabindranate', 'Рабиндранат', 5, 0, FALSE );  -- 9
INSERT INTO horses (name, ru_name, age, wins, ready) VALUES ('Angelfire', 'Энджелфае', 5, 0, FALSE );       -- 10

-- Races;
INSERT INTO races (start, finish, horses, winning)
VALUES ('2016-05-30 10:00:00','2016-05-30 10:45:00','Alien:Чужой,Black Ghost:Черный призрак,White Ghost:Белый призрак,Enisei:Енисей,Thunderbird:Гром,Ruby Rose:Рубироуз','Ghost:Черный призрак');
INSERT INTO races (start, finish, horses, winning)
VALUES ('2016-06-12 13:00:00','2016-06-12 13:45:00','Alien:Чужой,Black Ghost:Черный призрак,White Ghost:Белый призрак,Enisei:Енисей,Thunderbird:Гром,Ruby Rose:Рубироуз','Thunderbird:Гром');
INSERT INTO races (start, finish, horses, winning)
VALUES ('2016-06-13 19:00:00','2016-06-13 19:45:00','Predator:Хищник,Gulfstream:Гольфстрим,Rabindranate:Рабиндранат,Ruby Rose:Рубироуз,White Ghost:Белый призрак,Angelfire:Энджелфае','Predator:Хищник');
INSERT INTO races (start, finish, horses, winning)
VALUES ('2016-08-05 10:00:00','2016-08-05 10:45:00','Predator:Хищник,Gulfstream:Гольфстрим,Rabindranate:Рабиндранат,Ruby Rose:Рубироуз,White Ghost:Белый призрак,Angelfire:Энджелфае','Ruby Rose:Рубироуз');

-- Stakes
INSERT INTO stakes (user_id, horse_id, race_id, stake_value, date_time, wins, amount, editable)
VALUES (1, 4, 1, 100.25, '2016-05-30 10:00:00', TRUE, 10.0, false); -- 1 +
INSERT INTO stakes (user_id, horse_id, race_id, stake_value, date_time, wins, amount, editable)
VALUES (2, 5, 2, 100.25, '2016-06-12 13:30:00', FALSE, 0.0, false); -- 2 +
INSERT INTO stakes (user_id, horse_id, race_id, stake_value, date_time, wins, amount, editable)
VALUES (1, 6, 3, 100.25, '2016-06-13 19:45:00', FALSE, 0.0, false); -- 3 +
INSERT INTO stakes (user_id, horse_id, race_id, stake_value, date_time, wins, amount, editable)
VALUES (2, 4, 4, 100.25, '2016-08-05 10:09:00', FALSE, 0.0, true); -- 4
INSERT INTO stakes (user_id, horse_id, race_id, stake_value, date_time, wins, amount, editable)
VALUES (1, 4, 4, 100.25, '2016-08-05 10:10:00', FALSE, 0.0, true); -- 5
