CREATE SCHEMA IF NOT EXISTS dynamo;

DROP SEQUENCE IF EXISTS dynamo.audit_login_seq;  
CREATE SEQUENCE dynamo.audit_login_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.organization_app_map_seq;  
CREATE SEQUENCE dynamo.organization_app_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  

DROP SEQUENCE IF EXISTS dynamo.account_app_map_seq;  
CREATE SEQUENCE dynamo.account_app_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.role_app_map_seq;  
CREATE SEQUENCE dynamo.role_app_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.user_app_map_seq;  
CREATE SEQUENCE dynamo.user_app_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
    
DROP TABLE IF EXISTS dynamo.audit_login;
CREATE TABLE dynamo.audit_login
(
  id bigint NOT NULL DEFAULT nextval('dynamo.audit_login_seq'::regclass),
  client_details character varying(255),
  created_date timestamp with time zone,
  ip_address character varying(255),
  login_date timestamp with time zone,
  login_roles character varying(255),
  login_email character varying(255),
  modified_date timestamp with time zone,
  protocol character varying(255),
  CONSTRAINT audit_login_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dynamo.organization_app_map;
CREATE TABLE dynamo.organization_app_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.organization_app_map_seq'::regclass),
  organization_id character varying(45),
  app_id character varying(45),
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  CONSTRAINT organization_app_map_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dynamo.account_app_map;
CREATE TABLE dynamo.account_app_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.account_app_map_seq'::regclass),
  account_id character varying(45),
  app_id character varying(45),
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  CONSTRAINT account_app_map_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dynamo.role_app_map;
CREATE TABLE dynamo.role_app_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.role_app_map_seq'::regclass),
  organization_id character varying(45),
  role_id character varying(45),
  app_id character varying(45),
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  CONSTRAINT role_app_map_pkey PRIMARY KEY (id)
  );

DROP TABLE IF EXISTS dynamo.user_app_map;
CREATE TABLE dynamo.user_app_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.user_app_map_seq'::regclass),
  user_id character varying(45),
  app_id character varying(45),
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  CONSTRAINT user_app_map_pkey PRIMARY KEY (id)
);

-- *** Dynamo Auth - dynamo.api_key Table *** --

DROP SEQUENCE IF EXISTS dynamo.api_key_seq;  
CREATE SEQUENCE dynamo.api_key_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
    
DROP TABLE IF EXISTS dynamo.api_key;
CREATE TABLE dynamo.api_key
(
  id bigint NOT NULL DEFAULT nextval('dynamo.api_key_seq'::regclass),
  value uuid,
  status character varying(64),
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  CONSTRAINT api_key_pkey PRIMARY KEY (id)
);

-- *** Dynamo Auth - dynamo.organization_api_key_map Table *** --

DROP SEQUENCE IF EXISTS dynamo.org_api_key_map_seq;  
CREATE SEQUENCE dynamo.org_api_key_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
    
DROP TABLE IF EXISTS dynamo.organization_api_key_map;
CREATE TABLE dynamo.organization_api_key_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.org_api_key_map_seq'::regclass),
  organization_id bigint NOT NULL,
  api_key_id bigint NOT NULL,
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  CONSTRAINT organization_apikeymap_pkey PRIMARY KEY (id),
  CONSTRAINT org_apikeymap_fkey1 FOREIGN KEY (organization_id)
  REFERENCES dynamo.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT org_apikeymap_fkey2 FOREIGN KEY (api_key_id)
  REFERENCES dynamo.api_key (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- *** Dynamo Auth - dynamo.auth_user_oauth_token Table *** --

DROP SEQUENCE IF EXISTS dynamo.auth_uotoken_seq;  
CREATE SEQUENCE dynamo.auth_uotoken_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
    
DROP TABLE IF EXISTS dynamo.auth_user_oauth_token;
CREATE TABLE dynamo.auth_user_oauth_token
(
  id bigint NOT NULL DEFAULT nextval('dynamo.auth_uotoken_seq'::regclass),
  token_user bigint NOT NULL,
  application character varying(255),
  access_token character varying(5000),
  refresh_token character varying(5000),
  expires_at timestamp with time zone,
  user_id_at_source character varying(1000),
  modified_date timestamp with time zone,
  CONSTRAINT auth_user_oauth_token_pkey PRIMARY KEY (id),
  CONSTRAINT token_user_fkey1 FOREIGN KEY (token_user)
  REFERENCES dynamo.dynamo_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
      
-- *** oauth_schema.sql *** --
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(256),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token BYTEA,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication  BYTEA,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token  BYTEA,
  authentication  BYTEA
);

create table oauth_code (
  code VARCHAR(256), authentication  BYTEA
);







-- *** oauth_data.sql. Client password is karthik encoded using BCrypt2 *** --
INSERT INTO oauth_client_details (client_id, client_secret,access_token_validity, 
	authorized_grant_types, authorities, scope) 
    VALUES (
		'dynamo-oauth2-client',
        '$2a$10$1sw/Lc92UciLrXwC4uPx/OT8MiG0AUANtCTuBs5YP8xBwRDDib9ta', 
		300,
        'password,authorization_code,refresh_token,implicit,redirect',
        'ROLE_CLIENT,ROLE_TRUSTED_CLIENT',
        'read,write,trust');