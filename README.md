# Pay My Buddy
API REST application for managing an payment system. 
This app use SPRINT BOOT, My sql.

## Getting Started

- Endpoint : http://localhost:8080/
- Actuator : http://localhost:8090/

## Prerequisites

What things you need to install the software and how to install them

- Java 1.8
- Maven 3.6.2
- Spring Boot 2.2.6
- My SQL 8.0

# Installation

Database création :
- Create the database and the schema  
- Execute drop.sql and create.sql
- create the environnement variable JASYPT_ENCRYPTOR_PASSWORD for the key of encryption
- The database password is crypt with jasypt 
- At the first execution, be sure that the properties spring.jpa.hibernate.ddl-auto is to create and after put the value none
 
First element in database : excecute data.sql to insert system user. 2 lignes are mandatory 
- in person table : insert "paymybuddy@paymybuddy.com" ligne
- in account table : insert id 1 ligne

To start the application execute:
- java -jar paysystem-0.0.1-SNAPSHOT.jar

# Class Diagram
![ScreenShot](D-ClassV5.4.JPG)

# MPD
![ScreenShot](paymybuddy1.0.png)

# URI
## Signup

POST http://localhost:8080/signup

boby example :

{

    "firstName": "May",
    "lastName": "Day",
    "birthdate": "1985-10-24",
    "email": "may.day@dangeurementvotre.fr",
    "password": "SPECTRE"
    
}

## Signin

POST http://localhost:8080/signin

boby example :

{

    "email": "james.bond@mi6.uk",
    "password": "abc"
    
}

## List of persons

GET http://localhost:8080/persons

## Create a Buddy

POST http://localhost:8080/buddy

boby example :

{

    "myEmail": "james.bond@mi6.uk",
    "buddyEmail": "vesper.lynd@casinoroyal.com",
    "buddyFirstname": "",
    "buddyLastName": ""
    
}

## List my buddy

GEST http://localhost:8080/buddy/{email}


## Operate payement betwwen buddys

POST http://localhost:8080/transaction/buddy

boby example :

{

    "myEmail": "james.bond@mi6.uk",
    "buddyEmail": "vesper.lynd@casinoroyal.com",
    "description": "Il était une fois une transaction",
    "transactionAmount": "10",
    "feeAmount": ""
}

## List bank info for a person

GET http://localhost:8080/bankinfo/{email}

## Create bankinfo for a person

POST http://localhost:8080/bankinfo

boby example :

    {
  
        "type": 1,
        "info": "IBAN",
        "description": "abcdef",
        "person": {
              "email": "james.bond@mi6.uk"
        }
    }
    
## Credit car operation

POST http://localhost:8080/transaction/creditcard/{email}

boby example :

{

	"creditCardNumber": "1234567890123456",
	"expirationDate" :"2020-12-31",
	"ccv" : "123",
	"description" : "Operation par CB",
	"amount" : "1000"
	
}

## Transfert money to the bank

POST http://localhost:8080/transaction/bank/{email}

boby example :

{

	"description" : "virement à la banque",
	"amount" : "500",
    "bankinfo": {
              "id": "2"
        }
        
}
