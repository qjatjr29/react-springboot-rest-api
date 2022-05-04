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
    type         VARCHAR(50)  NOT NULL,
    price        bigint       NOT NULL,
    image        VARCHAR(500) DEFAULT NULL,
    description  VARCHAR(500) DEFAULT NULL,
    store_id     BINARY(16)   NOT NULL,
    CONSTRAINT fk_food_store FOREIGN KEY (store_id) REFERENCES stores (store_id) on DELETE CASCADE
);

CREATE TABLE orders
(
    order_id    binary(16)   PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL,
    address     VARCHAR(200) NOT NULL,
    phone_number    VARCHAR(200) NOT NULL,
    order_status VARCHAR(50)  NOT NULL,
    price       bigint      NOT NULL,
    created_at  datetime(6)  NOT NULL,
    updated_at  datetime(6)  DEFAULT NULL
);


CREATE TABLE order_lists
(
    order_list_id    binary(16)   PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL,
    address     VARCHAR(200) NOT NULL,
    phone_number    VARCHAR(200) NOT NULL
);

CREATE TABLE order_items
(
    seq        bigint       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   binary(16)   NOT NULL,
    food_id binary(16)   NOT NULL,
    category   VARCHAR(50)  NOT NULL,
    price      bigint       NOT NULL,
    quantity   int          NOT NULL,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_food FOREIGN KEY (food_id) REFERENCES foods (food_id)
);
