# Дипломный проект профессии "Тестировщик ПО" #
___
### Необходимое окружение: ###
1. Установленая IntelliJ IDEA;
2. Установленный Docker;
3. Установленный Node.js;
4. Java 11;
5. Браузер;

### Запуск приложения: ###
1. Склонировать репозиторий: https://github.com/kokanoka/Diploma;
2. Запустить Docker контейнер ```docker-compose up -d```
3. Запустить SUT командой:
 - Для использования Postgres: ```java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -jar artifacts/aqa-shop.jar```
 - для использования MySQL: ```java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar```
4. Запутить тесты командой:
 - Для использования Postgres: ```gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/postgres -Dlogin=app -Dpassword=pass -Dapp.url=http://localhost:8080```
 - Для использования MySQL: ```gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app -Dlogin=app -Dpassword=pass -Dapp.url=http://localhost:8080```
5. Остановить SUT ```CTRL + C```
6. Остановить контейнеры командой ```docker-compose stop``` , удалить контейнеры командой ```docker-compose down```
