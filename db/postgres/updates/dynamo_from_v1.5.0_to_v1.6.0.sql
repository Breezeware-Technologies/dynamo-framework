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
  access_token character varying(10000),
  refresh_token character varying(10000),
  expires_at timestamp with time zone,
  user_id_at_source character varying(1000),
  modified_date timestamp with time zone,
  CONSTRAINT auth_user_oauth_token_pkey PRIMARY KEY (id),
  CONSTRAINT token_user_fkey1 FOREIGN KEY (token_user)
  REFERENCES dynamo.dynamo_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);