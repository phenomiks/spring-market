BEGIN TRANSACTION;

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    price NUMERIC(7,2) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE products_categories (
    product_id BIGINT NOT NULL,
    category_id INTEGER NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    email VARCHAR(64) UNIQUE,
    enabled BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE reviews (
    product_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    review VARCHAR(1000),
    PRIMARY KEY (product_id, user_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    date TIMESTAMP DEFAULT current_timestamp,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orders_items (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(7,2) NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

COMMIT;