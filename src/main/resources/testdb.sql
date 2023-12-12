-- Заполнение таблицы users тестовыми данными
INSERT INTO `kata`.`users` (`id`, `age`, `e_mail`, `name`, `nickname`, `password`, `surname`)
VALUES
    ('1', '52', 'elonmusk@mail.ru', 'Илон', 'SpaceXman', '$2a$12$YphQMA.TDfcN3S37sVpeAuEsxO1RhhbXsMQMJeMdTUyprZsqqxALe', 'Маск'),
    ('2', '56', 'stevejobs@mail.ru', 'Стив', 'appleEater', '$2a$12$8Nmw.y04PikdcDkXx5NIUeJc.ZD5rIc2fVxoZ7Vn1czykG4nM1i5i', 'Джобс'),
    ('3', '39', 'paveldurov@mail.ru', 'Павел', 'durovVerniSteny', '$2a$12$t66btehYOUCHTKuvDfQpnubqaAYLvrOceez0P6c4T.oS2FbG3HoMS', 'Дуров');

-- Заполнение таблицы role тестовыми данными
INSERT INTO `kata`.`role` (`id`, `name`)
VALUES
    ('1', 'ROLE_USER'),
    ('2', 'ROLE_ADMIN');

-- Заполнение таблицы users_roles тестовыми данными
INSERT INTO `kata`.`users_roles` (`user_id`, `role_id`)
VALUES
    ('1', '1'),
    ('1', '2'),
    ('2', '1'),
    ('3', '1');

-- Заполнение таблицы task тестовыми данными
INSERT INTO `kata`.`task` (`id`, `description`, `header`, `priority`, `status`)
VALUES
    ('1', 'Покоряем Космос', 'Полететь на Марс', 'LOW', 'WAITING'),
    ('2', 'Новый год - новый IPhone', 'Изобрести IPhone', 'MEDIUM', 'IN_PROGRESS'),
    ('3', 'Для Роскомнадзора', 'Передать ключи шифрования', 'HIGH', 'COMPLETE');

-- Заполнение таблицы tasks_authors тестовыми данными
INSERT INTO `kata`.`tasks_authors` (`user_id`, `task_id`)
VALUES
    ('2', '1'),
    ('3', '2'),
    ('1', '3');

-- Заполнение таблицы tasks_employees тестовыми данными
INSERT INTO `kata`.`tasks_employees` (`user_id`, `task_id`)
VALUES
    ('1', '1'),
    ('2', '2'),
    ('3', '3');
