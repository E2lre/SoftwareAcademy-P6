INSERT INTO person (birthdate,email,firstname,lastname,password)
    VALUES
    ('1953-01-01','james.bond@mi6.uk','James','Bond','abc'),
    ('1992-07-13','vesper.lynd@casinoroyal.com','Vesper','Lynd','abc');

INSERT INTO account (person_id,balance)
    VALUES
    (1,0),
    (2,0);

INSERT INTO bankinfo (description,info,type,account_person_id)
    VALUES
    ("0123456789","RIB",0,1),
    ("9876543210","IBAN",1,2),
    ("123456789/2021-01-31/123","CB",3,2);


