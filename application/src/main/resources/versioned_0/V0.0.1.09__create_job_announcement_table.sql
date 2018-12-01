
create table JOB_ANNOUNCEMENT (
  id serial primary key,
  company_id varchar(10) references COMPANY (company_id),
  description varchar(2000) not null,
  programming_languages varchar(512),
  salary_min numeric(9,0) not null,
  salary_max numeric(9,0) not null,
  closing_date timestamp,
  active varchar(1) not null default 'Y'
);

alter table JOB_ANNOUNCEMENT add constraint valid_job_active_values check (active in ('Y', 'N'));


