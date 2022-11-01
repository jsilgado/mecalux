delete from RACK;
delete from WAREHOUSE;

delete from USER_ROL;
delete from ROL;
delete from USER;


insert into WAREHOUSE (id, client, warehouse_family, size) values ('3fa85f6457174562b3fc2c963f66afa6', 'Amazon', 'ROB', 4);
insert into WAREHOUSE (id, client, warehouse_family, size) values (REPLACE(random_uuid(), '-', ''), 'Mercadona', 'EST', 3);

insert into RACK (id, warehouse_id, type) values (REPLACE(random_uuid(), '-', ''), '3fa85f6457174562b3fc2c963f66afa6', 'A');


insert into USER (id, username, password, enabled, name, surname, email, telephone) values ('240042b4e0054fcf8dc849b1fdadd9ad', 'Admon', '$2a$10$wrlBWF9Hk/Hd4Q89V2x.3urwI1qH6tWz32Gcr2GDafffw02V5uMIm', 'true', 'Lydia', 'Silgado', 'admon@mecalux.com', '699889988');
insert into USER (id, username, password, enabled, name, surname, email, telephone) values ('8380d4c2816d445db9b8c25ded82a053', 'Auditor', '$2a$10$wrlBWF9Hk/Hd4Q89V2x.3urwI1qH6tWz32Gcr2GDafffw02V5uMIm', 'true', 'Judith', 'Silgado', 'auditor@mecalux.com', '699889988');

insert into ROL (id, rol, name) values (REPLACE(random_uuid(), '-', ''), 'ROLE_ADMIN', 'Administrator');
insert into ROL (id, rol, name) values (REPLACE(random_uuid(), '-', ''), 'ROLE_AUDITOR', 'Auditor');

insert into USER_ROL (id, user_id, rol_id) values (REPLACE(random_uuid(), '-', ''), SELECT id FROM USER WHERE username = 'Admon', SELECT id FROM ROL WHERE rol = 'ROLE_ADMIN');
insert into USER_ROL (id, user_id, rol_id) values (REPLACE(random_uuid(), '-', ''), SELECT id FROM USER WHERE username = 'Auditor', SELECT id FROM ROL WHERE rol = 'ROLE_AUDITOR');