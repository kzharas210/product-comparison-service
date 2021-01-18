DROP TABLE IF EXISTS provider_products;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS provider_rankings;
DROP TABLE IF EXISTS providers;

CREATE TABLE providers
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE provider_rankings
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    ranking     DECIMAL NOT NULL,
    provider_id INT     NOT NULL UNIQUE,
    FOREIGN KEY (provider_id) REFERENCES providers (id)
);

CREATE TABLE products
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(250) NOT NULL,
    category VARCHAR(250) NOT NULL,
    UNIQUE (name, category)
);

CREATE TABLE provider_products
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    provider_id INT     NOT NULL REFERENCES providers (id),
    product_id  INT     NOT NULL REFERENCES products (id),
    price       DECIMAL NOT NULL,
    UNIQUE (provider_id, product_id)
);