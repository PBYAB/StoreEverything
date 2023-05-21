CREATE TABLE Informations_TBL (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  name VARCHAR(20) NOT NULL,
                                  description VARCHAR(500) NOT NULL,
                                  category VARCHAR(20) NOT NULL,
                                  creationTime DATETIME NOT NULL,
                                  CONSTRAINT CHK_name_length CHECK (LENGTH(name) BETWEEN 3 AND 20),
                                  CONSTRAINT CHK_description_length CHECK (LENGTH(description) BETWEEN 5 AND 500),
                                  CONSTRAINT CHK_category_lowercase CHECK (category = LOWER(category))
);
