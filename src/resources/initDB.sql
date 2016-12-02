DROP TABLE IF EXISTS products;

CREATE TABLE products
(
  id      BIGINT PRIMARY KEY NOT NULL,
  name    VARCHAR NOT NULL,
  price   DECIMAL NOT NULL,
  count   INTEGER NOT NULL
);

DELETE FROM products;

INSERT INTO products (id, name, price, count) VALUES
  (0, 'тетрадь в клетку', 2.5, 1000),
  (1, 'тетрадь в линейку', 2.5, 1000),
  (2, 'общая тетрадь', 25, 65),
  (3, 'тетрадка', 2, 600),
  (4, 'тетрадь с голограммой', 45, 80),
  (5, 'ручка', 5, 1000),
  (6, 'карандаш', 4, 100),
  (7, 'карандаш НВ', 4.8, 100),
  (8, 'карандаш СВ', 5.5, 200),
  (9, 'карандаш ННВ', 5.5, 500),
  (10, 'автоматический карандаш', 30, 15);