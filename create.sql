create table account (person_id bigint not null, balance double precision not null, primary key (person_id)) engine=InnoDB
create table bankinfo (id integer not null auto_increment, description varchar(255), info varchar(255), type integer not null, bankinfo_person_id bigint, primary key (id)) engine=InnoDB
create table banktransfert (transfert_order integer not null, id bigint not null, bankinfo_id integer not null, primary key (id)) engine=InnoDB
create table buddy (creation datetime, person_id bigint not null, friend_person_id bigint not null, primary key (person_id, friend_person_id)) engine=InnoDB
create table credit (id bigint not null, buddy_person_id bigint, buddy_friend_person_id bigint, primary key (id)) engine=InnoDB
create table creditcartoperation (ccv integer not null, credit_card_number integer not null, credit_card_order integer not null, expiration_date datetime, id bigint not null, primary key (id)) engine=InnoDB
create table payment (fee_amount double precision not null, id bigint not null, buddy_person_id bigint, buddy_friend_person_id bigint, primary key (id)) engine=InnoDB
create table person (id bigint not null auto_increment, birthdate datetime, email varchar(500), firstname varchar(100), lastname varchar(100), password varchar(200), primary key (id)) engine=InnoDB
create table transaction (transaction_type varchar(31) not null, id bigint not null auto_increment, amount double precision not null, description varchar(255), transaction_date datetime, account_person_id bigint, primary key (id)) engine=InnoDB
alter table buddy add constraint UK_ay9wfgdf5v6wg0lrlfmwce0ms unique (person_id)
alter table buddy add constraint UK_gfxp8kb0a90sc2ja5r830ev2l unique (friend_person_id)
alter table person add constraint UK_fwmwi44u55bo4rvwsv0cln012 unique (email)
alter table account add constraint person_account_fk foreign key (person_id) references person (id)
alter table bankinfo add constraint bankinfo_person_fk foreign key (bankinfo_person_id) references person (id)
alter table banktransfert add constraint banktransfert_bankinfo_fk foreign key (bankinfo_id) references bankinfo (id)
alter table banktransfert add constraint FK3xg9j3a93rc360p182ehdd242 foreign key (id) references transaction (id)
alter table buddy add constraint person_buddy_fk foreign key (person_id) references person (id)
alter table buddy add constraint person_buddy_fk1 foreign key (friend_person_id) references person (id)
alter table credit add constraint buddy_credit_fk2 foreign key (buddy_person_id, buddy_friend_person_id) references buddy (person_id, friend_person_id)
alter table credit add constraint FKenxhlrkjg4elj67ohaahrden4 foreign key (id) references transaction (id)
alter table creditcartoperation add constraint FKai4dhnyilsyy6k0xn6b65orvc foreign key (id) references transaction (id)
alter table payment add constraint buddy_payment_fk2 foreign key (buddy_person_id, buddy_friend_person_id) references buddy (person_id, friend_person_id)
alter table payment add constraint FK3l3of0r80ek6uraurauqighnv foreign key (id) references transaction (id)
alter table transaction add constraint account_transaction_fk2 foreign key (account_person_id) references account (person_id)
