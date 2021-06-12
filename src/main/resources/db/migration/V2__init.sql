BEGIN TRANSACTION;

INSERT INTO users (username, password, email)
VALUES
    -- admin
    ('admin', '$2y$12$rBtn1bbKhTB/DDe6hlBjVuLy9uDwSpfjIVCJ362N//LZI1ez9ebD2', 'admin@gmail.com'),
    -- manager
    ('manager', '$2y$12$9gRCV9aagJOH3GFQffosVON5Ou5gaxnxOnXLlj7w8AmY3OUECtZ.y', 'manager@gmail.com'),
    -- user
    ('user', '$2y$12$g3oU2ErfkSt/RMWNdjT2xeQdUT78pAURWCGGwonP2ueWuW4XJl0Sm', 'user@gmail.com');

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_MANAGER'),
       ('ROLE_USER');

INSERT INTO users_roles
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO products (title, price)
VALUES ('Adobe Photoshop Elements 2021', 150),
       ('CLIP STUDIO PAINT PRO', 60),
       ('CorelDRAW Graphics Suite 2020', 340),
       ('Corel PaintShop Pro 2021 Ultimate', 70),
       ('Cyberlink PowerDirector 19 Ultimate', 140),
       ('Corel VideoStudio Ultimate 2020', 50),
       ('Webroot Antivirus Software 2021', 20),
       ('Trend Micro Maximum Security 2021', 37),
       ('McAfee LiveSafe Ultimate Protection for Unlimited Devices', 38),
       ('Malwarebytes Premium 4.0 Latest Version', 100),
       ('Norton Small Business - 5 Device', 100),
       ('AVG Technologies AVG Internet Security 2020', 90),
       ('Windows Server 2019 Standard', 250),
       ('Windows Server 2016 Standard', 200),
       ('Window 10 Home', 105),
       ('OfficeSuite Home & Business 2021', 100),
       ('Corel WordPerfect Office 2020 Home & Student', 60),
       ('Window 7 Professional', 70),
       ('Microsoft Project Professional 2019', 460),
       ('Nuance Dragon NaturallySpeaking Premium 13', 204);

INSERT INTO categories (title)
VALUES ('Software'),
       ('Books');

INSERT INTO products_categories
VALUES (1, 1),
       (2, 1),
       (3, 1);

COMMIT;