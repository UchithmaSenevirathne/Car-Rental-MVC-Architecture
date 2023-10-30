DROP DATABASE IF EXISTS apexAutoRental;

CREATE DATABASE IF NOT EXISTS apexAutoRental;

SHOW DATABASES;

USE apexAutoRental;

CREATE TABLE customer(
                         id VARCHAR(5) PRIMARY KEY,
                         name VARCHAR(20) NOT NULL,
                         address TEXT NOT NULL,
                         email VARCHAR(20) NOT NULL,
                         contact VARCHAR(11) NOT NULL
);

SHOW TABLES;