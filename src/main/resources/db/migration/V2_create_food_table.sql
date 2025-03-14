CREATE TABLE food (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    rating INT CHECK (rating BETWEEN 0 AND 5)
);
