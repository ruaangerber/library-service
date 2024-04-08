# Library Service
### Overview
This is a demo library service used to save, read, update, and delete books from a library. It also provides free-text search functionality.

### Installation
1. Clone the project.
2. Create a new bucket in Couchbase called `library`.
3. Run `./gradlew build` to execute tests and build the artifact.
4. Run the following command and replace `<BUCKET_PASSWORD>` with the password to your Couchbase database: 
`java -DBUCKET_PASSWORD="<BUCKET_PASSWORD>" -jar ./build/libs/library-service-0.0.1-SNAPSHOT.jar`
5. Use a REST client such as Postman to call the endpoints.

### Endpoints
Visit the OpenAPI webpage at `http://localhost:8080/swagger-ui/index.html` after starting the application locally to see the full documentation.

#### POST /
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

#### GET /
This endpoint returns a book by looking for it with its ISBN.

Example:
```
curl --location --request GET 'http://localhost:8080/book?isbn=125'
```

#### PUT /
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

#### DELETE /
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
