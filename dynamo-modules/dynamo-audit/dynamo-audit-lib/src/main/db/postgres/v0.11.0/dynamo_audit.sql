CREATE SCHEMA IF NOT EXISTS dynamo;

DROP SEQUENCE IF EXISTS dynamo.dynamo_audit_audit_item_seq;
CREATE SEQUENCE dynamo.dynamo_audit_audit_item_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

DROP TABLE IF EXISTS dynamo.dynamo_audit_audit_item;
CREATE TABLE dynamo.dynamo_audit_audit_item
(
  id bigint NOT NULL DEFAULT nextval('dynamo.dynamo_audit_audit_item_seq'::regclass),
  audit_date timestamp without time zone,
  audit_event character varying(255),
  audit_item_id character varying(255),
  audit_item_type character varying(255),
  audit_username character varying(255),
  audit_item_input_data character varying,
  audit_item_output_data character varying,
  client_details character varying(255),
  ip_address character varying(255),
  protocol character varying(255),
  created_date timestamp without time zone,
  modified_date timestamp without time zone,
  CONSTRAINT dynamo_audit_audit_item_pkey PRIMARY KEY (id)
);