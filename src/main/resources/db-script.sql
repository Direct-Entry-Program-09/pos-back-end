CREATE TABLE customer(
                         id VARCHAR(36) PRIMARY KEY ,
                         name VARCHAR(50) NOT NULL ,
                         address VARCHAR(200) NOT NULL
);

INSERT INTO customer (id, name, address) VALUES (UUID(),'nimal','panadura'),
                                                (UUID(),'kamal','galle'),
                                                (UUID(),'sunil','colombo'),
                                                (UUID(),'ruwan','matara'),
                                                (UUID(),'nuwan','kalutara');

CREATE TABLE item( code VARCHAR(36) PRIMARY KEY ,
                   description VARCHAR(150) NOT NULL ,
                   unit_price DECIMAL(5,2) NOT NULL,
                   stock INTEGER (10) NOT NULL

);

INSERT INTO item (code, description, unit_price, stock) VALUES (UUID(),'Soap','200',950),
                                                               (UUID(),'Milk','780',500),
                                                               (UUID(),'Chocolate Biscuits-400g','480',750),
                                                               (UUID(),'Sugar-1kg','280',50);
