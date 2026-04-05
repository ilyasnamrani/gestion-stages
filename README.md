#  Gestion des Stages (Microservices Architecture)

Ce projet est une application de gestion de stages basée sur une architecture de microservices avec **Spring Boot**, **Spring Cloud** et **PostgreSQL**.

##  Architecture Technique

![Architecture Diagram](docs/images/architecture.png)

Le projet est conçu pour être scalable, résilient et facile à surveiller grâce aux technologies suivantes :

- **Spring Cloud Config Server** (Port : 9999) : Centralisation de la configuration.
- **Eureka Discovery Service** (Port : 8761) : Service de découverte pour l'annuaire des microservices.
- **API Gateway** (Port : 8888) : Point d'entrée unique pour toutes les requêtes clients.
- **Zipkin** (Port : 9411) : Traçabilité distribuée pour le débogage.
- **Redis** (Port : 6379) : Système de cache distribué.
- **Apache Kafka** (Port : 9092) : Streaming d'événements asynchrones entre les services.

## Microservices

| Service | Port | Base de Données | Description |
|---|---|---|---|
| **Candidat Service** | 8080 | PostgreSQL (`candidat_db`) | Gestion des profils candidats. |
| **Entreprise Service** | 8081 | PostgreSQL (`entreprise_db`) | Gestion des offres et
entreprises. |
| **Notification Service** | 8082 | - | Envoi automatique d'emails via Kafka. |

##  Lancement Rapide

### Prérequis
- Docker & Docker Compose
- Java 17+
- Maven

### Étapes
1. **Configurer le Config Repo** :
   ```bash
   cd config-repo-gestion-stages
   git add .
   git commit -m "Update configurations"
   ```

2. **Démarrer les services** :
   ```bash
   docker compose up -d --build
   ```

3. **Accès** :
   - API Gateway : `http://localhost:8888`
   - Eureka Dashboard : `http://localhost:8761`
   - Zipkin Dashboard : `http://localhost:9411`
   - Kafdrop (Kafka UI) : `http://localhost:9000`

##  Configuration Base de Données

Le projet utilise désormais **PostgreSQL**. Chaque service gère sa propre base de données isolée. Les bases et les utilisateurs sont créés automatiquement par Docker au premier lancement via les configurations définies dans `docker-compose.yml`.
