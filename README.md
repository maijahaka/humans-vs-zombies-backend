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
  
## About API


Endpoints are secured by using Spring Security. Below is a list of supported methods. A more detailed documentation with response examples can be found in the postman collection, located in the root folder.

If an endpoint that requires an token with 'admin' role is called without one either HTTP 403 is returned (if a token with 'user' role is sent) or HTTP 401 (if no token is supplied).

### Game

All the game endpoints return 404 if ID parameter does not match any game object in the database.


```
GET/POST: api/v1/games
```


POST adds new game to database. A name for the game must be provided in the body. 
Requires Auth-header with JWT that has 'admin' role or else HTTP 401 / HTTP 403 is returned.

Returns:

HTTP 400 if name is not unique.

Added game object if no errors occur.


```
GET/PUT/DELETE: api/v1/games/{id}
```


PUT accepts following parameters in the body: 'id', 'name', 'rules', 'description', 'gameState'. 

The gameState must be one of the enums 'REGISTRATION, IN_PROGRESS, COMPLETE'. 

Body id must match path id or else HTTP 403 is returned. If no token with 'admin' role is supplied, returns HTTP 403 / 401.


DELETE removes a game from the database. Requires Auth-header with JWT that has 'admin' role or else HTTP 401 / HTTP 403 is returned.


GET returns a single game object from the database. No authentication required. Returns 404 if a game with given id can't be found.


```
GET: api/v1/games/{id}/statistics
```


Returns statistics of a game object. 


Game state must be 'COMPLETED' or else a HTTP 403 error is returned.


### Player

```
GET: api/v1/games/{id}/currentplayer
```
Returns a single player object in the game associated with the tokens subject_id. Requires Auth-header with JWT token. 


Returns:

HTTP 401 if no token is sent.
HTTP 403 if user has no player object in the game. 


```
GET: api/v1/games/{id}/players
```


Returns a list of player objects in the game. The API is using dynamic projection to hide sensitive fields when the endpoint is called without an admin token. For example players 'is_patient_zero' state is not exposed to regular users.


```
GET/POST/PUT/DELETE: api/v1/games/{id}/players{id}
```


POST adds a player object to the game. 
Requires Auth-header with JWT token, adds the player to the game using JWT's subject_id field.
A playerName should be sent in the body of the request. 
Returns:
HTTP 400 if no game exists with given id.
HTTP 400 if user has already registered to the game.
The added player if no errors occur.

DELETE removes player object from the game. Requires Auth-header with JWT that has 'admin' role or else HTTP 401 / HTTP 403 is returned.
Returns HTTP 404 if player with given id is not found.
If deletion went through returns the deleted player object.

GET Returns a single player object in the game. The API is using dynamic projection to hide sensitive fields when the endpoint is called without an admin token. For example players 'is_patient_zero' state is not exposed to regular users.
Returns HTTP 404 if player with given id is not found.

PUT is unfortunately currently not working


### Kill

```
GET/POST: api/v1/games/{id}/kills
```


GET returns a list of kill objects in the game.

POST adds new kill object to the game. Request body should contain following fields: 'lat', 'lng', 'story', 'biteCode', 'id (killers id)'

Returns:

HTTP 403 if bite code is invalid or gameid is invalid.

HTTP 403 if a human tries to kill another human.

HTTP 403 if a zombie is being killed.

The added kill object if no errors occur.


```
GET/PUT: api/v1/games/{id}/kills/{id}
```


GET returns a single kill object in the game.
No error handling implemented.


PUT updates a kill object. Requires Auth-header with JWT that has 'admin' role or else HTTP 401 / HTTP 403 is returned.


Following fields can be issued in the request body:
'timeStamp', 'story', 'lat', 'lng'

Returns:
HTTP 404 if body and path id's don't match or kill isn't found.
The updated kill object if no errors occure.


### Chat
-	POST message
-	GET all messages
-	GET all global messages

</details>

## Limitations

The application does not contain rate limiting.

Some of the API functinality could be improved on, e.g. currently an admin can't add a player to a game because we're adding players based on the JWT token that is sent with the request.

HTTP error responses are not 100% standardized across the application.

## Installation

See the **frontend** installation at https://github.com/Satuhoo/humans-vs-zombies-frontend

**Backend** installation

Clone the source code:
`git clone https://github.com/maijahaka/humans-vs-zombies-backend.git`

You need to set the following environment variables depending on your setup: 

POSTGRES_PORT, POSTGRES_DB_NAME, POSTGRES_USERNAME, POSTGRES_PASSWORD, KEYCLOAK_REALM, KEYCLOAK_AUTH_SERVER_URL, KEYCLOAK_RESOURCE, KEYCLOAK_CREDENTIALS_SECRET, KEYCLOAK_BEARER_ONLY, KEYCLOAK_USE_RESOURCE_ROLE_MAPPINGS, KEYCLOAK_SSL_REQUIRED, SERVER_PORT, ALLOWED_WEB_SOCKET_ORIGINS
