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
       ('CorelDRAW Graphics Suite 2020', 340);

INSERT INTO categories (title)
VALUES ('Software'),
       ('Books');

INSERT INTO products_categories
VALUES (1, 1),
       (2, 1),
       (3, 1);

COMMIT;