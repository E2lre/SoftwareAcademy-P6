-- MySQL Script generated by MySQL Workbench
-- Wed May 20 18:50:53 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema paymybuddy_prod
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema paymybuddy_prod
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `paymybuddy_prod` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `paymybuddy_prod` ;

-- -----------------------------------------------------
-- Table `paymybuddy_prod`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy_prod`.`person` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `birthdate` DATETIME NULL DEFAULT NULL,
  `email` VARCHAR(500) NULL DEFAULT NULL,
  `firstname` VARCHAR(100) NULL DEFAULT NULL,
  `lastname` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_fwmwi44u55bo4rvwsv0cln012` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy_prod`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy_prod`.`account` (
  `balance` DOUBLE NOT NULL,
  `person_id` INT NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `person_account_fk`
    FOREIGN KEY (`person_id`)
    REFERENCES `paymybuddy_prod`.`person` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy_prod`.`bankinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy_prod`.`bankinfo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `info` VARCHAR(255) NULL DEFAULT NULL,
  `type` INT NOT NULL,
  `account_person_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `account_bankinfo_fk` (`account_person_id` ASC) VISIBLE,
  CONSTRAINT `account_bankinfo_fk`
    FOREIGN KEY (`account_person_id`)
    REFERENCES `paymybuddy_prod`.`account` (`person_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy_prod`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy_prod`.`transaction` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `amount` DOUBLE NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `transaction_date` DATETIME NULL DEFAULT NULL,
  `account_person_id` INT NULL DEFAULT NULL,
  `bankinfo_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `account_transaction_fk` (`account_person_id` ASC) VISIBLE,
  INDEX `bankinfo_transaction_fk` (`bankinfo_id` ASC) VISIBLE,
  CONSTRAINT `account_transaction_fk`
    FOREIGN KEY (`account_person_id`)
    REFERENCES `paymybuddy_prod`.`account` (`person_id`),
  CONSTRAINT `bankinfo_transaction_fk`
    FOREIGN KEY (`bankinfo_id`)
    REFERENCES `paymybuddy_prod`.`bankinfo` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy_prod`.`banktransaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy_prod`.`banktransaction` (
  `flow` INT NOT NULL,
  `transaction_id` BIGINT NOT NULL,
  PRIMARY KEY (`transaction_id`),
  CONSTRAINT `transaction_banktransaction_fk`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `paymybuddy_prod`.`transaction` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy_prod`.`buddy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy_prod`.`buddy` (
  `creation` DATETIME NULL DEFAULT NULL,
  `person_id` INT NOT NULL,
  `friend_person_id` INT NOT NULL,
  PRIMARY KEY (`person_id`, `friend_person_id`),
  UNIQUE INDEX `UK_ay9wfgdf5v6wg0lrlfmwce0ms` (`person_id` ASC) VISIBLE,
  UNIQUE INDEX `UK_gfxp8kb0a90sc2ja5r830ev2l` (`friend_person_id` ASC) VISIBLE,
  CONSTRAINT `person_buddy_fk`
    FOREIGN KEY (`person_id`)
    REFERENCES `paymybuddy_prod`.`person` (`id`),
  CONSTRAINT `person_buddy_fk1`
    FOREIGN KEY (`friend_person_id`)
    REFERENCES `paymybuddy_prod`.`person` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy_prod`.`buddytransaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy_prod`.`buddytransaction` (
  `commission_amount` DOUBLE NOT NULL,
  `transaction_id` BIGINT NOT NULL,
  `buddy_person_id` INT NULL DEFAULT NULL,
  `buddy_friend_person_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  INDEX `buddy_buddytransaction_fk` (`buddy_person_id` ASC, `buddy_friend_person_id` ASC) VISIBLE,
  CONSTRAINT `buddy_buddytransaction_fk`
    FOREIGN KEY (`buddy_person_id` , `buddy_friend_person_id`)
    REFERENCES `paymybuddy_prod`.`buddy` (`person_id` , `friend_person_id`),
  CONSTRAINT `transaction_buddytransaction_fk`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `paymybuddy_prod`.`transaction` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
