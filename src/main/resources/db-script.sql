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

CREATE TABLE item(
                         code VARCHAR(36) PRIMARY KEY ,
                         description VARCHAR(150) NOT NULL ,
                         unit_price DECIMAL(50) NOT NULL,
                         stock INT(50) NOT NULL
);