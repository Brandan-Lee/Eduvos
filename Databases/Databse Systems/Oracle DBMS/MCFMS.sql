USE MCFMS;

-- Create all the tables necessary for the question

CREATE TABLE PROVINCES (

    ProvinceCode VARCHAR2(5) NOT NULL,
    ProvinceName VARCHAR2(13) NOT NULL,
    
    CONSTRAINT pk_Province_Code PRIMARY KEY (ProvinceCode)
    
);

CREATE TABLE MUNICIPALITIES (

    munCode VARCHAR2(5) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    avgPopulation NUMBER(10, 0),
    ProvinceCode VARCHAR2(10) NOT NULL,
    
    CONSTRAINT pk_Municipality_Code PRIMARY KEY (munCode),
    CONSTRAINT fk_Provinces FOREIGN KEY (ProvinceCode) REFERENCES PROVINCES (ProvinceCode)

);

CREATE TABLE FACILITIES (

    facilityID VARCHAR2(6) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    capacity NUMBER,
    address VARCHAR2(250),
    munCode VARCHAR2(10) NOT NULL,
    
    CONSTRAINT pk_Facility_ID PRIMARY KEY (facilityID),
    CONSTRAINT fk_Municipalities FOREIGN KEY (munCode) REFERENCES MUNICIPALITIES (munCode)

);

CREATE TABLE ROOMS (

    roomNo VARCHAR2(4) NOT NULL,
    facilityID VARCHAR2(6) NOT NULL,
    description VARCHAR2(250),
    
--    The reason for creating a composite primary key is because the room number must be specifically for the facility, and multiple facilities can have the same room number
    CONSTRAINT pk_rooms PRIMARY KEY (roomNo, facilityID),
    CONSTRAINT fk_Facilities FOREIGN KEY (facilityID) REFERENCES FACILITIES (facilityID)

);

CREATE TABLE ACTIVITIES (

    actRef VARCHAR2(6) NOT NULL,
    activityName VARCHAR2(50) NOT NULL,
    
    CONSTRAINT pk_Activities PRIMARY KEY (actRef)

);

CREATE TABLE USES (

    facilityID VARCHAR2(6) NOT NULL,
    actRef VARCHAR(6) NOT NULL,
    useDate DATE NOT NULL,
    
--    The reason for having a composite primary key, is because the USES table is only a connector table, and the useDate as the primary key would not be defining enough info to identify this table
    CONSTRAINT pk_Uses PRIMARY KEY (facilityID, actRef, useDate),
    CONSTRAINT fk_Facilities_Uses FOREIGN KEY (facilityID) REFERENCES FACILITIES (facilityID),
    CONSTRAINT fk_Activities FOREIGN KEY (actRef) REFERENCES ACTIVITIES (actRef)

);

--  Create a UNIQUE sequence which increment by one (1) for the tables
CREATE SEQUENCE seq_pk_increment
START WITH 1
INCREMENT BY 1
NOCACHE;

--  Insert data into each table
-- PROVINCES TABLE
INSERT INTO PROVINCES(ProvinceCode, ProvinceName)
VALUES ('GP', 'Gauteng');

INSERT INTO PROVINCES(ProvinceCode, ProvinceName)
VALUES ('WP', 'Western Cape');

--  MUNICIPALITIES TABLE
INSERT INTO MUNICIPALITIES(munCode, name, avgPopulation, ProvinceCode)
VALUES ('JHB', 'City of Johannesburg', 4949347, 'GP');

INSERT INTO MUNICIPALITIES(munCode, name, avgPopulation, ProvinceCode)
VALUES ('CPT', 'City of Cape Town', 4005016, 'WC');

--  FACILITIES TABLE
-- explanation of 'FAC' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'
-- 'FAC' is what I want my data to start with
-- || this is used for string concatenation
-- LPAD this is a function that allows you as a user to specify a specify format, i get my sequence, after fac there are 3 spaces still left, and the 0 are the placeholders before the value for the
--sequence is added
INSERT INTO FACILITIES(facilityID, name, capacity, address, munCode)
VALUES ('FAC' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'Modise Cultural Facility', 500, '123 Cultural St', 'JHB');

INSERT INTO FACILITIES(facilityID, name, capacity, address, munCode)
VALUES ('FAC' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'Ubuntu Arts Centre', 300, '456 Ubuntu Ave', 'CPT');

--  ROOMS TABLE
INSERT INTO ROOMS(roomNo, facilityID, description)
VALUES ('R' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'FAC001', 'Theatre');

INSERT INTO ROOMS(roomNo, facilityID, description)
VALUES ('R' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'FAC001', 'Cinema');

INSERT INTO ROOMS(roomNo, facilityID, description)
VALUES ('R' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'FAC002', 'Exhibition Hall');

INSERT INTO ROOMS(roomNo, facilityID, description)
VALUES ('R' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'FAC002', 'Workshop Room');

--  ACTIVITIES TABLE
INSERT INTO ACTIVITIES(actRef, activityName)
VALUES ('ACT' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'IsiZulu Dance Competition');

INSERT INTO ACTIVITIES(actRef, activityName)
VALUES ('ACT' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'Art Exhibition');

INSERT INTO ACTIVITIES(actRef, activityName)
VALUES ('ACT' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'Music Concert');

INSERT INTO ACTIVITIES(actRef, activityName)
VALUES ('ACT' || LPAD(seq_pk_increment.NEXTVAL, 3, '0'), 'Theatre Performance');

-- USES TABLE
INSERT INTO USES(facilityID, actRef, useDate)
VALUES ('FAC001', 'ACT001', TO_DATE('2025-05-20', 'YYYY-MM-DD'));

INSERT INTO USES(facilityID, actRef, useDate)
VALUES ('FAC001', 'ACT002', TO_DATE('2025-06-20', 'YYYY-MM-DD'));

INSERT INTO USES(facilityID, actRef, useDate)
VALUES ('FAC002', 'ACT003', TO_DATE('2025-10-20', 'YYYY-MM-DD'));

INSERT INTO USES(facilityID, actRef, useDate)
VALUES ('FAC002', 'ACT004', TO_DATE('2025-12-12', 'YYYY-MM-DD'));

--  Retrieve and display the number of municipalities that do not have any facility with an activity of ‘Music’ using a suitable query. Remember to use multiple JOIN statements, aggregate functions,
--suitable wild cards character and sub-query where necessary.                              
SELECT COUNT(munCode) AS municipalities_count
FROM MUNICIPALITIES
WHERE MUNICIPALITIES.munCode NOT IN (

        SELECT FACILITIES.munCode
        FROM FACILITIES
        JOIN USES ON FACILITIES.facilityID = USES.facilityID
        JOIN ACTIVITIES ON USES.actRef = ACTIVITIES.actRef
        WHERE ACTIVITIES.activityName LIKE '%Music%'

);

--  Write a query to retrieve and display a list of provinces that have municipalities with an average population of 4,000,000 (4 million) or more. You may consider the use of sub-query and
--  relevant conditional operators in obtaining the output if necessary.  
SELECT PROVINCES.ProvinceCode, PROVINCES.ProvinceName
FROM PROVINCES
JOIN MUNICIPALITIES ON PROVINCES.ProvinceCode = MUNICIPALITIES.ProvinceCode
WHERE MUNICIPALITIES.avgPopulation >= 4000000;


SELECT * FROM PROVINCES;
SELECT * FROM MUNICIPALITIES;
SELECT * FROM FACILITIES;
SELECT * FROM ROOMS;
SELECT * FROM ACTIVITIES;
SELECT * FROM USES;