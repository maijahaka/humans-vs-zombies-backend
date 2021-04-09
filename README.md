# Humans vs. Zombies

Humans vs. Zombies (HvZ) is a game of tag played at schools, camps, neighborhoods, libraries, and conventions around the world. The game simulates the exponential spread of a fictional zombie infection through a population. This application creates the base for the games to playing in the real world.

The frontend source code and more details about the application can be found at https://github.com/Satuhoo/humans-vs-zombies-frontend.

## Contributors

[Maija Haka](https://github.com/maijahaka), [Okko Partanen](https://github.com/okarp) and [Satu Heikkonen](https://github.com/Satuhoo)

## API endpoints

Endpoints are secured by using Spring Security.

### Game
-	Full CRUD 
-	GET statistics

### Player
-	Full CRUD 
-	GET logged player

### Kill
-	POST kill
-	GET all kills
-	GET kill by id

### Chat
-	POST message
-	GET all messages
-	GET all global messages

The postman collection can be found in the root folder.

## Limitations

The application does not contain rate limiting.

## Installation

See the **frontend** installation at https://github.com/Satuhoo/humans-vs-zombies-frontend

**Backend** installation

Clone the source code:
`git clone https://github.com/maijahaka/humans-vs-zombies-backend.git`

You need to set the following environment variables depending on your setup: 

POSTGRES_PORT, POSTGRES_DB_NAME, POSTGRES_USERNAME, POSTGRES_PASSWORD, KEYCLOAK_REALM, KEYCLOAK_AUTH_SERVER_URL, KEYCLOAK_RESOURCE, KEYCLOAK_CREDENTIALS_SECRET, KEYCLOAK_BEARER_ONLY, KEYCLOAK_USE_RESOURCE_ROLE_MAPPINGS, KEYCLOAK_SSL_REQUIRED, SERVER_PORT, ALLOWED_WEB_SOCKET_ORIGINS
