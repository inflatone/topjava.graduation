INSERT INTO USERS (ID, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES (1, 'user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       (2, 'admin@javaops.ru', 'Admin_First', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (ID, NAME)
VALUES (1, 'Mirazur'),
       (2, 'Asador Etxebarri'),
       (3, 'Pujol');

INSERT INTO DISH (ID, NAME, PRICE, RESTAURANT_ID)
VALUES (1, 'Curry Udon', 1999, 1),
       (2, 'Uni Pasta', 1111, 1),
       (3, 'Maine Lobster Roll', 1500, 1),
       (4, 'Tea Leaf Salad', 1500, 1),
       (5, 'Swiss pikeperch with fennel and lemon', 1500, 1),
       (6, 'New England Clam Chowder', 3999, 2),
       (7, 'Wood Oven Roasted Pig Face', 1055, 2),
       (8, 'Barbecued quail, seasonal condiments and steamed bun', 4099, 2),
       (9, 'Eel from the Oosterschelde', 4999, 3),
       (10, 'Steamed fresh flower crab with aged Chinese wine', 3290, 3);

INSERT INTO LUNCH (ID, DATE, RESTAURANT_ID)
VALUES (1, DATEADD(DAY, -2, CURRENT_DATE), 1),
       (2, DATEADD(DAY, -2, CURRENT_DATE), 2),
       (3, DATEADD(DAY, -2, CURRENT_DATE), 3),
       (4, DATEADD(DAY, -1, CURRENT_DATE), 1),
       (5, DATEADD(DAY, -1, CURRENT_DATE), 2),
       (6, DATEADD(DAY, -1, CURRENT_DATE), 3),
       (7, CURRENT_DATE, 1),
       (8, CURRENT_DATE, 2),
       (9, CURRENT_DATE, 3);
INSERT INTO LUNCH_DISHES (LUNCH_ID, DISH_ID)
VALUES (1, 1),
       (1, 3),
       (1, 4),
       (2, 6),
       (2, 7),
       (3, 10),
       (4, 1),
       (4, 2),
       (4, 5),
       (5, 7),
       (5, 8),
       (6, 9),
       (7, 2),
       (7, 3),
       (7, 5),
       (8, 6),
       (8, 8),
       (9, 9);