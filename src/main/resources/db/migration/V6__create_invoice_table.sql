CREATE TABLE invoices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL
);