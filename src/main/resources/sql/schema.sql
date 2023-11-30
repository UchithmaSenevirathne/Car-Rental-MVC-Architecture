DROP DATABASE IF EXISTS apexAutoRental;

CREATE DATABASE IF NOT EXISTS apexAutoRental;

SHOW DATABASES;

USE apexAutoRental;

CREATE TABLE user(
        userName VARCHAR(20) PRIMARY KEY,
        password VARCHAR(20) NOT NULL,
        email VARCHAR(20) NOT NULL,
        role VARCHAR(20) NOT NULL
);

INSERT INTO user VALUES("Sadmin", "1234", "uchithma@gmail.com", "ADM");

DESC user;
SELECT * FROM user;

CREATE TABLE login(
        logId VARCHAR(10) PRIMARY KEY,
        userName VARCHAR(20) NOT NULL,
        date VARCHAR(20) NOT NULL,
        time VARCHAR(15) NOT NULL
);

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
        userName varchar(20) NOT NULL,
        constraint foreign key (userName) references user(userName),
        availability VARCHAR(10) NOT NULL
);

DESC driver;
SELECT * FROM driver;

CREATE TABLE car(
        carNo VARCHAR(10) PRIMARY KEY,
        brand VARCHAR(20) NOT NULL,
        availability VARCHAR(10) NOT NULL,
        currentMileage DOUBLE(8,2) NOT NULL,
        kmOneDay DOUBLE(8,2) NOT NULL,
        priceOneDay DOUBLE(8,2) NOT NULL,
        priceExtraKm DOUBLE(8,2) NOT NULL
);

DESC car;
SELECT * FROM car;

CREATE TABLE booking(
        bId VARCHAR(10) PRIMARY KEY,
        pickUpDate VARCHAR(10) NOT NULL,
        days INT(5) NOT NULL,
        status VARCHAR(50) NOT NULL,
        payment DOUBLE(8,2) NOT NULL,
        cusId varchar(10) NOT NULL,
        constraint foreign key (cusId) references customer(cusId)
);

DESC booking;
SELECT * FROM booking;

CREATE TABLE bookingDetail(
        bId varchar(10) NOT NULL,
        constraint foreign key (bId) references booking(bId),
        carNo varchar(10) NOT NULL,
        constraint foreign key (carNo) references car(carNo),
        drId varchar(10) NOT NULL,
        constraint foreign key (drId) references driver(drId)
);

DESC bookingDetail;
SELECT * FROM bookingDetail;

CREATE TABLE payment(
        bId varchar(10) NOT NULL,
        constraint foreign key (bId) references booking(bId),
        totalPayment DOUBLE(10,2) NOT NULL,
        pickUpDate  VARCHAR(10) NOT NULL
);

DESC payment;
SELECT * FROM payment;

CREATE TABLE oneCarPayment(
        bId varchar(10) NOT NULL,
        constraint foreign key (bId) references booking(bId),
        carNo varchar(10) NOT NULL,
        constraint foreign key (carNo) references car(carNo),
        extraKm DOUBLE (5,2) NOT NULL,
        driverCost DOUBLE (10,2) NOT NULL,
        subTotal DOUBLE(10,2) NOT NULL
);

CREATE TABLE driverSalary(
        drSalId VARCHAR(10) PRIMARY KEY,
        drId varchar(10) NOT NULL,
        constraint foreign key (drId) references driver(drId),
        amount DOUBLE (8,2) NOT NULL,
        month VARCHAR(20) NOT NULL
);

DESC driverSalary;
SELECT * FROM driverSalary;

CREATE TABLE driverSchedule(
        drScheduleNo VARCHAR(10) PRIMARY KEY,
        date VARCHAR(10) NOT NULL,
        drId varchar(10) NOT NULL,
        constraint foreign key (drId) references driver(drId),
        bId varchar(10) NOT NULL,
        constraint foreign key (bId) references booking(bId),
        rideTo VARCHAR(20) NOT NULL,
        distance DOUBLE(8,2) NOT NULL
);

DESC driverSchedule;
SELECT * FROM driverSchedule;

SHOW TABLES;

select bd.bId,c.name,cr.brand,c.address,c.contact,b.pickUpDate,b.days from car cr left join bookingdetail bd on cr.carNo = bd.carNo left join booking b on bd.bId = b.bId left join customer c on b.cusId = c.cusId where bd.drId = 'D001';

select c.cusId,c.name,c.address,c.email,c.contact,b.bId,cr.brand,cr.priceOneDay,b.days,pc.extraKm,cr.priceExtraKm,pc.driverCost,pc.subTotal,p.totalPayment from customer c left join booking b on c.cusId = b.cusId left join bookingDetail bd on b.bId = bd.bId left join payment p on b.bId = p.bId left join driver d on bd.drId = d.drId left join car cr on bd.carNo = cr.carNo left join oneCarPayment pc on cr.carNo = pc.carNo where b.bId = 'B001';

select b.bId,b.cusId,b.pickUpDate,b.days,b.payment,bd.drId,bd.carNo from bookingDetail bd left join booking b on b.bId = bd.bId where status = 'Pending';

select b.bId,b.cusId,b.pickUpDate,b.days,p.totalPayment from booking b join payment p on b.bId = p.bId where b.status = 'PAID';