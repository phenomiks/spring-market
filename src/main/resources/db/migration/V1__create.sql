BEGIN TRANSACTION;

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(30) NOT NULL UNIQUE,
    password   VARCHAR(64) NOT NULL,
    email      VARCHAR(40) UNIQUE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE roles
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(40) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE users_roles
(
    user_id BIGINT  NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE products
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255)   NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE categories
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE products_categories
(
    product_id  BIGINT  NOT NULL,
    category_id INTEGER NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY,
    owner_id    BIGINT         NOT NULL REFERENCES users (id),
    total_price NUMERIC(10, 2) NOT NULL,
    address     VARCHAR(255),
    created_at  TIMESTAMP DEFAULT current_timestamp,
    updated_at  TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE orders_items
(
    id                BIGSERIAL PRIMARY KEY,
    order_id          BIGINT REFERENCES orders (id),
    product_id        BIGINT REFERENCES products (id),
    title             VARCHAR(255)   NOT NULL,
    quantity          INT            NOT NULL,
    price_per_product NUMERIC(10, 2) NOT NULL,
    created_at        TIMESTAMP DEFAULT current_timestamp,
    updated_at        TIMESTAMP DEFAULT current_timestamp
);

COMMIT;