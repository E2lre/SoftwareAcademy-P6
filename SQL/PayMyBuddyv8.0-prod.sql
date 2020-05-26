
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
                transaction_id INT NOT NULL,
                amount DECIMAL(6,2) NOT NULL,
                transaction_type VARCHAR(30) NOT NULL,
                description VARCHAR(200),
                transaction_date DATE NOT NULL,
                bankinfo_id INT NOT NULL,
                account_person_id INT NOT NULL,
                PRIMARY KEY (transaction_id)
);

ALTER TABLE Transaction MODIFY COLUMN amount DECIMAL(6, 2) COMMENT 'montants limités à 9 999.99€';


CREATE TABLE Credit (
                transaction_id INT NOT NULL,
                buddy_person_id INT NOT NULL,
                buddy_friend_person_id INT NOT NULL,
                PRIMARY KEY (transaction_id)
);


CREATE TABLE CreditCardOperation (
                transaction_id INT NOT NULL,
                credit_card_order INT NOT NULL,
                PRIMARY KEY (transaction_id)
);


CREATE TABLE BankTransfert (
                transaction_id INT NOT NULL,
                transfert_order INT NOT NULL,
                PRIMARY KEY (transaction_id)
);


CREATE TABLE Payment (
                transaction_id INT NOT NULL,
                feeAmount DECIMAL(6,2) NOT NULL,
                buddy_person_id INT NOT NULL,
                buddy_friend_person_id INT NOT NULL,
                PRIMARY KEY (transaction_id)
);


ALTER TABLE Account ADD CONSTRAINT person_account_fk
FOREIGN KEY (person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Buddy ADD CONSTRAINT person_buddy_fk1
FOREIGN KEY (friend_person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Buddy ADD CONSTRAINT person_buddy_fk
FOREIGN KEY (person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Payment ADD CONSTRAINT buddy_buddytransaction_fk
FOREIGN KEY (buddy_person_id, buddy_friend_person_id)
REFERENCES Buddy (person_id, friend_person_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Credit ADD CONSTRAINT buddy_credit_fk
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

ALTER TABLE Payment ADD CONSTRAINT transaction_buddytransaction_fk
FOREIGN KEY (transaction_id)
REFERENCES Transaction (transaction_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE BankTransfert ADD CONSTRAINT transaction_banktransaction_fk
FOREIGN KEY (transaction_id)
REFERENCES Transaction (transaction_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE CreditCardOperation ADD CONSTRAINT transaction_creditcardoperation_fk
FOREIGN KEY (transaction_id)
REFERENCES Transaction (transaction_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Credit ADD CONSTRAINT transaction_credit_fk
FOREIGN KEY (transaction_id)
REFERENCES Transaction (transaction_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
