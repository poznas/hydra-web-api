
create table HYDRA_USER (
  id serial primary key,
  user_id varchar(21) not null,
  username varchar(30) not null,
  email varchar(100),
  imageUrl varchar(500),
  provider varchar(20),
  unique (user_id)
);

create unique index user_id_idx on HYDRA_USER (lower(user_id));
create unique index user_name_idx on HYDRA_USER (lower(username));