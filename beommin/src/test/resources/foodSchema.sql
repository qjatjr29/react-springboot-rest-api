CREATE TABLE foods
(
    food_id      BINARY(16)   PRIMARY KEY,
    name         VARCHAR(20)  NOT NULL,
    category     VARCHAR(50)  NOT NULL,
    type         VARCHAR(50)  NOT NULL,
    price        bigint       NOT NULL,
    description  VARCHAR(500) DEFAULT NULL
);