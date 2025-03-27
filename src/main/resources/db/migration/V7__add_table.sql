CREATE TABLE `table_restaurant` (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    table_number  VARCHAR(10)  NOT NULL UNIQUE,
    capacity      INT          NOT NULL CHECK (capacity > 0),
    booked_guests INT          NOT NULL DEFAULT 0 CHECK (booked_guests >= 0),
    status        VARCHAR(20)  NOT NULL DEFAULT 'available' CHECK (status IN ('available', 'unavailable')),
    type          VARCHAR(50)  NOT NULL CHECK (type IN ('bàn thường', 'bàn vip', 'bàn gia đình', 'bàn ngoài trời'))
);