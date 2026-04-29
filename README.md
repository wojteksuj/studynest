# StudyNest

A full-stack flashcard study application built to practice **full-stack development** with a focus on clean architecture, authentication, and containerized deployment.

The system allows users to register, organize flashcards into topic-based sets, and study them through a responsive React interface backed by a secure, JWT-authenticated REST API.

Its purpose is to provide a structured, distraction-free environment for self-directed learning, with persistent progress and organized content management.

All wired together through a single Spring Boot backend, a React SPA, and an nginx reverse proxy — fully containerized with Docker Compose.


## How it works

<img width="1015" height="844" alt="sn-diagram" src="https://github.com/user-attachments/assets/4946094e-d02c-42ee-a790-c30d39177c93" />
<br>

**nginx** serves the compiled React SPA for all `/*` routes and transparently proxies `/api/*` requests to the Spring Boot backend — exposing only a single port to the browser.

**Spring Boot API** handles user registration and login (issuing JWTs), and exposes protected endpoints for managing topics, flashcard sets, and individual flashcards. Schema migrations are applied automatically at startup via Liquibase.

**React frontend** provides a single-page interface for studying, built with TypeScript and Tailwind CSS, communicating with the API using JWT tokens stored client-side.


## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.3 |
| Security | Spring Security, JWT (jjwt) |
| Database | PostgreSQL 16 |
| Migrations | Liquibase |
| ORM | JPA / Hibernate |
| Boilerplate | Lombok |
| Frontend | React 19, TypeScript |
| Bundler | Vite |
| Styling | Tailwind CSS |
| Routing | React Router |
| Infrastructure | Docker, Docker Compose, nginx |


## API Reference

### Auth — `/api/auth`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | No | Register a new user |
| `POST` | `/api/auth/login` | No | Login and receive a JWT |

### Flashcard Sets — `/api/flashcard-sets`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/flashcard-sets` | Yes | List the current user's flashcard sets |
| `POST` | `/api/flashcard-sets` | Yes | Create a new flashcard set |
| `GET` | `/api/flashcard-sets/{id}` | Yes | Get a specific flashcard set |
| `DELETE` | `/api/flashcard-sets/{id}` | Yes | Delete a flashcard set |
| `GET` | `/api/flashcard-sets/{id}/flashcards` | Yes | List flashcards within a set |
| `POST` | `/api/flashcard-sets/{id}/flashcards` | Yes | Add a flashcard to a set |

### Flashcards — `/api/flashcards`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `PUT` | `/api/flashcards/{id}` | Yes | Update a flashcard |

### Topics — `/api/topics`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/topics` | Yes | List the current user's topics |
| `POST` | `/api/topics` | Yes | Create a topic |

### Users — `/api/users`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/users/username` | Yes | Get the current user's info |

All protected endpoints require an `Authorization: Bearer <token>` header using the JWT returned at login.


## Database Schema

Managed by Liquibase — migrations are applied automatically on first startup.

```
users
  └── topics
        └── flashcard_sets
              └── flashcards
```


## Repository Structure

```
studynest/
├── backend/        # Spring Boot API
├── frontend/       # React + TypeScript SPA
└── docker-compose.yml
```


## Running Locally

The only requirement is [Docker](https://www.docker.com/get-started) with Compose v2.

```bash
git clone <repo-url>
cd studynest
docker compose up --build
```

Open **http://localhost:5173** — register an account and start studying.

```bash
docker compose down       # stop containers
docker compose down -v    # stop and delete database volume
```


## Tests

The backend includes unit and controller tests covering authentication, business logic, and API behavior.
Mockito is used for mocking dependencies, and Spring WebMVC Test is used for lightweight controller testing.


## AI

The backend application and overall architecture were designed and implemented entirely by me.
AI agent (OpenAI GPT-4.5) was used as a supporting tool for developing the frontend, as well as for learning, discussing design decisions, refining code, and generating unit tests.
