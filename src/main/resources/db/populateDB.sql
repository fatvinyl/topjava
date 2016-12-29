DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2016-12-29 04:00:16.568217', 'завтрак', 350, 100000),
  ('2016-12-30 04:00:16.568217', 'обед', 550, 100000),
  ('2016-12-31 04:00:16.568217', 'ужин', 550, 100000);

INSERT INTO meals (date_time,description, calories, user_id) VALUES
  ('2016-12-29 04:00:16.568217', 'завтрак', 400, 100001),
  ('2016-12-30 04:00:16.568217', 'обед', 500, 100001),
  ('2016-12-31 04:00:16.568217', 'ужин', 600, 100001);

