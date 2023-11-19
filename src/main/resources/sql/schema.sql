DROP DATABASE IF EXISTS apexAutoRental;

CREATE DATABASE IF NOT EXISTS apexAutoRental;

SHOW DATABASES;

USE apexAutoRental;

CREATE TABLE user(
        userName VARCHAR(20) PRIMARY KEY,
        password VARCHAR(20) NOT NULL,
        role VARCHAR(20) NOT NULL
);

INSERT INTO user VALUES("admin", "1234", "ADM");

DESC user;
SELECT * FROM user;

CREATE TABLE customer(
        cusId VARCHAR(10) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        email VARCHAR(20) NOT NULL,
        contact VARCHAR(15) NOT NULL
);

DESC customer;
SELECT * FROM customer;

CREATE TABLE driver(
        drId VARCHAR(10) PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        address TEXT NOT NULL,
        email VARCHAR(20) NOT NULL,
        contact VARCHAR(11) NOT NULL,
        licenseNo VARCHAR(20) NOT NULL,
        userName varchar(20),
        constraint foreign key (userName) references user(userName),
        availability VARCHAR(10)
);

DESC driver;
SELECT * FROM driver;

CREATE TABLE car(
        carNo VARCHAR(10) PRIMARY KEY,
        brand VARCHAR(20) NOT NULL,
        availability VARCHAR(10),
        currentMileage DOUBLE(8,2),
        kmOneDay DOUBLE(8,2),
        priceOneDay DOUBLE(8,2),
        priceExtraKm DOUBLE(8,2)
);

DESC car;
SELECT * FROM car;

CREATE TABLE booking(
        bId VARCHAR(10) PRIMARY KEY,
        pickUpDate VARCHAR(10) NOT NULL,
        days INT(5) ,
        status VARCHAR(50) NOT NULL,
        payment DOUBLE(8,2),
        cusId varchar(10),
        constraint foreign key (cusId) references customer(cusId)
);

DESC booking;
SELECT * FROM booking;

CREATE TABLE bookingDetail(
        bId varchar(10),
        constraint foreign key (bId) references booking(bId),
        carNo varchar(10),
        constraint foreign key (carNo) references car(carNo),
        drId varchar(10),
        constraint foreign key (drId) references driver(drId)
);

DESC bookingDetail;
SELECT * FROM bookingDetail;

CREATE TABLE payment(
        bId varchar(10),
        constraint foreign key (bId) references booking(bId),
        totalPayment DOUBLE(10,2),
        pickUpDate  VARCHAR(10) NOT NULL
);

DESC payment;
SELECT * FROM payment;

CREATE TABLE driverSalary(
        drSalId VARCHAR(10) PRIMARY KEY,
        amount DOUBLE (8,2) NOT NULL,
        month VARCHAR(20) NOT NULL,
        drId varchar(10),
        constraint foreign key (drId) references driver(drId)
);

DESC driverSalary;
SELECT * FROM driverSalary;

CREATE TABLE driverSchedule(
        drScheduleNo VARCHAR(10) PRIMARY KEY,
        date VARCHAR(10) NOT NULL,
        drId varchar(10),
        constraint foreign key (drId) references driver(drId),
        bId varchar(10),
        constraint foreign key (bId) references booking(bId),
        rideTo VARCHAR(20) NOT NULL,
        distance DOUBLE(8,2) NOT NULL
);

DESC driverSchedule;
SELECT * FROM driverSchedule;

CREATE TABLE carMaintain(
        mainId VARCHAR(5) PRIMARY KEY,
        type VARCHAR(10) NOT NULL
);

DESC carMaintain;
SELECT * FROM carMaintain;

CREATE TABLE maintainDetail(
        mainDetailId VARCHAR(5) PRIMARY KEY,
        date VARCHAR(10) NOT NULL,
        carNo varchar(5),
        constraint foreign key (carNo) references car(carNo),
        mainId varchar(5),
        constraint foreign key (mainId) references carMaintain(mainId),
        status VARCHAR(10)
);

DESC maintainDetail;
SELECT * FROM maintainDetail;

SHOW TABLES;