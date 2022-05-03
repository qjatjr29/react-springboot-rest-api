CREATE TABLE stores
(
    store_id      BINARY(16)   PRIMARY KEY,
    name         VARCHAR(20)  NOT NULL,
    address      VARCHAR(50)  NOT NULL,
    phone_number VARCHAR(50)  NOT NULL,
    image        VARCHAR(500) DEFAULT NULL,
    category     VARCHAR(50)  NOT NULL,
    created_at      datetime(6) DEFAULT CURRENT_TIMESTAMP(6)
);
CREATE TABLE foods
(
    food_id      BINARY(16)   PRIMARY KEY,
    name         VARCHAR(20)  NOT NULL,
    category     VARCHAR(50)  NOT NULL,
    sub_category VARCHAR(50)  NOT NULL,
    price        bigint       NOT NULL,
    image        VARCHAR(500) DEFAULT NULL,
    description  VARCHAR(500) DEFAULT NULL,
    store_id     BINARY(16)   NOT NULL,
    CONSTRAINT fk_food_store FOREIGN KEY (store_id) REFERENCES stores (store_id) on DELETE CASCADE
);
