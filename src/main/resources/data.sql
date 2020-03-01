INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('admin@javaops.ru', 'Admin_First', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME)
VALUES ('Mirazur'),
       ('Asador Etxebarri'),
       ('Pujol');

INSERT INTO DISH (NAME, PRICE)
VALUES ('Curry Udon', 1999),
       ('Uni Pasta', 1111),
       ('Maine Lobster Roll', 1500),
       ('New England Clam Chowder', 3999),
       ('Wood Oven Roasted Pig Face', 1055)