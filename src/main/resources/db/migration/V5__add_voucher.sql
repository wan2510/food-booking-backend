CREATE TABLE voucher (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    discount_percentage INT NOT NULL,
    max_discount_value INT NOT NULL,
    min_order_value INT NOT NULL,
    expired_at DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    type VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

INSERT INTO voucher (id, code, name, discount_percentage, max_discount_value, min_order_value, expired_at, status, type, created_at, updated_at)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'VOUCHER1', 'Giảm giá 10%', 10, 50000, 100000, '2025-12-31 23:59:59', 'ACTIVE', 'FOR_ALL_USERS', '2023-10-01 00:00:00', '2023-10-01 00:00:00');