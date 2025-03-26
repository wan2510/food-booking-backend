CREATE TABLE `role`
(
    uuid          VARCHAR(36)  NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    created_at    datetime     NOT NULL,
    updated_at     datetime     NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (uuid)
);

CREATE TABLE user
(
    uuid          VARCHAR(36)  NOT NULL,
    email         VARCHAR(255) NOT NULL,
    hash_password VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    phone         VARCHAR(15)  NOT NULL,
    status        VARCHAR(255) NULL,
    avatar_url    VARCHAR(255) NULL,
    role_id       VARCHAR(36)  NOT NULL,
    created_at    datetime     NOT NULL,
    updated_at     datetime     NOT NULL,
    deleted_ad     datetime     NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (uuid)
);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_phone UNIQUE (phone);

ALTER TABLE user
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (uuid);

INSERT INTO `role` (uuid, name, description, created_at, update_at)
VALUES
    ("1827d8b1-9c3f-4aa9-9a0d-fa78503e0a6f", "ROLE_USER", null, NOW(), NOW()),
    ("d69b211a-d6b2-4d30-a0b9-b3f714175f3d", "ROLE_ADMIN", null, NOW(), NOW());