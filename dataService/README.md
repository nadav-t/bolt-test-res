# Server B: User Data Storage

## Functionality
Server B is responsible for storing and retrieving user information from a database. It provides the following functionalities:

- Store User: Persists user information in the PostgreSQL database.
- Retrieve User: Fetches user information from the database based on the provided user ID.
- Support for PostgresDB: Utilizes Spring Data for PostgreSQL integration.

### Additional Features to Implement
- Circuit Breaking: Consider implementing circuit breaking for database calls to handle failures gracefully.
- Exception Handling: Enhance exception handling to provide meaningful error messages to clients.
- Logging: Improve logging throughout the application for better debugging and monitoring.
- Tests: Add tests to all methods and flows.
- Dynamic environment: support environments dynamically.

### build
`docker build -t server-b .`

### run
`docker run -p 8081:8082 server-a`