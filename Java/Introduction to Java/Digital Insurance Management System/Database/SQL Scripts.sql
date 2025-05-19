CREATE DATABASE `Digital_Insurance_Managment_System`;
USE `Digital_Insurance_Managment_System`;

CREATE TABLE Customers (
	customer_Id int NOT NULL auto_increment UNIQUE,
    customer_name varchar(50) NOT NULL,
    customer_age int NOT NULL,
    PRIMARY KEY (customer_id)
);

CREATE TABLE Policies (
	policy_Id int NOT NULL AUTO_INCREMENT UNIQUE,
    policy_type varchar(100) NOT NULL,
    sum_insured decimal NOT NULL,
    coverage_amount decimal NOT NULL,
    premium_amount decimal NOT NULL,
    customer_id int NOT NULL,
    PRIMARY KEY (policy_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);