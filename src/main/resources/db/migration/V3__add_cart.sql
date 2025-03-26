CREATE TABLE `cart` (
    uuid       VARCHAR(36)  NOT NULL,
    user_id    VARCHAR(36)  NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    CONSTRAINT pk_cart PRIMARY KEY (uuid),
    CONSTRAINT FK_CART_USER FOREIGN KEY (user_id) REFERENCES `user` (uuid)
);

CREATE TABLE `cart_item` (
    uuid      VARCHAR(36)  NOT NULL,
    cart_id   VARCHAR(36)  NOT NULL,
    food_id   VARCHAR(36)  NOT NULL,
    quantity  INT          NOT NULL CHECK (quantity > 0),
    created_at DATETIME    NOT NULL,
    updated_at DATETIME    NOT NULL,
    CONSTRAINT pk_cart_item PRIMARY KEY (uuid),
    CONSTRAINT FK_CART_ITEM_CART FOREIGN KEY (cart_id) REFERENCES `cart` (uuid),
    CONSTRAINT FK_CART_ITEM_FOOD FOREIGN KEY (food_id) REFERENCES `food` (uuid)
);

CREATE TABLE `order` (
    uuid       VARCHAR(36)  NOT NULL,
    user_id    VARCHAR(36)  NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status     VARCHAR(50)  NOT NULL CHECK (status IN ('PENDING', 'COMPLETED', 'CANCELLED')),
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (uuid),
    CONSTRAINT FK_ORDER_USER FOREIGN KEY (user_id) REFERENCES `user` (uuid)
);

CREATE TABLE `order_item` (
    uuid       VARCHAR(36)  NOT NULL,
    order_id   VARCHAR(36)  NOT NULL,
    food_id    VARCHAR(36)  NOT NULL,
    quantity   INT          NOT NULL CHECK (quantity > 0),
    price      DECIMAL(10,2) NOT NULL,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (uuid),
    CONSTRAINT FK_ORDER_ITEM_ORDER FOREIGN KEY (order_id) REFERENCES `order` (uuid),
    CONSTRAINT FK_ORDER_ITEM_FOOD FOREIGN KEY (food_id) REFERENCES `food` (uuid)
);