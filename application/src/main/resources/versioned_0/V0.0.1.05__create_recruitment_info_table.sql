
create table RECRUITMENT_INFORMATION (
  id serial primary key,
  user_id varchar(21) references HYDRA_USER (user_id),
  company_id varchar(10) references COMPANY (company_id),
  recruitmentType varchar(20) not null,
  programmingLanguage varchar(20),
  content varchar(2000) not null,
  active varchar(1) not null default 'Y'
);

create table INFORMATION_VOTE (
  user_id varchar(21) references HYDRA_USER (user_id),
  information_id integer references RECRUITMENT_INFORMATION(id),
  vote varchar(4) not null,
  PRIMARY KEY (user_id, information_id)
);

alter table INFORMATION_VOTE add constraint valid_vote_values check (vote in ('up', 'down'));
alter table RECRUITMENT_INFORMATION add constraint valid_info_active_values check (active in ('Y', 'N'));


