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

DROP SEQUENCE IF EXISTS dynamo.organization_api_key_map_seq;  
CREATE SEQUENCE dynamo.organization_api_key_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
    
DROP TABLE IF EXISTS dynamo.organization_api_key_map;
CREATE TABLE dynamo.organization_api_key_map
(
  id bigint NOT NULL DEFAULT nextval('dynamo.organization_api_key_map_seq'::regclass),
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