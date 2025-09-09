
-- 1. USERS table


CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255),
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255)
) ENGINE=InnoDB;

-- 2. CATEGORY table
CREATE TABLE category (
                          id TINYINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255)
) ENGINE=InnoDB;

-- 3. PRODUCT table (depends on category)
CREATE TABLE product (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255),
                         description TEXT,
                         price DECIMAL(19, 2),
                         category_id TINYINT,
                         CONSTRAINT fk_product_category FOREIGN KEY (category_id)
                             REFERENCES category(id)
                             ON DELETE SET NULL
) ENGINE=InnoDB;

-- 4. ADDRESS table (depends on users)
CREATE TABLE address (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         street VARCHAR(255),
                         city VARCHAR(255),
                         zip VARCHAR(20),
                         state VARCHAR(255),
                         user_id BIGINT,
                         CONSTRAINT fk_address_user FOREIGN KEY (user_id)
                             REFERENCES users(id)
                             ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5. PROFILE table (depends on users, one-to-one)
CREATE TABLE profile (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         bio TEXT,
                         profile_picture VARCHAR(255),
                         user_id BIGINT UNIQUE,
                         CONSTRAINT fk_profile_user FOREIGN KEY (user_id)
                             REFERENCES users(id)
                             ON DELETE CASCADE
) ENGINE=InnoDB;

-- 6. WISHLIST table (many-to-many between users and products)
CREATE TABLE wishlist (
                          user_id BIGINT,
                          product_id BIGINT,
                          PRIMARY KEY (user_id, product_id),
                          CONSTRAINT fk_wishlist_user FOREIGN KEY (user_id)
                              REFERENCES users(id)
                              ON DELETE CASCADE,
                          CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id)
                              REFERENCES product(id)
                              ON DELETE CASCADE
) ENGINE=InnoDB;
