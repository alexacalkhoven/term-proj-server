CREATE TABLE IF NOT EXISTS students (
    id INT NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    UNIQUE (name, number)
););

CREATE TABLE IF NOT EXISTS prerequisites (
    parent_id INT NOT NULL,
    child_id INT NOT NULL,
    UNIQUE (parent_id, child_id)
);

CREATE TABLE IF NOT EXISTS registrations ( 
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    student_id INT NOT NULL,
    offering_id INT NOT NULL,
    grade CHAR(1) NOT NULL,
    UNIQUE (student_id, offering_id)
);

CREATE TABLE IF NOT EXISTS offerings (
    id INT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    number INT NOT NULL,
    capacity INT NOT NULL,
    students INT NOT NULL,
    course_id INT NOT NULL,
    UNIQUE (course_id, number)
);