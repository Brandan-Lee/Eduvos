-- CREATE DATABASE `Digital_Insurance_Managment_System`;
USE `Digital_Insurance_Managment_System`;

CREATE TABLE Customers (
	customer_id INT NOT NULL AUTO_INCREMENT UNIQUE,
    customer_national_id VARCHAR(13) NOT NULL UNIQUE,
    customer_name VARCHAR(50) NOT NULL,
    customer_surname VARCHAR(50) NOT NULL,
    customer_address VARCHAR(255) NOT NULL,
    customer_age INT NOT NULL CHECK (customer_age > 0),
    PRIMARY KEY (customer_id)
);

CREATE TABLE Policies (
	policy_id INT NOT NULL AUTO_INCREMENT UNIQUE,
    customer_id INT NOT NULL,
    policy_type VARCHAR(100) NOT NULL,
    sum_insured DECIMAL(10, 2) NOT NULL,
    coverage_amount DECIMAL(10,2) NOT NULL,
    premium_amount DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (policy_id),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
)

SELECT * FROM Customers;

SELECT * FROM policies;