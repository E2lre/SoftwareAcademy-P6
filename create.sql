create table account (person_id bigint not null, balance Decimal(6,2), primary key (person_id)) engine=InnoDB
create table bankinfo (id integer not null auto_increment, description varchar(255), info varchar(255) not null, type integer not null, bankinfo_person_id bigint, primary key (id)) engine=InnoDB
create table banktransfert (transfert_order integer not null, id bigint not null, bankinfo_id integer, primary key (id)) engine=InnoDB
create table credit (id bigint not null, credit_person_id bigint, primary key (id)) engine=InnoDB
create table creditcardoperation (ccv integer not null, credit_card_number double precision not null, credit_card_order integer not null, expiration_date datetime not null, id bigint not null, primary key (id)) engine=InnoDB
create table payment (fee_amount Decimal(6,2) not null, id bigint not null, payment_person_id bigint, primary key (id)) engine=InnoDB
create table person (id bigint not null auto_increment, birthdate datetime, email varchar(500) not null, firstname varchar(100) not null, lastname varchar(100) not null, password varchar(200) not null, primary key (id)) engine=InnoDB
create table person_buddy (person_id bigint not null, buddy_id bigint not null) engine=InnoDB
create table person_roles (person_id bigint not null, roles integer) engine=InnoDB
create table transaction (transaction_type varchar(31) not null, id bigint not null auto_increment, amount Decimal(6,2) not null, description varchar(255), transaction_date datetime not null, account_person_id bigint, primary key (id)) engine=InnoDB
alter table person add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email)
alter table account add constraint person_account_fk foreign key (person_id) references person (id)
alter table bankinfo add constraint bankinfo_person_fk foreign key (bankinfo_person_id) references person (id)
alter table banktransfert add constraint banktransfert_bankinfo_fk foreign key (bankinfo_id) references bankinfo (id)
alter table banktransfert add constraint FK3xg9j3a93rc360p182ehdd242 foreign key (id) references transaction (id)
alter table credit add constraint credit_person_fk foreign key (credit_person_id) references person (id)
alter table credit add constraint FKenxhlrkjg4elj67ohaahrden4 foreign key (id) references transaction (id)
alter table creditcardoperation add constraint FKq4j36g4p4iuqicll0fv907qvu foreign key (id) references transaction (id)
alter table payment add constraint payment_person_fk foreign key (payment_person_id) references person (id)
alter table payment add constraint FK3l3of0r80ek6uraurauqighnv foreign key (id) references transaction (id)
alter table person_buddy add constraint FK17gjyd6imvbbhkumqb2vi569 foreign key (buddy_id) references person (id)
alter table person_buddy add constraint FKtloo6kbv3pb63yn1b14g1ibia foreign key (person_id) references person (id)
alter table person_roles add constraint FKs955luj19xyjwi3s1omo1pgh4 foreign key (person_id) references person (id)
alter table transaction add constraint account_transaction_fk2 foreign key (account_person_id) references account (person_id)
