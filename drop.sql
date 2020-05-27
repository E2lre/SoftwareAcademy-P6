alter table account drop foreign key person_account_fk
alter table bankinfo drop foreign key bankinfo_person_fk
alter table banktransfert drop foreign key banktransfert_bankinfo_fk
alter table banktransfert drop foreign key FK3xg9j3a93rc360p182ehdd242
alter table buddy drop foreign key person_buddy_fk
alter table buddy drop foreign key person_buddy_fk1
alter table credit drop foreign key buddy_credit_fk2
alter table credit drop foreign key FKenxhlrkjg4elj67ohaahrden4
alter table creditcartoperation drop foreign key FKai4dhnyilsyy6k0xn6b65orvc
alter table payment drop foreign key buddy_payment_fk2
alter table payment drop foreign key FK3l3of0r80ek6uraurauqighnv
alter table transaction drop foreign key account_transaction_fk2
drop table if exists account
drop table if exists bankinfo
drop table if exists banktransfert
drop table if exists buddy
drop table if exists credit
drop table if exists creditcartoperation
drop table if exists payment
drop table if exists person
drop table if exists transaction
