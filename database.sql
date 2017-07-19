-- Create sequence
create sequence INVESTING_PROFILE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 10;

-- Create table
create table INVESTING_PROFILE
(
  id                  NUMBER(19) not null,
  profile_type        NVARCHAR2(25),
  polish_fund_assign  NUMBER(5,2),
  foreign_fund_assign NUMBER(5,2),
  money_fund_assign   NUMBER(5,2)
);
-- Create/Recreate primary, unique and foreign key constraints
alter table INVESTING_PROFILE
  add constraint PK_INVESTING_PROFILE primary key (ID);
-- Create/Recreate indexes
create index IN_INVESTING_PROFILE_TYPE on INVESTING_PROFILE (PROFILE_TYPE);

insert into investing_profile values (INVESTING_PROFILE_ID_SEQ.Nextval , 'bezpieczny', 20, 75, 5);
insert into iszkody_atn.investing_profile values (INVESTING_PROFILE_ID_SEQ.Nextval, 'zrownowazony', 30, 60, 10);
insert into iszkody_atn.investing_profile values (INVESTING_PROFILE_ID_SEQ.Nextval, 'agresywny', 40, 20, 40);