CREATE SCHEMA IF NOT EXISTS dynamo;

DROP SEQUENCE IF EXISTS dynamo.inventory_item_seq;
CREATE SEQUENCE dynamo.inventory_item_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

DROP TABLE IF EXISTS dynamo.inventory_item;
CREATE TABLE dynamo.inventory_item
(
	  id bigint NOT NULL DEFAULT nextval('dynamo.inventory_item_seq'::regclass),
	  item_name  character varying(90),
	  item_category character varying(90),
	  manufacturer character varying(90),
	  model_number character varying(90),
	  item_sku character varying(90) UNIQUE,
	  created_date timestamp with time zone,
	  modified_date timestamp with time zone,
	  CONSTRAINT inventory_item_pkey PRIMARY KEY (id)
);

-- Update the sequence numbers after all the sample data inserts.
select setval('dynamo.inventory_item_seq', (select max(id)+1 from dynamo.inventory_item), false);