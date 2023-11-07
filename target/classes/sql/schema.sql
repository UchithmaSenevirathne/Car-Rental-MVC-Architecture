DROP DATABASE IF EXISTS apexAutoRental;

CREATE DATABASE IF NOT EXISTS apexAutoRental;

SHOW DATABASES;

USE apexAutoRental;

CREATE TABLE user(
        userName VARCHAR(20) PRIMARY KEY,
        password VARCHAR(20) NOT NULL
);

DESC user;

SELECT * FROM user;

CREATE TABLE customer(
        cusId VARCHAR(5) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        email VARCHAR(20) NOT NULL,
        contact VARCHAR(11) NOT NULL,
        userName varchar(20),
        constraint foreign key (userName) references user(userName)
);

DESC customer;

SELECT * FROM customer;

CREATE TABLE driver(
        drId VARCHAR(5) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        email VARCHAR(20) NOT NULL,
        contact VARCHAR(11) NOT NULL,
        licenseNo VARCHAR(20) NOT NULL,
        userName varchar(20),
        constraint foreign key (userName) references user(userName)
);

DESC driver;

SELECT * FROM driver;

CREATE TABLE worker(
        wId VARCHAR(5) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        contact VARCHAR(11) NOT NULL,
        userName varchar(20),
        constraint foreign key (userName) references user(userName)
);

DESC worker;

SELECT * FROM worker;

CREATE TABLE driverSalary(
        drSalId VARCHAR(5) PRIMARY KEY,
        amount DOUBLE (6,2) NOT NULL,
        month VARCHAR(20) NOT NULL,
        drId varchar(5),
        constraint foreign key (drId) references driver(drId)
);

DESC driverSalary;

SELECT * FROM driverSalary;

CREATE TABLE workerSalary(
        wSalId VARCHAR(5) PRIMARY KEY,
        amount DOUBLE (6,2) NOT NULL,
        month VARCHAR(20) NOT NULL,
        wId varchar(5),
        constraint foreign key (wId) references worker(wId)
);

DESC workerSalary;

SELECT * FROM workerSalary;

CREATE TABLE booking(
        bId VARCHAR(5) PRIMARY KEY,
        type VARCHAR(20) NOT NULL,
        cusId varchar(5),
        constraint foreign key (cusId) references customer(cusId)
);

DESC booking;

SELECT * FROM booking;

CREATE TABLE driverSchedule(
        drSchedule VARCHAR(5) PRIMARY KEY,
        date DATE NOT NULL,
        drId varchar(5),
        constraint foreign key (drId) references driver(drId),
        bId varchar(5),
        constraint foreign key (bId) references booking(bId),
        rideTo VARCHAR(20) NOT NULL,
        distance DOUBLE(5,2) NOT NULL
);

DESC driverSchedule;

SELECT * FROM driverSchedule;

CREATE TABLE payment(
        payId VARCHAR(5) PRIMARY KEY,
        payAmount DOUBLE(10,2) NOT NULL,
        bId varchar(5),
        constraint foreign key (bId) references booking(bId)
);

DESC payment;

SELECT * FROM payment;

CREATE TABLE car(
        carNo VARCHAR(5) PRIMARY KEY,
        brand VARCHAR(20) NOT NULL
);

DESC car;

SELECT * FROM car;

CREATE TABLE bookingDetail(
        bDetailId VARCHAR(5) PRIMARY KEY,
        date DATE NOT NULL,
        carNo varchar(5),
        constraint foreign key (carNo) references car(carNo),
        bId varchar(5),
        constraint foreign key (bId) references booking(bId),
        rideTo VARCHAR(20) NOT NULL,
        distance DOUBLE(5,2) NOT NULL,
        status VARCHAR(10)
);

DESC bookingDetail;

SELECT * FROM bookingDetail;

CREATE TABLE carMaintain(
                    mainId VARCHAR(5) PRIMARY KEY,
                    type VARCHAR(10) NOT NULL
);

DESC carMaintain;

SELECT * FROM carMaintain;

CREATE TABLE maintainDetail(
        mainDetailId VARCHAR(5) PRIMARY KEY,
        date DATE NOT NULL,
        carNo varchar(5),
        constraint foreign key (carNo) references car(carNo),
        mainId varchar(5),
        constraint foreign key (mainId) references carMaintain(mainId),
        status VARCHAR(10)
);

DESC maintainDetail;

SELECT * FROM maintainDetail;

SHOW TABLES;