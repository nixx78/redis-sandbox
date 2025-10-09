# Проект песочница Redis  

## Redis management console
* URL http://127.0.0.1:5540
* Connection to DB, host should be like 192.. :6379

## Application Endpoints
 
* Application swagger endpoint: http://localhost:8080/swagger-ui.html
* Redis Management

## Основные примеры использования - Spring Boot Starter Redis

### CRUD Операции
* Использование связанных объектов User(1) -> Role(n)
* Хранение данных в JSON (Picture)
* Получение данных при помощи Paging (UserPagingRepository)

### Операции поиска
* Поиск данных по полю (использование @Indexed)
* Получение suggestion по полю

## Работа с Redis используя библиотеку Jedis
* Операции с различными структурами данных
* Операции с Hash
* Операции с Stream
* Поиск по полям индекса

## Тестовые данные 
* test/resources

# RediSearch

## Работа с индексами

### Создание индексов
FT.CREATE photo-idx ON HASH PREFIX 1 "photo:" SCHEMA filename TEXT genre TAG keywords TAG SEPARATOR "," place TEXT rating NUMERIC SORTABLE captureDateTime NUMERIC SORTABLE

### Список индексов
FT._LIST
### Посмотреть схему индекса:
FT.INFO photo-idx

## Пример поиска по индексированным полям
* Используя команду: FT.SEARCH photo-idx "@genre:{NATURE}"
* Из Java code: PhotoSearchEngine





