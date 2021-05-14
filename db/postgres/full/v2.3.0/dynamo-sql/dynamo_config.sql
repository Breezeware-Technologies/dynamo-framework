------------------------------------------------------------------------------ 
-- ~~~~~~~~~~#################### DYNAMO CONFIG ####################~~~~~~~~~~
------------------------------------------------------------------------------

-- =============== Dynamo Config Schema =============== 

CREATE SCHEMA IF NOT EXISTS dynamo; 

DROP SEQUENCE IF EXISTS properties_seq;
CREATE SEQUENCE properties_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
DROP TABLE IF EXISTS properties;
CREATE TABLE properties
(
  id bigint NOT NULL DEFAULT nextval('properties_seq'::regclass),		
  application character varying(180),
  profile character varying(180),
  label character varying(180),
  key character varying(180),
  value character varying(180),
  CONSTRAINT dynamo_admin_properties_pkey PRIMARY KEY (id)
);  

-- =============== Dynamo Config Data =============== 

-- Set Auditing Flag for all modules under the Dynamo Framework 
INSERT INTO properties (id, application, profile, label, key, value) VALUES (1, 'dynamoadminwebapp', 'DBAuthentication','master','enableAuditing','true'), (2, 'dynamoadminwebapp', 'DBAuthentication','master','applicationServerAddress','http://localhost:8080');