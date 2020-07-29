CREATE SCHEMA IF NOT EXISTS dynamo; 

DROP SEQUENCE IF EXISTS dynamo.organization_seq;
CREATE SEQUENCE dynamo.organization_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
DROP SEQUENCE IF EXISTS dynamo.group_seq;
CREATE SEQUENCE dynamo.group_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.role_seq;
CREATE SEQUENCE dynamo.role_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
DROP SEQUENCE IF EXISTS dynamo.user_group_map_seq;
CREATE SEQUENCE dynamo.user_group_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.user_role_map_seq;
CREATE SEQUENCE dynamo.user_role_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
DROP SEQUENCE IF EXISTS dynamo.user_seq;
CREATE SEQUENCE dynamo.user_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP TABLE IF EXISTS dynamo.organization;
CREATE TABLE dynamo.organization
(
  id bigint NOT NULL DEFAULT nextval('dynamo.organization_seq'::regclass),
  created_date timestamp with time zone,
  description character varying(180),
  modified_date timestamp with time zone,
  name character varying(45),
  CONSTRAINT organization_pkey PRIMARY KEY (id)
);  

DROP TABLE IF EXISTS dynamo.dynamo_group;
CREATE TABLE dynamo.dynamo_group
(
  id bigint NOT NULL DEFAULT nextval('dynamo.group_seq'::regclass),
  organization_id bigint,
  created_date timestamp with time zone,
  description character varying(180) NOT NULL,
  modified_date timestamp with time zone,
  name character varying(45) NOT NULL,
  CONSTRAINT group_pkey PRIMARY KEY (id),
  CONSTRAINT org_group_reference_constraint FOREIGN KEY (organization_id)
      REFERENCES dynamo.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT group_name_org_id UNIQUE(name, organization_id)
);  

DROP TABLE IF EXISTS dynamo.role;
CREATE TABLE dynamo.role
(
  id bigint NOT NULL DEFAULT nextval('dynamo.role_seq'::regclass),
  organization_id bigint,
  created_date timestamp with time zone,
  description character varying(180) NOT NULL,
  modified_date timestamp with time zone,
  name character varying(45) NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY (id),
  CONSTRAINT org_role_reference_constraint FOREIGN KEY (organization_id)
      REFERENCES dynamo.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT role_name_org_id UNIQUE(name, organization_id)
);

DROP TABLE IF EXISTS dynamo.dynamo_user;
CREATE TABLE dynamo.dynamo_user
(
  id bigint NOT NULL DEFAULT nextval('dynamo.user_seq'::regclass),
  user_unique_id character varying(90) NOT NULL,
  organization_id bigint,
  created_date timestamp with time zone,
  email character varying(90) NOT NULL,
  phone_number character varying(30),
  first_name character varying(45) NOT NULL,
  last_name character varying(45) NOT NULL,
  middle_initial character varying(1),
  modified_date timestamp with time zone,
  password character varying(90),
  status character varying(45),
  user_time_zone_id character varying(90),
  CONSTRAINT user_pkey PRIMARY KEY (id),
  CONSTRAINT fkkv4tku44hr7ebdt08y2gbp8dl FOREIGN KEY (organization_id)
      REFERENCES dynamo.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT dyanmo_org_user_email UNIQUE(email),
  CONSTRAINT dyanmo_org_user_user_unique_id UNIQUE(user_unique_id)
);

