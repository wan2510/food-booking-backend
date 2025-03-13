CREATE TABLE restaurant_table (
    id INT PRIMARY KEY IDENTITY(1,1),
    number INT,
    description VARCHAR(MAX),
    status BIT DEFAULT 1,
    max_number_human INT,
    created_at DATETIME2,
    updated_at DATETIME2
);
