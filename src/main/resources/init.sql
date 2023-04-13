CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price INT NOT NULL,
                          quantity INT NOT NULL
);

CREATE TABLE discount_cards (
                                id SERIAL PRIMARY KEY,
                                number VARCHAR(255) NOT NULL,
                                discount_percentage INT NOT NULL
);

CREATE TABLE receipts (
                          id SERIAL PRIMARY KEY,
                          discount_card_id INT,
                          FOREIGN KEY (discount_card_id) REFERENCES discount_cards(id)
);

CREATE TABLE receipt_products (
                                  receipt_id INTEGER NOT NULL,
                                  product_id INTEGER NOT NULL,
                                  quantity INTEGER NOT NULL,
                                  PRIMARY KEY (receipt_id, product_id),
                                  FOREIGN KEY (receipt_id) REFERENCES receipts(id),
                                  FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO products (name, price, quantity)
VALUES ('Product 1', 10, 100),
       ('Product 2', 20, 200),
       ('Product 3', 30, 300),
       ('Product 4', 40, 400);

INSERT INTO discount_cards (number, discount_percentage)
VALUES ('card-1234', 10),
       ('card-2345', 20),
       ('card-3456', 30);

INSERT INTO receipts (total_amount, discount_card_id)
VALUES (100, NULL),
       (200, 1),
       (300, 2),
       (400, 3);

INSERT INTO receipt_products (receipt_id, product_id, quantity)
VALUES (1, 1, 10),
       (1, 2, 20),
       (2, 2, 30),
       (2, 3, 40),
       (3, 3, 50),
       (3, 4, 60),
       (4, 4, 70),
       (4, 1, 80);