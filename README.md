# Extend dummyjson.com to include brand based filtering
Java and Springboot application to extend the functionality of dummyjson.com by exposing endpoints that allows users to do brand based filtering.


### Access it on [extenddummyjson-latest.onrender.com](https://extenddummyjson-latest.onrender.com)
>The backend is hosted on render and can be accessed using the above link but the initial load may be a minute long as render spins down the deployment after inactivity.


### Overview

The application uses Spring WebMVC framework.   
After the application is ready, an event is triggered which fetches all the products from [dummyjson.com/products](https://dummyjson.com/products) endpoint which serves all the products available.  
All the products are stored in the database for which I'm using __H2__.  
I've defined the products entity and am utilizing __JPA__, creating a repository to access the database.

### Endpoints
The backend service exposes the following rest endpoints for consumption 
1. '/products/brands'  
   Which returns the list of all brands (which it parses from the original products list)
2. '/products/brand/{brandName}'  
   This acts similar to the one in dummyjson and returns all products under that brand
3. '/products/category/{category}/brand/{brandName}'  
   This endpoint is used when we have both filters active. I originally wanted to use RequestParams for this but then decided to go ahead with extending the facility similar to that of dummyjson.com
   
### Setup
This is a Springboot application built on Java17.  
To run it, make sure you have Java and Maven installed on your system.

#### Steps
1. Clone the repository
2. Do a `mvn clean install`
3. Run using `mvn spring-boot:run`

<hr />

#### Access Frontend on [cosmic-druid-28d7d7.netlify.app/](https://cosmic-druid-28d7d7.netlify.app/)
