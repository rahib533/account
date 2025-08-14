# Account Service (Spring Boot)

A simple Spring Boot application – REST API for managing **Customer**, **Account**, and **Transaction** resources.

## Technologies
- Java 17+
- Spring Boot (Web, Data JPA)
- Restful API
- OpenAPI documentation
- PostgreSQL
- Docker
- Docker compose
- JUnit
- Mockito
- Mapstruct
- Hibernate
- Lombok

## Project Structure
- **Model**: `Customer`, `Account`, `Transaction` (and enums `ModelStatus`, `TransactionType`)
- **Controller**:
  - `/v1/customer`
  - `/v1/account`
  - `/v1/transaction`
- **Service & Mapper** layers for business logic and DTO mapping

## Endpoints (short)
### Customer
- `GET /v1/customer/getAll` – Get all customers
- `GET /v1/customer/get/{id}` – Get a single customer
- `POST /v1/customer/create` – Create a new customer
- `PUT /v1/customer/update` – Update customer
- `DELETE /v1/customer/delete/{id}` – Delete customer

### Account
- `GET /v1/account/getAll` – Get all accounts
- `GET /v1/account/get/{id}` – Get a single account
- `POST /v1/account/create` – Create a new account
- `PUT /v1/account/update` – Update account
- `DELETE /v1/account/delete/{id}` – Delete account

### Transaction
- `GET /v1/transaction/getAll` – Get all transactions
- `GET /v1/transaction/get/{id}` – Get a single transaction
- `POST /v1/transaction/create` – Create a new transaction
- `PUT /v1/transaction/update` – Update transaction
- `DELETE /v1/transaction/delete/{id}` – Delete transaction

## Info
- All `id` values are of type `UUID`.
- `ModelStatus`: `ACTIVE`, `INACTIVE`
- `TransactionType`: `INITIAL`, `TRANSFER`, `DEPOSIT`, `WITHDRAWAL`

## Run
```bash
# Clone the project
git clone https://github.com/<user>/<repo>.git
cd <repo>

# Run (Maven)
./mvnw spring-boot:run
```

Swagger UI: [http://localhost:${PORT}/swagger-ui/index.html#/](http://localhost:${PORT}/swagger-ui/index.html#/)