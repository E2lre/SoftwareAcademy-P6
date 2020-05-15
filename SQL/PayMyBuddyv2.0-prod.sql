USE paymybuddy_prod;


CREATE TABLE Person (
                id INT AUTO_INCREMENT NOT NULL,
                password VARCHAR(200) NOT NULL,
                firstname VARCHAR(100) NOT NULL,
                lastname VARCHAR(100) NOT NULL,
                birthdate DATE NOT NULL,
                email VARCHAR(500) NOT NULL,
                fail INT,
                status INT,
                PRIMARY KEY (id)
);

ALTER TABLE Person MODIFY COLUMN fail INTEGER COMMENT 'nb erreur de connexion';

ALTER TABLE Person MODIFY COLUMN status INTEGER COMMENT 'indique le statut de cet utilisateur: actif, fermé, interdit';


CREATE TABLE Transaction (
                id INT AUTO_INCREMENT NOT NULL,
                amout DECIMAL(6,2) NOT NULL,
                description VARCHAR(200),
                date DATE NOT NULL,
                user_person_id INT NOT NULL,
                buddy_person_id INT NOT NULL,
                PRIMARY KEY (id)
);

ALTER TABLE Transaction MODIFY COLUMN amout DECIMAL(6, 2) COMMENT 'montants limités à 9 999.99€';


CREATE INDEX transaction_buddy_idx
 ON Transaction
 ( buddy_person_id ASC );

CREATE INDEX transaction_user_idx
 ON Transaction
 ( user_person_id ASC );

CREATE TABLE Account (
                person_id INT NOT NULL,
                balance DECIMAL(8,2) NOT NULL,
                PRIMARY KEY (person_id)
);

ALTER TABLE Account MODIFY COLUMN balance DECIMAL(8, 2) COMMENT 'montant maxi sur le compte : 999 999.99€';


CREATE TABLE BankInfo (
                id INT AUTO_INCREMENT NOT NULL,
                type INT,
                description VARCHAR(200) NOT NULL,
                person_id INT NOT NULL,
                PRIMARY KEY (id)
);

ALTER TABLE BankInfo MODIFY COLUMN type INTEGER COMMENT 'indique le type de mode de paiement bancaire (CB, RIB ...)';


CREATE INDEX bankinfo_person_idx
 ON BankInfo
 ( person_id ASC );

CREATE TABLE BankTransaction (
                id INT AUTO_INCREMENT NOT NULL,
                flow INT NOT NULL,
                amount DECIMAL(8,2) NOT NULL,
                description VARCHAR(200) NOT NULL,
                bank_info_id INT NOT NULL,
                account_person_id INT NOT NULL,
                PRIMARY KEY (id)
);

ALTER TABLE BankTransaction MODIFY COLUMN flow INTEGER COMMENT 'indique le sens de l''opration (crédit ou débit)';

ALTER TABLE BankTransaction MODIFY COLUMN amount DECIMAL(8, 2) COMMENT 'montant maxi (toujours positif) 999 999.99€';


CREATE INDEX banktransaction_account_idx
 ON BankTransaction
 ( account_person_id );

ALTER TABLE BankInfo ADD CONSTRAINT person_bankinfo_fk
FOREIGN KEY (person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Account ADD CONSTRAINT person_account_fk
FOREIGN KEY (person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Transaction ADD CONSTRAINT person_transaction_fk
FOREIGN KEY (user_person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Transaction ADD CONSTRAINT person_transaction_fk1
FOREIGN KEY (buddy_person_id)
REFERENCES Person (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE BankTransaction ADD CONSTRAINT account_banktransaction_fk
FOREIGN KEY (account_person_id)
REFERENCES Account (person_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE BankTransaction ADD CONSTRAINT bankinfo_banktransaction_fk
FOREIGN KEY (bank_info_id)
REFERENCES BankInfo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
