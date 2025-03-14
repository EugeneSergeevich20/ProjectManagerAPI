CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(80) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE roles (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles (
                             user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                             PRIMARY KEY (user_id, role_id)
);