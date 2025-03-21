CREATE TABLE voucher (
    uuid VARCHAR(36) PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,       -- Mã voucher
    name VARCHAR(255) NOT NULL,             -- Tên voucher
    discount_percentage INT NOT NULL,       -- Giảm giá (%)
    max_discount_amount DECIMAL(10,2),      -- Giảm tối đa (VND)
    min_order_value DECIMAL(10,2),          -- Mức tối thiểu (VND)
    created_at datetime NOT NULL,               -- Ngày tạo
    expired_date datetime NOT NULL,              -- Ngày hết hạn
    type VARCHAR(50) NOT NULL,              -- Loại voucher
    status ENUM('Khả dụng', 'Không khả dụng') NOT NULL, -- Trạng thái
    updated_at datetime NOT NULL
);