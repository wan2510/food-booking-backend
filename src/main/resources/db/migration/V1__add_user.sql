CREATE TABLE `role`
(
    uuid          VARCHAR(36)  NOT NULL,
    name          VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    created_at    datetime     NOT NULL,
    update_at     datetime     NOT NULL,
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
    update_at     datetime     NOT NULL,
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