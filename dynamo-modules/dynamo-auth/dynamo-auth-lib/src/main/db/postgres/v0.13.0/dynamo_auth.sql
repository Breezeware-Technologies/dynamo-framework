CREATE SCHEMA IF NOT EXISTS dynamo;

DROP SEQUENCE IF EXISTS dynamo.dynamo_auth_audit_login_seq;  
CREATE SEQUENCE dynamo.dynamo_auth_audit_login_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  

DROP SEQUENCE IF EXISTS dynamo.dynamo_auth_account_app_map_seq;  
CREATE SEQUENCE dynamo.dynamo_auth_account_app_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.dynamo_auth_role_app_map_seq;  
CREATE SEQUENCE dynamo.dynamo_auth_role_app_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.dynamo_auth_user_app_map_seq;  
CREATE SEQUENCE dynamo.dynamo_auth_user_app_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
    
DROP TABLE IF EXISTS dynamo.dynamo_auth_audit_login;
CREATE TABLE dynamo.dynamo_auth_audit_login
(
  id bigint NOT NULL DEFAULT nextval('dynamo.dynamo_auth_audit_login_seq'::regclass),
  client_details character varying(255),
  created_date timestamp without time zone,
  ip_address character varying(255),
  login_date timestamp without time zone,
  login_roles character varying(255),
  login_email character varying(255),
  modified_date timestamp without time zone,
  protocol character varying(255),
  CONSTRAINT dynamo_auth_audit_login_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dynamo.dynamo_auth_account_app_map;
CREATE TABLE dynamo.dynamo_auth_account_app_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.dynamo_auth_account_app_map_seq'::regclass),
  account_id character varying(45),
  app_id character varying(45),
  created_date timestamp without time zone,
  modified_date timestamp without time zone,
  CONSTRAINT dynamo_auth_account_app_map_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dynamo.dynamo_auth_role_app_map;
CREATE TABLE dynamo.dynamo_auth_role_app_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.dynamo_auth_role_app_map_seq'::regclass),
  account_id character varying(45),
  role_id character varying(45),
  app_id character varying(45),
  created_date timestamp without time zone,
  modified_date timestamp without time zone,
  CONSTRAINT dynamo_auth_role_app_map_pkey PRIMARY KEY (id)
  );

DROP TABLE IF EXISTS dynamo.dynamo_auth_user_app_map;
CREATE TABLE dynamo.dynamo_auth_user_app_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.dynamo_auth_user_app_map_seq'::regclass),
  user_id character varying(45),
  app_id character varying(45),
  created_date timestamp without time zone,
  modified_date timestamp without time zone,
  CONSTRAINT dynamo_auth_user_app_map_pkey PRIMARY KEY (id)
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