 USE db_jpa_crud_security

CREATE TABLE `db_jpa_crud_security`.`products` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `price` INT NOT NULL,
    `description` TEXT NOT NULL,
    `sku` VARCHAR(45),
    PRIMARY KEY (`id`)
);

CREATE TABLE `db_jpa_crud_security`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(20) NOT NULL UNIQUE,
    `password` VARCHAR(60) NOT NULL,
    `enabled` TINYINT DEFAULT 1 NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `db_jpa_crud_security`.`roles` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `db_jpa_crud_security`.`users_roles` (
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `db_jpa_crud_security`.`users` (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `db_jpa_crud_security`.`roles` (`id`)
);