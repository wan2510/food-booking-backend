
CREATE TABLE IF NOT EXISTS voucher (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount INT NOT NULL,
    max_discount_value INT NOT NULL,
    min_order_value INT NOT NULL,
    expired_at TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


INSERT INTO voucher (id, name, code, discount, max_discount_value, min_order_value, expired_at, status, type)
VALUES
    ('1', 'Khách hàng mới', 'NEW2025', 10, 50000, 200000, '2025-12-31 23:59:59', 'KHẢ_DỤNG', 'FOR_NEW_USERS'),
    ('2', 'Khách hàng VIP', 'VIP999', 20, 100000, 300000, '2025-12-31 23:59:59', 'KHẢ_DỤNG', 'FOR_VIP_USERS');
