-- CREATE DATABASE `Digital_Insurance_Managment_System`;
USE `Digital_Insurance_Managment_System`;

CREATE TABLE Customers (
	customer_id int NOT NULL auto_increment UNIQUE,
    customer_national_id int NOT NULL,
    customer_name varchar(50) NOT NULL,
    customer_surnmae varchar(50) NOT NULL,
    customer_address varchar(255) NOT NULL,
    customer_age int NOT NULL,
    PRIMARY KEY (customer_id)
);

CREATE TABLE Policies (
	policy_id int NOT NULL AUTO_INCREMENT UNIQUE,
    customer_id int NOT NULL,
    policy_type varchar(100) NOT NULL,
    sum_insured decimal NOT NULL,
    coverage_amount decimal NOT NULL,
    premium_amount decimal NOT NULL,
    PRIMARY KEY (policy_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);