CREATE TABLE voucher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,       -- Mã voucher
    name VARCHAR(255) NOT NULL,             -- Tên voucher
    discount_percentage INT NOT NULL,       -- Giảm giá (%)
    max_discount_amount DECIMAL(10,2),      -- Giảm tối đa (VND)
    min_order_value DECIMAL(10,2),          -- Mức tối thiểu (VND)
    created_at DATE NOT NULL,               -- Ngày tạo
    expiry_date DATE NOT NULL,              -- Ngày hết hạn
    type VARCHAR(50) NOT NULL,              -- Loại voucher
    status ENUM('Khả dụng', 'Không khả dụng') NOT NULL -- Trạng thái
);

INSERT INTO voucher (code, name, discount_percentage, max_discount_amount, min_order_value, created_at, expiry_date, type, status)
VALUES
('WELCOME10', 'Chào mừng người mới', 10, 50000, 200000, '2024-01-01', '2025-01-01', 'cho người mới', 'Khả dụng'),
('VIP20', 'Ưu đãi khách VIP', 20, 100000, 500000, '2024-02-01', '2024-12-01', 'cho khách vip', 'Khả dụng'),
('SUMMER15', 'Giảm giá mùa hè', 15, 75000, 300000, '2024-06-01', '2024-10-01', 'cho người dùng', 'Không khả dụng');
