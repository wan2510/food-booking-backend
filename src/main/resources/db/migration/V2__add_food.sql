CREATE TABLE `category` 
(
    uuid        VARCHAR(36)  NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NULL,
    created_at  datetime     NOT NULL,
    updated_at  datetime     NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (uuid)
);

CREATE TABLE `food`
(
    uuid        VARCHAR(36)  NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NULL,
    status      VARCHAR(50)  NOT NULL,
    category_id VARCHAR(36)  NOT NULL,
    image_url   VARCHAR(255) NULL,
    price       DECIMAL(10,2) NOT NULL,
    created_at  datetime     NOT NULL,
    updated_at  datetime     NOT NULL,
    CONSTRAINT pk_food PRIMARY KEY (uuid)
);

ALTER TABLE `category`
    ADD CONSTRAINT uc_category_name UNIQUE (name);

ALTER TABLE `food`
    ADD CONSTRAINT FK_FOOD_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES `category` (uuid);