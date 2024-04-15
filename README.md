# Library Service
### Overview
This is a demo library service used to save, read, update, and delete books from a library. It also provides free-text search functionality.

### Installation
1. Clone the project.
2. Run `docker-compose up` to spin up the service as well as a Couchbase instance.
3. The service will raise connection exceptions while couchbase is being initialised. Give it a minute.
4. The following log entry indicates that Couchbase is initialised and we were able to connect: 
`[com.couchbase.core][BucketOpenedEvent][10s] Opened bucket "library"`
5. The following log entry indicates that the Couchbase initialiser service exited correctly and we can now use the service:
`couchbase-server-init exited with code 0`
6. Use a REST client such as Postman to call the endpoints.
7. Press `Ctrl + C` and run `docker-compose down` to stop the service and database.

### Endpoints
Visit the OpenAPI webpage at `http://localhost:8080/swagger-ui/index.html` after starting the application locally to see the full documentation.

#### POST /book
This endpoint creates a new book.

Example:
```
curl --location --request POST 'http://localhost:8080/book' \
--header 'Content-Type: application/json' \
--data-raw '{
    "isbn": "128",
    "title": "hello world!",
    "description": "a random book",
    "authors": [
        {
            "name": "john doe"
        }
    ],
    "publishYear": "2024"
}'
```

#### GET /book
This endpoint returns a book by looking for it with its ISBN.

Example:
```
curl --location --request GET 'http://localhost:8080/book?isbn=125'
```

#### PUT /book
This endpoint updates an existing book. Provide all the fields as this will overwrite all fields.

Example:
```
curl --location --request PUT 'http://localhost:8080/book' \
--header 'Content-Type: application/json' \
--data-raw '{
    "isbn": "125",
    "title": "hello world!3",
    "description": "a random book",
    "authors": [
        {
            "name": "john doe"
        }
    ],
    "publishYear": 2024
}'
```

#### DELETE /book
This endpoint deletes an existing book by looking for it with its ISBN.

Example:
```
curl --location --request DELETE 'http://localhost:8080/book?isbn=125'
```

#### GET /search
This endpoint searches for books by using free text.

Example:
```
curl --location --request GET 'http://localhost:8080/book/search?query=2024&page=0&size=1'
```
