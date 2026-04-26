# Overview
This project is built as part of my preparation for backend engineering roles in Germany.

This project is a simplified backend system for cinema seat booking, designed to simulate real-world challenges such as concurrent reservations and temporary seat holding.

The goal of this project is not to build a full-featured cinema platform, but to demonstrate:

- Backend system design
- Concurrency handling (race conditions)
- Clean architecture and separation of concerns
- Practical trade-offs in data modeling

# Key Features
- View available seats for a session
- Reserve one or multiple seats
- Temporary seat holding with expiration (TTL)
- Confirm reservation (simulate successful payment)
- Prevent double booking under concurrent requests

# System Design
## Booking Flow
1. User selects seats (no reservation yet)
2. User sends reservation request
3. System checks seat availability
4. Seats are temporarily reserved (status = PENDING)
5. Reservation expires if not confirmed within a time window
6. On confirmation, seats become permanently booked (CONFIRMED)

## Concurrency Handling
The system handles race conditions when multiple users try to reserve the same seat:

- Seat availability is validated at reservation time
- Reservation is performed atomically
- Only one request can successfully reserve a seat

Approach used:

- Transactional validation in the service layer
- Database-level consistency (via queries + constraints)

## Reservation Expiration
- Each reservation has a reserved_until timestamp
- Expired reservations are treated as invalid
Expiration is handled using:
- Lazy validation during requests
- (In future) background cleanup process

# Data Model
- User
    - id (primary key)
    - name
- Hall
    - id (primary key)
    - name
- Seat
    - id (primary key)
    - hall_id (foreign key)
    - name
- Session
    - id (primary key)
    - hall_id (foreign key)
    - start_time
    - name
    - description
- Reservation
    - id (primary key)
    - session_id (foreign key)
    - reserved_by (foreign key to user_id)
    - reserved_until (datetime)
    - status: PENDING - waiting for payment confirmation, DONE - payment was successfull, CANCELLED - payment cancelled by user or reservation timeout
- Reservation_seats - список мест в каждой резервации
    - id (primary key)
    - reservation_id (foreign key)
    - seat_id (foreign key)


# Project Structure
```
components/   # configuration of components, such as data initialiazation
controller/   # REST API endpoints
dto/          # API request/response models
entity/       # JPA entities (data model)
repository/   # Database access layer (Spring Data JPA)
service/      # Business logic (core of the system)
```
# Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- H2 (in-memory database)
- Maven

# Notes
This project intentionally focuses on backend engineering challenges rather than UI or full product features.

It is designed to demonstrate how real-world booking systems handle consistency, concurrency, and scalability concerns.
