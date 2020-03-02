DROP TABLE IF EXISTS book;

CREATE TABLE book (
                       id INT AUTO_INCREMENT  PRIMARY KEY,
                       book_name VARCHAR(250) NOT NULL,
                       author_name VARCHAR(250) NOT NULL
);
