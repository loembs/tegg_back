# Platforme Tegg - Backend Spring Boot

Backend Spring Boot pour la plateforme de services à domicile Tëgg.

## 🏗️ Architecture

- **Java 17** avec Spring Boot 3.2.0
- **PostgreSQL** comme base de données
- **Spring Security** avec JWT pour l'authentification
- **Docker** pour le déploiement

## 📦 Structure du Projet

```
backend-spring/
├── src/main/java/sn/tegg/platforme/
│   ├── config/           # Configuration (Security, etc.)
│   ├── data/
│   │   ├── enums/        # Énumérations
│   │   ├── models/       # Entités JPA
│   │   └── repository/   # Repositories JPA
│   ├── security/         # JWT et Sécurité
│   ├── services/         # Logique métier
│   └── web/
│       ├── controllers/  # API REST
│       └── dto/          # DTOs Request/Response
├── src/main/resources/
│   ├── application.properties           # Configuration principale
│   ├── application-dev.properties       # Environnement Développement
│   ├── application-prod.properties      # Environnement Production
│   └── data.sql                          # Données initiales
├── Dockerfile
├── docker-compose.yml
└── docker-compose.dev.yml
```

## 🚀 Démarrage Rapide

### Avec Docker (Recommandé)

```bash
# Démarrer tous les services (PostgreSQL + Backend)
docker-compose up -d

# Vérifier les logs
docker-compose logs -f backend

# Arrêter les services
docker-compose down
```

### En développement (sans Docker)

```bash
# Démarrer PostgreSQL avec Docker uniquement
docker-compose -f docker-compose.dev.yml up -d

# Lancer le backend localement
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 👤 Utilisateurs de Test

Le fichier `data.sql` crée automatiquement les utilisateurs suivants :

| Rôle      | Téléphone       | Mot de passe  | Nom             |
|-----------|-----------------|---------------|-----------------|
| ADMIN     | +221771234567   | password123   | Admin Principal |
| CLIENT    | +221772345678   | password123   | Moussa Diop     |
| ARTISAN   | +221773456789   | password123   | Ibrahima Ndiaye |
| ARTISAN   | +221774567890   | password123   | Aminata Ba      |
| CLIENT    | +221775678901   | password123   | Fatou Sow       |

## 🔌 API Endpoints

### Authentification
- `POST /api/auth/login` - Connexion
- `POST /api/auth/register` - Inscription
- `POST /api/auth/verify-otp` - Vérification OTP
- `GET /api/auth/me` - Utilisateur actuel

### Services
- `GET /api/services/categories` - Liste des catégories
- `GET /api/services/categories/{id}` - Détails catégorie
- `GET /api/services/subcategory/{id}` - Services par sous-catégorie
- `GET /api/services/search?keyword=` - Recherche services

### Demandes
- `POST /api/requests` - Créer une demande
- `GET /api/requests/my-requests` - Mes demandes
- `PUT /api/requests/{id}/status` - Mettre à jour statut

## 🐳 Docker Commandes

```bash
# Build l'image
docker build -t tegg-backend .

# Lancer avec profil dev
docker-compose --env-file .env up -d

# Logs en temps réel
docker-compose logs -f backend

# Entrer dans le container
docker-compose exec backend sh

# Redémarrer le backend
docker-compose restart backend
```

## 🔧 Configuration

### Variables d'environnement

Copiez `.env.example` vers `.env` et modifiez selon vos besoins :

```bash
cp .env.example .env
```

### Profiles

- `dev` : Développement avec logs détaillés
- `prod` : Production avec logs minimisés

```bash
# Lancer avec un profil spécifique
java -jar app.jar --spring.profiles.active=prod
```

## 📊 Base de Données

### pgAdmin

Pour accéder à pgAdmin (incluez le profil `tools`) :

```bash
docker-compose --profile tools up -d
```

- URL : http://localhost:5050
- Email : admin@tegg.sn
- Mot de passe : admin123

### Connection directe

```bash
# Depuis le host
psql -h localhost -U postgres -d tegg_db

# Depuis Docker
docker-compose exec postgres psql -U postgres -d tegg_db
```

## 🔐 Sécurité

- JWT pour l'authentification stateless
- BCrypt pour le hachage des mots de passe
- CORS configuré pour les origins autorisés
- Rôles : ADMIN, CLIENT, ARTISAN

## 🧪 Tests

```bash
# Lancer les tests
mvn test

# Tests avec couverture
mvn clean test jacoco:report
```

## 📝 Licence

Copyright © 2024 Platforme Tegg
