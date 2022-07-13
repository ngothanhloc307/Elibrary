DROP DATABASE library;
CREATE DATABASE library;
DROP TABLE library.ebook;
CREATE TABLE library.ebook (
  call_no VARCHAR(225) NOT NULL,
  name VARCHAR(225),
  author VARCHAR(225),
  publisher VARCHAR(225),
  quantity INT,
  issued INT,
  
  PRIMARY KEY (call_no)
);

DROP TABLE library.e_librarian;
CREATE TABLE library.e_librarian (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(300),
  password VARCHAR(300),
  email VARCHAR(300),
  mobile INT,
  PRIMARY KEY (id)
);

DROP TABLE library.e_issued_book;
CREATE TABLE library.e_issued_book (
  call_no VARCHAR(300),
  student_id VARCHAR(300),
  student_name VARCHAR(300),
  student_mobile LONG,
  issued_date date,
  return_status VARCHAR(300)
);

---------------------------------------------------------------------------
