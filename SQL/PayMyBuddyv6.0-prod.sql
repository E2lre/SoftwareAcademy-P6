USE paymybuddy_prod;

CREATE TABLE Person (
                id INT AUTO_INCREMENT NOT NULL,
                password VARCHAR(200) NOT NULL,
                firstname VARCHAR(100) NOT NULL,
                lastname VARCHAR(100) NOT NULL,
                birthdate DATE NOT NULL,
                email VARCHAR(500) NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX person_emal_status_one_idx
 ON Person
 ( email );

CREATE TABLE Buddy (
                person_id INT NOT NULL,
                friend_person_id INT NOT NULL,
                creation DATE NOT NULL,
                PRIMARY KEY (person_id, friend_person_id)
);


CREATE TABLE Account (
                person_id INT NOT NULL,
                balance DECIMAL(8,2) NOT NULL,
                PRIMARY KEY (person_id)
);

ALTER TABLE Account MODIFY COLUMN balance DECIMAL(8, 2) COMMENT 'montant maxi sur le compte : 999 999.99€';


CREATE TABLE BankInfo (
                id INT AUTO_INCREMENT NOT NULL,
                type INT,
                info VARCHAR(100) NOT NULL,
                description VARCHAR(200) NOT NULL,
                account_person_id INT NOT NULL,
                PRIMARY KEY (id)
);

ALTER TABLE BankInfo MODIFY COLUMN type INTEGER COMMENT 'indique le type de mode de paiement bancaire (CB, RIB ...)';


CREATE TABLE Transaction (
                id INT NOT NULL,
                account_person_id INT NOT NULL,
                bankinfo_id INT NOT NULL,
                amount DECIMAL(6,2) NOT NULL,
                description VARCHAR(200),
                transaction_date DATE NOT NULL,
                PRIMARY KEY (id, account_person_id, bankinfo_id)
);

ALTER TABLE Transaction MODIFY COLUMN amount DECIMAL(6, 2) COMMENT 'montants limités à 9 999.99€';


CREATE TABLE BankTransaction (
                transaction_id INT NOT NULL,
                flow INT NOT NULL,
                PRIMARY KEY (transaction_id)
);

ALTER TABLE BankTransaction MODIFY COLUMN flow INTEGER COMMENT 'indique le sens de l''opration (crédit ou débit)';


CREATE TABLE BuddyTransaction (
                transaction_id INT NOT NULL,
                commissionAmount DECIMAL(6,2) NOT NULL,
                buddy_person_id INT NOT NULL,
                buddy_friend_person_id INT NOT NULL,
                PRIMARY KEY (transaction_id)
);


ALTER TABLE Account ADD CONSTRAINT person_account_fk
FOREIGN KEY (person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Buddy ADD CONSTRAINT person_buddy_fk
FOREIGN KEY (person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Buddy ADD CONSTRAINT person_buddy_fk1
FOREIGN KEY (friend_person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE BuddyTransaction ADD CONSTRAINT buddy_buddytransaction_fk
FOREIGN KEY (buddy_person_id, buddy_friend_person_id)
REFERENCES Buddy (person_id, friend_person_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE BankInfo ADD CONSTRAINT account_bankinfo_fk
FOREIGN KEY (account_person_id)
REFERENCES Account (person_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Transaction ADD CONSTRAINT account_transaction_fk
FOREIGN KEY (account_person_id)
REFERENCES Account (person_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Transaction ADD CONSTRAINT bankinfo_transaction_fk
FOREIGN KEY (bankinfo_id)
REFERENCES BankInfo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE BuddyTransaction ADD CONSTRAINT transaction_buddytransaction_fk
FOREIGN KEY (transaction_id)
REFERENCES Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE BankTransaction ADD CONSTRAINT transaction_banktransaction_fk
FOREIGN KEY (transaction_id)
REFERENCES Transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
