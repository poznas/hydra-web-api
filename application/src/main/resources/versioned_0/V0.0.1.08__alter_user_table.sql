CREATE SEQUENCE company_sequence;

ALTER TABLE COMPANY ALTER COLUMN company_id
 SET DEFAULT '@company'||nextval('company_sequence'::regclass)::varchar;