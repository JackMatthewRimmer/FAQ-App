DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS assigned_questions;

CREATE TABLE accounts (
    accounts_id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE questions (
    questions_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL
);

CREATE TABLE assigned_questions (
    assigned_questions_id SERIAL PRIMARY KEY,
    accounts_id INTEGER,
    questions_id INTEGER,
    FOREIGN KEY (accounts_id) REFERENCES accounts(accounts_id),
    FOREIGN KEY (questions_id) REFERENCES questions(questions_id)
);