DROP TABLE IF EXISTS dynamo.user_group_map;
CREATE TABLE dynamo.user_group_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.user_group_map_seq'::regclass),
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  user_id bigint,
  group_id bigint,
  CONSTRAINT user_group_map_pkey PRIMARY KEY (id),
  CONSTRAINT fkde25aplm67g4i7hjos97bg8v6 FOREIGN KEY (group_id)
      REFERENCES dynamo.dynamo_group (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkhyj13bofjpx6hfisw6li837fo FOREIGN KEY (user_id)
      REFERENCES dynamo.dynamo_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP TABLE IF EXISTS dynamo.user_role_map;
CREATE TABLE dynamo.user_role_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.user_role_map_seq'::regclass),
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  user_id bigint,
  role_id bigint,
  CONSTRAINT user_role_map_pkey PRIMARY KEY (id),
  CONSTRAINT fk7dh2e02st5ciooovecqprgmnt FOREIGN KEY (user_id)
      REFERENCES dynamo.dynamo_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkfua57mv09bipvksnui5qhwr0a FOREIGN KEY (role_id)
      REFERENCES dynamo.role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP SEQUENCE IF EXISTS dynamo.user_registration_token_seq;
CREATE SEQUENCE dynamo.user_registration_token_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

DROP TABLE IF EXISTS dynamo.user_registration_token;
CREATE TABLE dynamo.user_registration_token
(
  id bigint NOT NULL DEFAULT nextval('dynamo.user_registration_token_seq'::regclass),
  token_created_date timestamp with time zone,
  token_expiry_date timestamp with time zone,
  token character varying(45),
  user_id bigint,
  CONSTRAINT user_registration_token_pkey PRIMARY KEY (id),
  CONSTRAINT user_registration_token_fk FOREIGN KEY (user_id)
      REFERENCES dynamo.dynamo_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
); 
 
DROP SEQUENCE IF EXISTS dynamo.password_reset_token_seq;
CREATE SEQUENCE dynamo.password_reset_token_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

DROP TABLE IF EXISTS dynamo.password_reset_token;
CREATE TABLE dynamo.password_reset_token
(
  id bigint NOT NULL DEFAULT nextval('dynamo.password_reset_token_seq'::regclass),
  token_created_date timestamp with time zone,
  token_expiry_date timestamp with time zone,
  token character varying(45),
  user_id bigint,
  CONSTRAINT password_reset_token_pkey PRIMARY KEY (id),
  CONSTRAINT password_reset_token_fk FOREIGN KEY (user_id)
      REFERENCES dynamo.dynamo_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
); 

-- *** dynamo_orgth_data.sql *** --
INSERT INTO dynamo.organization (id, name, description, created_date, modified_date) VALUES (1, 'Default Organization','Default Organization',now(), now()), (2, 'Breeze Tech','Breeze Technologies India',now(),now());
INSERT INTO dynamo.dynamo_group (id, organization_id, name, description, created_date, modified_date) VALUES (1,2,'Business','Business',now(),now()),(2,2,'Technical','Technical',now(),now());
INSERT INTO dynamo.role (id, organization_id, name, description, created_date, modified_date) VALUES (1,2,'ROLE_SYSTEM_ADMIN','System Administrator',now(),now()),(2,2,'ORGANIZATION_ADMIN','Organization Administrator', now(), now()),(3,2,'ORGANIZATION_USER','Organization User', now(), now());
INSERT INTO dynamo.dynamo_user (id, user_unique_id, organization_id, password, status, user_time_zone_id, email, phone_number, first_name, last_name, middle_initial, created_date, modified_date) VALUES (1,'abcdeabcdeabcde12345', 2,'$2a$10$1sw/Lc92UciLrXwC4uPx/OT8MiG0AUANtCTuBs5YP8xBwRDDib9ta', 'ACTIVE', 'Asia/Kolkata', 'karthik@breezeware.net', '+919677122547', 'Karthik','Muthukumaraswamy',NULL, now(), now()),(2,'abcdeabcdeabcde12346', 2,'sivaram','ACTIVE','Asia/Kolkata','sivaram@breezeware.net', '+919789775755', 'Sivaram','Muthuswami',NULL,now(),now()),(3,'abcdeabcdeabcde12347', 2,'rsiva','ACTIVE','Asia/Kolkata','rsiva@breezeware.net', '+916369013649', 'Siva','R',NULL,now(),now()),(4,'abcdeabcdeabcde12348', 2,'gowtham','ACTIVE','Asia/Kolkata','gowtham@breezeware.net', '+917871288876', 'Gowtham','Jayaraman',NULL,now(),now()),(5,'abcdeabcdeabcde12349', 2,'siddharth','ACTIVE','Asia/Kolkata','siddharth@breezeware.net', '+917871288876', 'Siddharth','Elanchezian',NULL,now(),now());
INSERT INTO dynamo.user_group_map (id, user_id, group_id, created_date, modified_date) VALUES (1,1,2,now(),now()),(2,2,1,now(),now()),(3,3,2,now(),now()),(4,4,2,now(),now()), (5,5,1,now(),now());
INSERT INTO dynamo.user_role_map (id, user_id, role_id, created_date, modified_date) VALUES (1,1,1,now(),now()),(2,2,2,now(),now()),(3,3,3,now(),now()),(4,4,3,now(),now()),(5,5,3,now(),now());

-- Update the sequence numbers after all the sample data inserts.
select setval('dynamo.organization_seq', (select max(id)+1 from dynamo.organization), false);
select setval('dynamo.group_seq', (select max(id)+1 from dynamo.dynamo_group), false);
select setval('dynamo.user_seq', (select max(id)+1 from dynamo.dynamo_user), false);
select setval('dynamo.role_seq', (select max(id)+1 from dynamo.role), false);

select setval('dynamo.user_group_map_seq', (select max(id)+1 from dynamo.user_group_map), false);
select setval('dynamo.user_role_map_seq', (select max(id)+1 from dynamo.user_role_map), false);

select setval('dynamo.password_reset_token_seq', (select max(id)+1 from dynamo.password_reset_token), false);
select setval('dynamo.user_registration_token_seq', (select max(id)+1 from dynamo.user_registration_token), false);
