-- Vnnn__MIGRAION_NAME.sql
-- SQL 파일명에 다음 규칙을 따르면 Migration시 자동적용됩니다.
-- 1. 설정파일(application.yml) 'spring.flyway.locations' 에 파일이 위치할것
-- 2. 'V'문자로 시작 후 실행순서에 따른 숫자를 적습니다. ex) V001, V022, V52

CREATE TABLE BOOK
(
    TITLE     VARCHAR(100) NOT NULL,
    ISBM      VARCHAR(100) NOT NULL,
    AUTHOR    VARCHAR(100) NOT NULL,
    BOOK_TYPE VARCHAR(5)   NOT NULL
        CONSTRAINT BOOK_NOTE_DEFAULT DEFAULT '0',
    NOTE      VARCHAR(100) NULL
        CONSTRAINT BOOK_NOTE_DEFAULT DEFAULT 'DEFAULT',

    CONSTRAINT BOOK_PK PRIMARY KEY
        (
         TITLE,
         ISBM,
         AUTHOR
            )
)
