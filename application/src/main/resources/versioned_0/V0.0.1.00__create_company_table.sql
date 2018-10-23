
create table COMPANY (
  id serial primary key,
  company_id varchar(10) not null,
  name varchar(100) not null,
  address varchar(250),
  active varchar(1) not null default 'Y',
  official_language varchar(2) default 'en'
);

create unique index company_id_idx on COMPANY (lower(company_id));
create unique index company_name_idx on COMPANY (lower(name));

alter table COMPANY add constraint valid_active_values check (active in ('Y', 'N'));

comment on column COMPANY.official_language is 'ISO 639-1:2002';
comment on column COMPANY.active is 'Indicates whether company is active';