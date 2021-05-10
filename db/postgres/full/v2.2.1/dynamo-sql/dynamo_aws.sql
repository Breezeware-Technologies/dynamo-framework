----------------------------------------------------------------------------- 
-- ~~~~~~~~~~#################### DYNAMO AWS ####################~~~~~~~~~~
-----------------------------------------------------------------------------

-- =============== Dynamo IAM User Credential Schema =============== 

CREATE SCHEMA IF NOT EXISTS dynamo;

DROP SEQUENCE IF EXISTS dynamo.organization_iam_user_credential_seq;
CREATE SEQUENCE dynamo.organization_iam_user_credential_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

DROP TABLE IF EXISTS dynamo.organization_iam_user_credential;
CREATE TABLE dynamo.organization_iam_user_credential
(
	  id bigint NOT NULL DEFAULT nextval('dynamo.organization_iam_user_credential_seq'::regclass),
	  iam_arn character varying(90),
	  access_key character varying(90),
	  secert_key character varying(90),
	  organization_id bigint,
	  created_date timestamp with time zone,
	  modified_date timestamp with time zone,
	  CONSTRAINT organization_iam_user_credential_pkey PRIMARY KEY (id),
	  CONSTRAINT org_iam_user_credential_reference_constraint FOREIGN KEY (organization_id)
      REFERENCES dynamo.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION

);

-- Update the sequence numbers after all the sample data inserts.
select setval('dynamo.organization_iam_user_credential_seq', (select max(id)+1 from dynamo.organization_iam_user_credential), false);