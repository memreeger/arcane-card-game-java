# Arcane Card Game (Java)

Arcane is a console-based card game project developed in Java with a focus on object-oriented programming, layered architecture, and clean code practices.

The project was designed to improve backend-oriented thinking by separating responsibilities into different layers and building scalable game logic using design patterns and service-based architecture.

---

## Features

* Multiple deck systems

    * Alchemy Deck
    * Element Deck
    * Quantum Deck

* Deck operations

    * Shuffle deck
    * Draw cards
    * Create player hand
    * Add and remove cards from hand

* Card system

    * Number cards
    * Special cards

* Discard pile structure

* Console-based gameplay simulation

---

## Technologies & Concepts

* Java
* OOP Principles
* SOLID Principles
* Layered Architecture
* Factory Design Pattern
* Singleton Pattern
* Collections Framework
* Enum-based game system design

---

## Project Structure

```text
src
├── abst
├── enums
├── factory
├── model
│   ├── card
│   ├── deck
│   ├── hand
│   └── user
│
├── presentation
│
└── services
    ├── deck
    │   ├── create
    │   └── manipulate
    │
    ├── discardPile
    └── hand
```

---

## Design Approach

The project was built with a service-oriented structure in order to separate responsibilities clearly between:

* Models
* Services
* Interfaces
* Factories
* Presentation layer

Deck creation is handled through a factory-based architecture, while deck manipulation and hand management are separated into dedicated services.

This structure was designed to keep the project maintainable and scalable as new game mechanics are added.

---

## Sample Gameplay Flow

1. User selects a deck type
2. Selected deck is created
3. Deck is shuffled
4. Initial hand is generated
5. Player can:

    * Draw cards
    * Add cards to hand
    * Remove cards from hand
    * Manage hand state

---

## Future Improvements

Planned improvements for the project include:

* PostgreSQL integration
* JDBC implementation
* JPA / Hibernate support
* DTO and DAO architecture
* REST API development with Spring Boot
* Player and score systems
* Expanded game rules and mechanics

---

## Purpose of the Project

This project was primarily developed to improve:

* Java OOP knowledge
* Clean architecture understanding
* Design pattern usage
* Backend development fundamentals
* Scalable project structure design

---

## Author

Emre Eger
