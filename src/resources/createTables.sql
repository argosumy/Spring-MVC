CREATE SEQUENCE BOOK_ID_CATEGORY_SEQ start with 1 increment by 1 /

CREATE TABLE BOOK_CATEGORIES(
        CATEGORY_ID INTEGER NOT NULL ,
        NAME VARCHAR(20) not null ,
        DESCRIPTION VARCHAR2(1000),
        PARENT_ID INTEGER,
        CONSTRAINT CATEGORY_ID_PK PRIMARY KEY (CATEGORY_ID)) /

ALTER TABLE BOOK_CATEGORIES ADD CONSTRAINT PARENT_ID_FK FOREIGN KEY (PARENT_ID) REFERENCES BOOK_CATEGORIES(CATEGORY_ID)/

CREATE OR REPLACE TRIGGER BOOK_CATEGORIES_TR BEFORE INSERT ON BOOK_CATEGORIES FOR EACH ROW
BEGIN
SELECT BOOK_ID_CATEGORY_SEQ.nextval INTO:NEW.CATEGORY_ID FROM dual;
END; /

CREATE SEQUENCE BOOK_ID_PRODUCT_SEQ start with 1 increment by 1 /

CREATE TABLE BOOK_PRODUCT(
        PRODUCT_ID INTEGER NOT NULL,
        NAME VARCHAR(20) not null,
        DESCRIPTION VARCHAR2(1000),
        PRICE FLOAT(126),
        IS_ACTIVE INTEGER,
        CATEGORY_ID INTEGER,
        CONSTRAINT PRODUCT_ID_PK PRIMARY KEY (PRODUCT_ID)) /

ALTER TABLE BOOK_PRODUCT ADD CONSTRAINT CATEGORY_ID_FK FOREIGN KEY (CATEGORY_ID) REFERENCES BOOK_CATEGORIES(CATEGORY_ID)/

CREATE OR REPLACE TRIGGER BOOK_PRODUCT_TR BEFORE INSERT ON BOOK_PRODUCT FOR EACH ROW
BEGIN
SELECT BOOK_ID_PRODUCT_SEQ.nextval INTO:NEW.PRODUCT_ID FROM dual;
END; /

INSERT INTO BOOK_CATEGORIES(name, description, parent_id)values ('ROOT','',1)/
