### Summary

Приложение представляет собой простую систему управления задачами. 
Система обеспечивает создание, редактирование, удаление и просмотр задач. 

### Stack

Проект написан на базе `Java 17`, `Spring Boot 2`, `Maven` и архитектуре REST. 
Работа с базой данных `MySQL 8` с помощью `Spring Data JPA` и `Hibernate`.
Чтобы не писать boilerplate-код, на проекте используется `Lombok`.
Контроллеры и их методы описываются с помощью `Swagger`.

### Бэкенд

Пакеты: 

- config - Конфигурационные файлы для Spring Security
- controller - REST-контроллеры
- model - классы сущностей
- repository - dao-классы
- service - классы бизнес-логики, переиспользующие методы dao-классов


### Фронтенд

Фронтенд написан с помощью `Bootstrap`, `JavaScript` и `HTML`. 

Навигация:

1. `Панель управления` - позволяет создавать новых пользователей, редактировать/просматривать/удалять существующих (необходимо обладать правами администратора)
2. `Таскборд` - позволяет создавать новые задачи, редактировать/просматривать/удалять существующие
3. `Окно задачи` - открывается при нажатии на кнопку `Открыть` на `Таскборде`, в нем можно просматривать информацию и оставлять комментарии
4. `Профиль` - отображает информацию о текущем пользователе
5. `Swagger UI` - содержит описание всех контроллеров и их методов.

### Инструкция по запуску

1. На компьютере должен быть установлен MySQL 8 (ссылка на скачивание - https://dev.mysql.com/downloads/workbench/)
2. Приложение по умолчанию использует:

- Подключение по порту `localhost:3306`
- Название схемы `kata` 
- Имя пользователя `root`
- Пароль `root`

При желании эти параметры можно изменить в файле `application.properties` (spring.datasource.url, spring.datasource.username, spring.datasource.password)

Убедитесь, что схема и пользователь с параметрами из `application.properties` были созданы.

3. Запустите TaskManagerSystemApplication в среде разработки, создадутся таблицы
4. Заполните схему в MySQL тестовыми данными из файла `testdb.sql` (если меняли название схемы, то в запросах его тоже нужно поменять с `kata` на свою)
5. Откройте браузер и перейдите по адресу http://localhost:8080/login
6. Введите в форме для входа: 

- e-mail `elonmusk@mail.ru` и пароль `100` (для администратора)
ИЛИ
- e-mail `stevejobs@mail.ru` и пароль `200` (для обычного пользователя)
- e-mail `paveldurov@mail.ru` и пароль `300` (для обычного пользователя)

8. Готово! Можно пользоваться приложением! :) Если все настроили верно, то должны получиться страницы, как в папке `images`