CREATE TABLE customer
(
    id      varchar(36) primary key,
    name    varchar(50)  not null,
    address varchar(200) not null
);
INSERT INTO customer (id, name, address)
VALUES (UUID(), 'nimal', 'panadura'),
       (UUID(), 'kamal', 'galle'),
       (UUID(), 'sunil', 'colombo'),
       (UUID(), 'ruwan', 'matara'),
       (UUID(), 'nuwan', 'kalutara');

CREATE TABLE `order`
(
    id         VARCHAR(36) PRIMARY KEY,
    date       DATE        NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE item
(
    code        VARCHAR(36) PRIMARY KEY,
    description VARCHAR(150)  NOT NULL,
    unit_price  DECIMAL(5, 2) NOT NULL,
    stock       INTEGER(10)   NOT NULL
);

CREATE TABLE orderDetails
(
    order_id   VARCHAR(30),
    item_code  VARCHAR(36)   NOT NULL,
    qty        INT(10)       NOT NULL,
    unit_Price DECIMAL(5, 2) NOT NULL,
    PRIMARY KEY (order_id, item_code),
    FOREIGN KEY (item_code) REFERENCES item (code),
    FOREIGN KEY (order_id) REFERENCES `order` (id)
);

