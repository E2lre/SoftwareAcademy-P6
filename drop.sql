alter table account drop foreign key person_account_fk
alter table bankinfo drop foreign key bankinfo_person_fk
alter table banktransfert drop foreign key banktransfert_bankinfo_fk
alter table banktransfert drop foreign key FK3xg9j3a93rc360p182ehdd242
alter table credit drop foreign key credit_person_fk
alter table credit drop foreign key FKenxhlrkjg4elj67ohaahrden4
alter table creditcardoperation drop foreign key FKq4j36g4p4iuqicll0fv907qvu
alter table payment drop foreign key payment_person_fk
alter table payment drop foreign key FK3l3of0r80ek6uraurauqighnv
alter table person_buddy drop foreign key FK17gjyd6imvbbhkumqb2vi569
alter table person_buddy drop foreign key FKtloo6kbv3pb63yn1b14g1ibia
alter table person_roles drop foreign key FKs955luj19xyjwi3s1omo1pgh4
alter table transaction drop foreign key account_transaction_fk2
drop table if exists account
drop table if exists bankinfo
drop table if exists banktransfert
drop table if exists credit
drop table if exists creditcardoperation
drop table if exists payment
drop table if exists person
drop table if exists person_buddy
drop table if exists person_roles
drop table if exists transaction
