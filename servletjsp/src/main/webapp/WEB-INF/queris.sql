CREATE TABLE users_list(
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         login VARCHAR(100) NOT NULL UNIQUE,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         create_date VARCHAR(50) NOT NULL
);