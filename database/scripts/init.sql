CREATE TABLE WARDS
(
    ID       bigserial PRIMARY KEY,
    NAME     varchar(50),
    MAXCOUNT numeric(1000, 0)
);

CREATE TABLE DIAGNOSIS
(
    ID   bigserial PRIMARY KEY,
    NAME varchar(50)
);

CREATE TABLE PEOPLE
(
    ID           bigserial PRIMARY KEY,
    FIRST_NAME   varchar(20),
    LAST_NAME    varchar(20),
    FATHER_NAME  varchar(20),
    DIAGNOSIS_ID bigint,
    WARD_ID      bigint,
    FOREIGN KEY (DIAGNOSIS_ID) REFERENCES DIAGNOSIS,
    FOREIGN KEY (WARD_ID) REFERENCES WARDS
);
