-- create database logs
CREATE TABLE LOGS
   (ID BIGSERIAL PRIMARY KEY, 
    USER_ID VARCHAR(20)    NOT NULL,
    TIME   TIMESTAMP	   NOT NULL,
    LOGGER  VARCHAR(200)    NOT NULL,
    LEVEL   VARCHAR(20)    NOT NULL,
    MESSAGE text  NOT NULL
   );