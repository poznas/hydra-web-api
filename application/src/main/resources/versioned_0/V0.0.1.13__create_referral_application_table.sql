
create table REFERRAL_APPLICATION (
  id serial primary key,
  referral_id integer references REFERRAL_ANNOUNCEMENT(id),
  user_id varchar(21) references HYDRA_USER (user_id),
  github_url varchar(500) not null,
  linkedin_url varchar(500),
  cv_url varchar(500) not null
);



