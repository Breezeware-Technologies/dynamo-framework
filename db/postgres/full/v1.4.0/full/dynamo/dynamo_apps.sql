CREATE SCHEMA IF NOT EXISTS dynamo;

DROP SEQUENCE IF EXISTS dynamo.dynamo_app_seq;
CREATE SEQUENCE dynamo.dynamo_app_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
DROP SEQUENCE IF EXISTS dynamo.app_feature_seq;
CREATE SEQUENCE dynamo.app_feature_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;    
  
DROP TABLE IF EXISTS dynamo.dynamo_app;
CREATE TABLE dynamo.dynamo_app
(
  id bigint NOT NULL DEFAULT nextval('dynamo.dynamo_app_seq'::regclass),	
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  description character varying(180),
  name character varying(45),
  status character varying(45),
  CONSTRAINT dynamo_app_pkey PRIMARY KEY (id),
  CONSTRAINT dynamo_app_name UNIQUE(name)
);  

DROP TABLE IF EXISTS dynamo.app_feature;
CREATE TABLE dynamo.app_feature
(
  id bigint NOT NULL DEFAULT nextval('dynamo.app_feature_seq'::regclass),	
  app_id bigint,
  created_date timestamp with time zone,
  modified_date timestamp with time zone,
  description character varying(180),
  name character varying(45),
  status character varying(45),
  CONSTRAINT app_feature_pkey PRIMARY KEY (id),
  CONSTRAINT fkkv4tku44hr7ebdt08y2gbp8dm FOREIGN KEY (app_id)
      REFERENCES dynamo.dynamo_app (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- *** data.sql *** --
INSERT INTO dynamo.dynamo_app (id, created_date, modified_date, description, name, status) VALUES (1,'2015-12-23 10:31:22','2015-12-23 10:31:22','A series of questions','Questionnaire','active'), (2,'2015-12-23 10:31:22','2015-12-23 10:31:22','A type of healthcare document','Continuty of Care Management','active'),(3,'2015-12-23 10:31:22','2015-12-23 10:31:22','Resources for fast healthcare inter-operability of systems','Fast Healthcare Inter-operability Resources','active');
INSERT INTO dynamo.app_feature(id, app_id, created_date, modified_date, description, name, status) VALUES (1,1,'2015-12-23 10:31:22','2015-12-23 10:31:22','Creating a questionnaire','Create questionnaire','active'),(2,1,'2015-12-23 10:31:22','2015-12-23 10:31:22','Viewing a questionnaire','View questionnaire','active'),(3,3,'2015-12-23 10:31:22','2015-12-23 10:31:22','Importing FHIR document','Import FHIR','active'),(4,3,'2015-12-23 10:31:22','2015-12-23 10:31:22','Viewing FHIR document','View FHIR','active'),(5,2,'2015-12-23 10:31:22','2015-12-23 10:31:22','Viewing a patient record','View CCM','active');


-- Update the sequence numbers after all the sample data inserts.
select setval('dynamo.dynamo_app_seq', (select max(id)+1 from dynamo.dynamo_app), false);
select setval('dynamo.app_feature_seq', (select max(id)+1 from dynamo.app_feature), false);