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