CREATE TABLE WARDS
(
    ID       serial PRIMARY KEY,
    NAME     varchar(50),
    MAX_COUNT int
);

CREATE TABLE DIAGNOSIS
(
    ID   serial PRIMARY KEY,
    NAME varchar(50)
);

CREATE TABLE PEOPLE
(
    ID           serial PRIMARY KEY,
    FIRST_NAME   varchar(20),
    LAST_NAME    varchar(20),
    FATHER_NAME  varchar(20),
    DIAGNOSIS_ID int,
    WARD_ID      int,
    FOREIGN KEY (DIAGNOSIS_ID) REFERENCES DIAGNOSIS,
    FOREIGN KEY (WARD_ID) REFERENCES WARDS
);
