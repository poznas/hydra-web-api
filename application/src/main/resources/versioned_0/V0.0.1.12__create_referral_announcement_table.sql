
create table REFERRAL_ANNOUNCEMENT (
  id serial primary key,
  author_id varchar(21) references HYDRA_USER (user_id),
  job_id integer references JOB_ANNOUNCEMENT(id),
  description varchar(2000) not null,
  referral_bonus numeric(9,0) not null,
  referral_bonus_percentage decimal(3,2) not null,
  closing_date timestamp,
  active varchar(1) not null default 'Y'
);

alter table REFERRAL_ANNOUNCEMENT add constraint valid_referral_active_values check (active in ('Y', 'N'));


