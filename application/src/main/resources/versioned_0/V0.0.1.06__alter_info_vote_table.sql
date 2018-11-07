
alter table INFORMATION_VOTE drop constraint valid_vote_values;
alter table INFORMATION_VOTE add constraint valid_vote_values check (vote in ('UP', 'DOWN'));
