INSERT INTO person(id,birthdate,email,firstname,lastname,password)
VALUES
(1,'2020-05-01','paymybuddy@paymybuddy.com','Application','System','abc'),
(2,'1953-01-01','james.bond@mi6.uk','James','Bond','abc'),
(3,'1992-07-13','vesper.lynd@casinoroyal.com','Vesper','Lynd','$2a$12$e7KSJW8IMwHfdzTA2.qgTuKo8y3oj6Mf7BaFjT.UF3dLWTpA6jeEq');

INSERT INTO account(person_id,balance)
VALUES
(1,0),
(2,1000),
(3,1000);

INSERT INTO bankinfo(id,description,info,type,bankinfo_person_id)
VALUES
(2,'Description bank info 1','RIB',0,2),
(3,'Description bank info 2','IBAN',1,3);

