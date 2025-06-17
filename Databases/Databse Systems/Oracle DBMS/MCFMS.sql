USE MCFMS;

-- Create all the tables necessary for the question

CREATE TABLE PROVINCES (

    ProvinceCode VARCHAR2(4) NOT NULL,
    ProvinceName VARCHAR2(13) NOT NULL,
    
    CONSTRAINT pk_Province_Code PRIMARY KEY (ProvinceCode)
    
);

CREATE TABLE MUNICIPALITIES (

    munCode VARCHAR2(4) NOT NULL,
    name VARCHAR2(27) NOT NULL,
    avgPopulation NUMBER(10, 0),
    ProvinceCode VARCHAR2(4) NOT NULL,
    
    CONSTRAINT pk_Municipality_Code PRIMARY KEY (munCode),
    CONSTRAINT fk_Provinces FOREIGN KEY (ProvinceCode) REFERENCES PROVINCES (ProvinceCode)

);

CREATE TABLE FACILITIES (

    facilityID NUMBER NOT NULL,
    name VARCHAR2(100) NOT NULL,
    capacity NUMBER,
    address VARCHAR2(250),
    munCode VARCHAR2(4) NOT NULL,
    
    CONSTRAINT pk_Facility_ID PRIMARY KEY (facilityID),
    CONSTRAINT fk_Municipalities FOREIGN KEY (munCode) REFERENCES MUNICIPALITIES (munCode)

);

CREATE TABLE ROOMS (

    roomNo NUMBER NOT NULL,
    facilityID NUMBER NOT NULL,
    description VARCHAR2(250),
    
--    The reason for creating a composite primary key is because the room number must be specifically for the facility, and multiple facilities can have the same room number
    CONSTRAINT pk_rooms PRIMARY KEY (roomNo, facilityID),
    CONSTRAINT fk_Facilities FOREIGN KEY (facilityID) REFERENCES FACILITIES (facilityID)

);

CREATE TABLE ACTIVITIES (

    actRef VARCHAR2(4) NOT NULL,
    activityName VARCHAR2(50) NOT NULL,
    
    CONSTRAINT pk_Activities PRIMARY KEY (actRef)

);

CREATE TABLE USES (

    facilityID NUMBER NOT NULL,
    actRef VARCHAR(4) NOT NULL,
    useDate DATE NOT NULL,
    
--    The reason for having a composite primary key, is because the USES table is only a connector table, and the useDate as the primary key would not be defining enough info to identify this table
    CONSTRAINT pk_Uses PRIMARY KEY (facilityID, actRef, useDate),
    CONSTRAINT fk_Facilities_Uses FOREIGN KEY (facilityID) REFERENCES FACILITIES (facilityID),
    CONSTRAINT fk_Activities FOREIGN KEY (actRef) REFERENCES ACTIVITIES (actRef)

);

--  Create a UNIQUE sequence which increment by one (1) for the tables


SELECT * FROM PROVINCES;
SELECT * FROM MUNICIPALITIES;
SELECT * FROM FACILITIES;
SELECT * FROM ROOMS;
SELECT * FROM ACTIVITIES;
SELECT * FROM USES;