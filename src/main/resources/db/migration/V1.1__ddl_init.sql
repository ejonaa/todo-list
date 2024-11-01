create table IF NOT EXISTS T_USER
(
    ID            BIGINT primary key,
    LOGIN         varchar(50)  not null,
    PASSWORD_HASH varchar(60)  not null,
    FIRST_NAME    varchar(50)  not null,
    LAST_NAME     varchar(50)  not null,
    CRT_TIME      TIMESTAMP(6) not null,
    MOD_TIME      TIMESTAMP(6)
);

create table IF NOT EXISTS T_PROJECT
(
    ID         BIGINT primary key,
    TITLE      varchar(50)  not null,
    USER_ID    INTEGER,
    ID_USR_CRT BIGINT       not null,
    CRT_TIME   TIMESTAMP(6) not null,
    ID_USR_MOD BIGINT,
    MOD_TIME   TIMESTAMP(6)
);

create table IF NOT EXISTS T_TASK
(
    ID         BIGINT primary key,
    NAME       varchar(50)  not null,
    NOTE       VARCHAR(3000),
    DUE_DATE   DATE,
    COMPLETED  BOOLEAN,
    IMPORTANT  BOOLEAN,
    LIST_ID    INTEGER,
    USER_ID    INTEGER,
    ID_USR_CRT BIGINT       not null,
    CRT_TIME   TIMESTAMP(6) not null,
    ID_USR_MOD BIGINT,
    MOD_TIME   TIMESTAMP(6)
);

create table IF NOT EXISTS T_STEP
(
    ID         BIGINT primary key,
    NAME       varchar(50)  not null,
    TASK_ID    INTEGER,
    USER_ID    INTEGER,
    ID_USR_CRT BIGINT       not null,
    CRT_TIME   TIMESTAMP(6) not null,
    ID_USR_MOD BIGINT,
    MOD_TIME   TIMESTAMP(6)
);

CREATE SEQUENCE SEQ_T_USER AS BIGINT INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_T_PROJECT AS BIGINT INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_T_TASK AS BIGINT INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_T_STEP AS BIGINT INCREMENT BY 1 START WITH 1;

