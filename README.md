# Humans vs. Zombies

Humans vs. Zombies (HvZ) is a game of tag played at schools, camps, neighborhoods, libraries, and conventions around the world. The game simulates the exponential spread of a fictional zombie infection through a population. This application creates the base for the games to playing in the real world.

The frontend source code and more details about the application can be found at https://github.com/Satuhoo/humans-vs-zombies-frontend.

## Keycloak instructions

The Java backend validates incoming API requests with Keycloaks JWT's. 
If you have docker installed a local Keycloak container can be started with a simple command:

```
docker run -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin quay.io/keycloak/keycloak:12.0.4
```

NOTE: This will start a Keycloak container running in your localhost port 8080. This might collide with Springs default port so you might have to adjust your ports. You can login to Keycloaks admin console with credentials 'admin / admin' in your localhost address after the container has spun up.

### Configurating Keycloak 

You must first create a new realm and add a new Bearer only client for the Java Spring application. The realm roles this application validates against is 'admin' and 'user', be sure to add them in Keycloak. 

As configuring a Keycloak instance is beyond the scope of this readme




## Contributors

[Maija Haka](https://github.com/maijahaka), [Okko Partanen](https://github.com/okarp) and [Satu Heikkonen](https://github.com/Satuhoo)

<details>
<summary>Open API documentation</summary>
## API endpoints


Endpoints are secured by using Spring Security. Below is a list of supported methods. A more detailed documentation with response examples can be found in the postman collection, located in the root folder.

### Game

All the endpoints return 404 if ID parameter does not match any game object in the databse.

-	Full CRUD 

GET/POST. POST requires Auth-header with JWT that has 'admin' role or else HTTP 401 / HTTP 403 is returned.
```
api/v1/games
```
PUT/DELETE. Both require Auth-header with JWT that has 'admin' role or else HTTP 401 / HTTP 403 is returned.

```
api/v1/games/{id}
```
GET statistics of a game. Game state must be 'COMPLETED' or else a HTTP 403 error is returned.
```
api/v1/games/{id}/statistics
```



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

</details>

## Limitations

The application does not contain rate limiting.

## Installation

See the **frontend** installation at https://github.com/Satuhoo/humans-vs-zombies-frontend

**Backend** installation

Clone the source code:
`git clone https://github.com/maijahaka/humans-vs-zombies-backend.git`

You need to set the following environment variables depending on your setup: 

POSTGRES_PORT, POSTGRES_DB_NAME, POSTGRES_USERNAME, POSTGRES_PASSWORD, KEYCLOAK_REALM, KEYCLOAK_AUTH_SERVER_URL, KEYCLOAK_RESOURCE, KEYCLOAK_CREDENTIALS_SECRET, KEYCLOAK_BEARER_ONLY, KEYCLOAK_USE_RESOURCE_ROLE_MAPPINGS, KEYCLOAK_SSL_REQUIRED, SERVER_PORT, ALLOWED_WEB_SOCKET_ORIGINS
