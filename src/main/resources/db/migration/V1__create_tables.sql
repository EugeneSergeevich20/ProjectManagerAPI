CREATE TABLE projects (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name VARCHAR(255) NOT NULL UNIQUE,
                          description TEXT,
                          status VARCHAR(50) NOT NULL
);

CREATE TABLE tasks (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       project_id UUID NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
                       status VARCHAR(50) NOT NULL
);

CREATE TABLE tags (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE task_tags (
                           task_id UUID NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
                           tag_id UUID NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
                           PRIMARY KEY (task_id, tag_id)
);
