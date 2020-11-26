DROP SEQUENCE IF EXISTS dynamo.address_seq;
CREATE SEQUENCE dynamo.address_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
DROP SEQUENCE IF EXISTS dynamo.organization_address__map_seq;
CREATE SEQUENCE dynamo.organization_address__map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;  
  
  
  DROP TABLE IF EXISTS dynamo.address;
CREATE TABLE dynamo.address
(
  id bigint NOT NULL DEFAULT nextval('dynamo.address_seq'::regclass),
  address_line1 character varying(100),
  address_line2 character varying(100),
  city  character varying(100),
  state character varying(100),
  pincode character varying(15),
  CONSTRAINT addresss_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dynamo.organization_address_map;
CREATE TABLE dynamo.organization_address_map
(
    id bigint NOT NULL DEFAULT nextval('dynamo.organization_address__map_seq'::regclass),
    organization_id bigint,
    address_id bigint,
    CONSTRAINT organization_address_map_pkey PRIMARY KEY (id),
    CONSTRAINT organization_fkey FOREIGN KEY (organization_id)
      REFERENCES dynamo.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT address_fkey FOREIGN KEY (address_id)
      REFERENCES dynamo.address (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

select setval('dynamo.address_seq', (select max(id)+1 from dynamo.address), false);
select setval('dynamo.organization_address__map_seq', (select max(id)+1 from dynamo.organization_address_map), false);