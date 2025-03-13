CREATE TABLE `booking` (
    uuid        VARCHAR(36)  NOT NULL,
    user_id     VARCHAR(36)  NOT NULL,
    guests      INT          NOT NULL CHECK (guests > 0),
    date        DATE         NOT NULL,
    time        TIME         NOT NULL,
    status      VARCHAR(50)  NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED')),
    created_at  DATETIME     NOT NULL DEFAULT NOW(),
    updated_at  DATETIME     NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    CONSTRAINT pk_booking PRIMARY KEY (uuid),
    CONSTRAINT FK_BOOKING_USER FOREIGN KEY (user_id) REFERENCES `user` (uuid)
);