CREATE TABLE Categories_TBL (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(20) NOT NULL,
                                CONSTRAINT CHK_name_length CHECK (LENGTH(name) BETWEEN 3 AND 20)
);