# Spring Boot: Migrating from REST to GraphQL

This project is a practical demonstration of migrating a traditional Spring Boot REST API to a Spring Boot GraphQL API, or maintaining both simultaneously.

It specifically showcases how clean architectural practices allow for adding a completely new API paradigm (GraphQL) with virtually zero changes to the underlying business logic, models, or database layers.

## The Starting Point: Legacy Code (REST API)

At its core, this application is a standard Spring Boot application managing a vehicle inventory.

- **Model (`Vehicle.java`)**: A JPA `@Entity` representing a vehicle with various attributes (make, model, year, price, mileage, etc.).
- **Repository (`VehicleRepository.java`)**: A standard Spring Data JPA interface extending `JpaRepository` for basic CRUD operations against an in-memory H2 database.
- **Service (`VehicleService.java`)**: Contains all the business logic for fetching, creating, updating, and deleting vehicles.
- **REST Controller (`VehicleController.java`)**: A traditional `@RestController` using standard HTTP verbs (`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`) to expose endpoints at `/api/vehicles`. 

This setup essentially represents the classic state of most Spring Boot microservices.

## The Migration: Adopting GraphQL

To introduce GraphQL, I have took advantage of Spring's separation of concerns. I successfully implemented the GraphQL API with **minimal changes**—in fact, no existing service, model, or repository files had to be modified!

Here's how it was done:
1. **Adding Dependencies:** Added the `spring-boot-starter-graphql` dependency to the project framework.
2. **Creating the Schema:** A new `schema.graphqls` file was added in `src/main/resources/graphql/` to declare the data structures (`type Vehicle`, `input VehicleInput`) and available operations (`type Query`, `type Mutation`).
3. **The GraphQL Controller (`VehicleGraphqlController.java`)**: Rather than trying to cram GraphQL logic into our REST controller or changing the service layer, I simply created a new `@Controller` specifically for GraphQL:
   - Injected the **exact same** `VehicleService`.
   - Instead of HTTP mapping annotations like `@GetMapping`, used GraphQL-specific mapping annotations (`@QueryMapping`, `@MutationMapping`).
   - Instead of using `@PathVariable` or `@RequestBody` to capture input data, passed inputs using `@Argument`.

The rest of the stack remains fundamentally untouched.

## Testing: REST vs. GraphQL

I have maintained comprehensive functional testing across the application to prove that the underlying Spring Boot functionality remains completely intact while delivering the same results regardless of the protocol used.

### REST Testing (`VehicleControllerTest.java`)
- Evaluates the REST interface functionality using standalone `MockMvcBuilders`.
- Tests endpoints against HTTP networking factors like appropriate status codes (200 OK, 404 Not Found), parsing `PathVariable` routing, and evaluating serialized JSON representations via simulated HTTP methods.
- Mocks responses from `VehicleService` using Mockito to properly isolate the controller layer from the service/database layers.

### GraphQL Testing (`VehicleGraphqlControllerTest.java`)
- Testing the GraphQL implementation becomes much more streamlined. Because the GraphQL controller methods are just standard functional Java methods, they can effectively be tested as pure POJOs without having to spin up a simulated HTTP context.
- Given mock inputs, the tests simply verify that the properties seamlessly invoke the underlying service.
- Like the REST controller tests, the `VehicleService` is explicitly mocked via standard Mockito to maintain proper unit testing boundaries.

## How to Run

To run the application locally, use the standard Gradle wrapper from the root generated directly by Spring Boot:

```bash
./gradlew bootRun
```

This will automatically:
- Spin up an embedded Tomcat server on port `8080`.
- Initialize an in-memory instance of the H2 Database.
- Serve the REST API at `http://localhost:8080/api/vehicles`.
- Serve the GraphQL API at `http://localhost:8080/graphql`.
- Enable the GraphiQL interactive web IDE at `http://localhost:8080/graphiql`.

## Frontend Integration

The project also includes a responsive Vanilla JavaScript Single Page Application (SPA) located in `/src/main/resources/static/`.

### Serving the Frontend
Because it is placed inside `resources/static`, Spring Boot natively serves the frontend as static files. Once the application starts, we can simply view it by going to **http://localhost:8080** in your browser. No separate `npm` commands or frontend servers are necessary!

### How It Works
Rather than communicating with the REST `/api/vehicles` endpoints, the `app.js` logic exclusively demonstrates interacting with the **GraphQL layer (`/graphql`)**.

We can browse the inventory, create new vehicles with a fluid UI, and delete existing entries via the power of GraphQL.
