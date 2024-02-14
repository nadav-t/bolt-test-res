# Server A: User Management

### Functionality
Server A is responsible for managing user-related operations. It provides the following functionalities:

- Create User: Allows the creation of a new user by providing user details.
- Get User: Retrieves user information based on the provided user ID.
- Update User: Updates user information based on the provided user ID and new data.
- Delete User: Deletes a user based on the provided user ID.

### Additional Features to Implement
- Pagination: Implement pagination for listing users in case the number of users is large.
- Exception Handling: Enhance exception handling to provide meaningful error messages to clients.
- Logging: Improve logging throughout the application for better debugging and monitoring.
- Tests: Add tests to all methods and flows.
- Dynamic environment: support environments dynamically.

### build
`docker build -t server-a .`

### run
`docker run -p 8081:8081 server-a`

### Full project build and run
- For running th full project please run the docker-compose.yml file
- It will create server A, server B and DB.
- `docker-compose build`
- `docker-compose up`


