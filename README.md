# San Francisco Restaurant Inspections API
This API allows users to search search for restaurants' inspections. The API allows searching either by name, zip, or both.

## Using the API
#### Restaurants:
1. `/v1/api/restaurant`
	- GET: gets all restaurants (the home route is not recommended and will return ALL restaurants and their inspections
		- To specify a ZIP code, user query strings
		- `/api/v1/restaurant?zip=94112` will return all restaurants with a ZIP code of 94112
		- `/api/v1/restaurant?name=ramen` will return all restaurants with "ramen" as its names. This search is case insensitive.
		- `/api/v1/restaurant?zip=94112&name=mcdonald%27s` will return restaurants with name "mcdonald's" and zip 94112. It is fine to switch the ordering of the query string key/values.
2. `/v1/api/inspection`
	- GET: gets all inspections. Again, this is not recommended and will return a lot of JSON.
	- It is also possible to append a restaurant ID as the path parameter to get all inspections of that restaurant.

## Tools
- [Spring Boot](https://spring.io/projects/spring-boot)
- [PostgreSQL](https://www.postgresql.org/)
- [AWS](https://aws.amazon.com/)
- [Clever Cloud](https://www.clever-cloud.com/en/)
- [Postman](https://www.postman.com/)
- [Docker](https://www.docker.com/)
