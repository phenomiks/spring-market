# Spring Market

Интернет-магазин [spring-market.ru](http://spring-market.ru)

![Spring Market Example](./assets/spring_market.gif)

## Описание

"Spring Market" - проект простого интернет-магазина, относящийся к категории RDD (resume-driven development).

В качестве демонстрации проект развернут на облачном сервере и доступен по адресу [spring-market.ru](http://spring-market.ru) .
Для входа воспользуйтесь тестовым пользователем логин/пароль = user/user.

"Spring Market" имеет следующие особенности:
1. Spring AOP, AspectJ: логирование вызова методов и создания токена для контроллеров "ProductController" и "AuthController" соответственно с использованием
   аспектно-ориентированного программирования (АОП);
2. Spring Security, JWT: авторизация пользователей происходит при помощи JWT токенов;
3. RabbitMQ: для отправки сформированных заказов стороннему сервису ("amqp_consumer").
   Модуль "amqp_consumer" предназначен для принятия сформированных заказов и "последующей их обработки".
   В качестве ответа "amqp_consumer" отправляет "Spring Market" сообщение об удачном получении заказа;
4. Redis, PostgreSQL, H2 Database, Flyway.
   * Redis: хранение корзины пользователя (ее состояния);
   * PostgreSQL: хранение основной базы данных (БД) (товаров, заказов, пользователей);
   * H2 Database: для проведения тестирования используется in-memory копия основной БД;
   * Flyway: контроль версии базы данных.
5. Swagger: задокументировано REST API контроллера "ProductController";
6. JUnit, Mockito: проведено модульное и интеграционное тестирование некоторых компонентов проекта;
7. REST, SOAP, JAXB.
   * REST: основной протокол для взаимодействия и обмена данными с сервером;
   * SOAP: дополнительно добавлен SOAP протокол для получения всех товаров или товара по идентификатору;
   * JAXB: генерация классов на основе XML Schema (XSD).
8. DTO: возврат DTO сущности в формате JSON в качестве ответа сервера;
9. Exception Handling: реализована обработка исключений с помощью Spring для REST API;
10. Spring Data Specifications: реализация фильтра по названию, минимальной и максимальной цене через
    фильтрацию выборки из базы при помощи спецификаций.

### Выполнение SOAP-запросов

1. Запрос для получения описания веб-сервиса (WSDL):
   ```
   http://localhost:8081/app/ws/products.wsdl
   ```
2. Запрос для получения списка всех товаров по адресу [localhost:8081/app/ws](http://localhost:8081/app/ws)
   с телом:
   ```xml
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.ru/springmarket/ws/products">
       <soapenv:Header/>
       <soapenv:Body>
           <f:getAllProductsRequest/>
       </soapenv:Body>
   </soapenv:Envelope>
   ```
3. Запрос для получения товара по идентификатору по адресу [localhost:8081/app/ws](http://localhost:8081/app/ws)
   с телом:
   ```xml
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.ru/springmarket/ws/products">
       <soapenv:Header/>
       <soapenv:Body>
            <f:getProductByIdRequest>
                <f:id>{id}</f:id>
            </f:getProductByIdRequest>
       </soapenv:Body>
   </soapenv:Envelope>
   ```
   где {id} - идентификатор товара.

## Установка

Для запуска проекта выполните следующие действия:
1. Клонируйте проект:
   ```
   $ git clone https://github.com/phenomiks/spring-market.git
   ```
2. Установите Docker и PostgreSQL.
3. Разверните Redis в контейнере Docker командой:
   ```
   docker run --name redis -d -p 6379:6379 redis
   ```
4. Разверните RabbitMQ в контейнере Docker командой:
   ```
   docker run -d --hostname rabbitmq -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management
   ```   
5. Создайте в PostgreSQL базу данных (БД) с названием "spring_market":
   ```   
   sudo -u postgres psql
   CREATE DATABASE spring_market;
   ```   
6. Создайте файл "private.properties" в папке проекта "./src/main/resources" со следующими полями:
   ```
   spring.datasource.username=[username]
   spring.datasource.password=[password]
   jwt.secret=[secret]
   ```
   где 
   - в поле "spring.datasource.username" указывается username владельца БД "spring_market" (например, postgres);
   - в поле "spring.datasource.password" - пароль соответствующего владельца;
   - в поле "jwt.secret" - секретное слово для создания JWT токена (например, AScxvxcas89900xcv456fgbcv72qASsxzQqc).
7. Соберите проект:
   ```
   mvn clean install
   ```
   7.1. (По желанию) Для модуля "amqp_consumer" выполните аналогичную команду.
8. Запустите проект:
   ```
   java -jar spring-market-1.0.0.jar
   ```
   8.1. (По желанию) Для запуска модуля "amqp_consumer" выполните:
   ```
   java -jar amqp_consumer-0.0.1-SNAPSHOT.jar
   ```
9. *Дополнительно возможно развернуть фронт по следующей инструкции (в работе).*

## Лицензия

[MIT](./LICENSE)

<br>

---


#### Используемый стек технологий:

---

Java 11, Spring Boot, Spring Web MVC, Spring Security, JWT, Spring Data JPA, Redis, PostgreSQL, H2 Database, Flyway,
Spring AOP, AspectJ, RabbitMQ, Swagger, JUnit, Mockito, JAXB, Maven, Lombok