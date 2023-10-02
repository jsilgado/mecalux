CREATE TABLE warehouse (
	id varchar(36) NOT NULL,
	client varchar(100) NOT NULL,
	capacity int4 NOT NULL,
	warehouse_family varchar(3) NOT NULL,
	CONSTRAINT warehouse_pkey PRIMARY KEY (id)
);

CREATE TABLE rack (
	id varchar(36) NOT NULL,
	"type" varchar(1) NOT NULL,
	warehouse_id varchar(36) NULL,
	CONSTRAINT rack_pkey PRIMARY KEY (id)
);


-- rack foreign keys
ALTER TABLE rack ADD CONSTRAINT fk_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouse(id);


CREATE TABLE rol (
	id varchar(36) NOT NULL,
	"name" varchar(255) NULL,
	rol varchar(255) NULL,
	CONSTRAINT rol_pkey PRIMARY KEY (id)
);

CREATE TABLE users (
	id varchar(36) NOT NULL,
	email varchar(100) NULL,
	enabled bool NOT NULL,
	"name" varchar(100) NOT NULL,
	"password" varchar(100) NOT NULL,
	surname varchar(100) NOT NULL,
	telephone varchar(20) NULL,
	username varchar(100) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE user_rol (
	id varchar(36) NOT NULL,
	rol_id varchar(36) NULL,
	user_id varchar(36) NULL,
	CONSTRAINT user_rol_pkey PRIMARY KEY (id)
);


insert into warehouse (id, client, warehouse_family, capacity) values (gen_random_uuid(), 'Amazon', 'ROB', 4);
insert into warehouse (id, client, warehouse_family, capacity) values (gen_random_uuid(), 'Mercadona', 'EST', 3);

insert into rack (id, warehouse_id, type) values(gen_random_uuid(), (SELECT id FROM warehouse WHERE client = 'Amazon'), 'A');


insert into users (id, username, password, enabled, name, surname, email, telephone) values (gen_random_uuid(), 'Admon', '$2a$10$wrlBWF9Hk/Hd4Q89V2x.3urwI1qH6tWz32Gcr2GDafffw02V5uMIm', 'true', 'Lydia', 'Silgado', 'admon@mecalux.com', '699889988');
insert into users (id, username, password, enabled, name, surname, email, telephone) values (gen_random_uuid(), 'Auditor', '$2a$10$wrlBWF9Hk/Hd4Q89V2x.3urwI1qH6tWz32Gcr2GDafffw02V5uMIm', 'true', 'Judith', 'Silgado', 'auditor@mecalux.com', '699889988');

insert into rol (id, rol, name) values (gen_random_uuid(), 'ROLE_ADMIN', 'Administrator');
insert into rol (id, rol, name) values (gen_random_uuid(), 'ROLE_AUDITOR', 'Auditor');

insert into user_rol (id, user_id, rol_id) values (gen_random_uuid(), (SELECT id FROM users WHERE username = 'Admon'), (SELECT id FROM rol WHERE rol = 'ROLE_ADMIN'));
insert into user_rol (id, user_id, rol_id) values (gen_random_uuid(), (SELECT id FROM users WHERE username = 'Auditor'), (SELECT id FROM rol WHERE rol = 'ROLE_AUDITOR'));



-- user_rol foreign keys
ALTER TABLE user_rol ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_rol ADD CONSTRAINT fk_rol_id FOREIGN KEY (rol_id) REFERENCES rol(id);