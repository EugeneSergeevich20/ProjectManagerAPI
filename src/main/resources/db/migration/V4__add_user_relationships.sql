DELETE FROM projects;

ALTER TABLE projects ADD COLUMN owner_id UUID NOT NULL;

ALTER TABLE projects ADD CONSTRAINT fk_projects_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE;

CREATE TABLE project_users (
                               project_id UUID NOT NULL,
                               user_id UUID NOT NULL,
                               PRIMARY KEY (project_id, user_id),
                               CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                               CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE tasks ADD COLUMN assignee_id UUID;

ALTER TABLE tasks ADD CONSTRAINT fk_tasks_assignee FOREIGN KEY (assignee_id) REFERENCES users(id) ON DELETE SET NULL;
