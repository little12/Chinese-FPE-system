create table USER
(
    ID           INTEGER auto_increment,
    NAME         VARCHAR(50),
    ACCOUNT_ID   VARCHAR(100),
    TOKEN        VARCHAR(50),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT
);