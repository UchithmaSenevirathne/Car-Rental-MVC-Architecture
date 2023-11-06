DROP DATABASE IF EXISTS apexAutoRental;

CREATE DATABASE IF NOT EXISTS apexAutoRental;

SHOW DATABASES;

USE apexAutoRental;

CREATE TABLE user(
        uId VARCHAR(5) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        userName VARCHAR(10) NOT NULL,
        password VARCHAR(8) NOT NULL
);

DESC user;

SELECT * FROM user;

CREATE TABLE customer(
        id VARCHAR(5) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        email VARCHAR(20) NOT NULL,
        contact VARCHAR(11) NOT NULL
);

DESC customer;

SELECT * FROM customer;

CREATE TABLE driver(
        id VARCHAR(5) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        email VARCHAR(20) NOT NULL,
        contact VARCHAR(11) NOT NULL,
        licenseNo VARCHAR(20) NOT NULL,
        userId varchar(5),
        constraint foreign key (userId) references user(uId),
        availability VARCHAR(10) NOT NULL
);

DESC driver;

SELECT * FROM driver;

CREATE TABLE worker(
        id VARCHAR(5) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        contact VARCHAR(11) NOT NULL,
        userId varchar(5),
        constraint foreign key (userId) references user(uId)
);

DESC worker;

SELECT * FROM worker;

CREATE TABLE driverSalary(
        drSalId VARCHAR(5) PRIMARY KEY,
        amount DOUBLE (6,2) NOT NULL,
        month VARCHAR(20) NOT NULL,
        driverId varchar(5),
        constraint foreign key (driverId) references driver(id)
);

DESC driverSalary;

SELECT * FROM driverSalary;

CREATE TABLE workerSalary(
        wSalId VARCHAR(5) PRIMARY KEY,
        amount DOUBLE (6,2) NOT NULL,
        month VARCHAR(20) NOT NULL,
        driverId varchar(5),
        constraint foreign key (driverId) references driver(id)
);

DESC workerSalary;

SELECT * FROM workerSalary;

CREATE TABLE booking(
        bId VARCHAR(5) PRIMARY KEY,
        type VARCHAR(20) NOT NULL,
        cusId varchar(5),
        constraint foreign key (cusId) references customer(id)
);

DESC booking;

SELECT * FROM booking;

CREATE TABLE driverSchedule(
        drSchedule VARCHAR(5) PRIMARY KEY,
        date DATE NOT NULL,
        driverId varchar(5),
        constraint foreign key (driverId) references driver(id),
        bookingId varchar(5),
        constraint foreign key (bookingId) references booking(bId),
        rideTo VARCHAR(20) NOT NULL,
        distance DOUBLE(5,2) NOT NULL
);

DESC driverSchedule;

SELECT * FROM driverSchedule;

CREATE TABLE payment(
        payId VARCHAR(5) PRIMARY KEY,
        payAmount DOUBLE(10,2) NOT NULL,
        bookingId varchar(5),
        constraint foreign key (bookingId) references booking(bId)
);

DESC payment;

SELECT * FROM payment;

CREATE TABLE car(
        carNo VARCHAR(5) PRIMARY KEY,
        brand VARCHAR(20) NOT NULL,
        availability VARCHAR(10) NOT NULL
);

DESC car;

SELECT * FROM car;

CREATE TABLE bookingDetail(
        bDetailId VARCHAR(5) PRIMARY KEY,
        date DATE NOT NULL,
        carNumber varchar(5),
        constraint foreign key (carNumber) references car(carNo),
        bookingId varchar(5),
        constraint foreign key (bookingId) references booking(bId),
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
        carNumber varchar(5),
        constraint foreign key (carNumber) references car(carNo),
        maintainId varchar(5),
        constraint foreign key (maintainId) references carMaintain(mainId),
        status VARCHAR(10)
);

DESC maintainDetail;

SELECT * FROM maintainDetail;

SHOW TABLES;