# Système de gestion de bibliothèque en ligne

## Introduction

Ce projet est un **système de gestion de bibliothèque en ligne** conçu pour gérer efficacement les livres, les emprunts, et les utilisateurs. Il permet aux utilisateurs de rechercher des livres, de faire des réservations, ainsi que de gérer les retours. L'application est bâtie sur une architecture RESTful, ce qui permet une communication fluide entre le frontend et le backend via des API.

Les principales fonctionnalités incluent :
- Recherche de livres
- Réservation de livres
- Gestion des retours

## Prérequis

Avant de lancer le projet, assurez-vous d'avoir installé les éléments suivants :

- **Java 17**
- **Maven** (pour la gestion des dépendances)
- **PostgreSQL** (pour la base de données)
- **Git** (pour cloner le dépôt)

## Installation et lancement du projet

Suivez ces étapes pour cloner et lancer le projet :

1. Clonez le dépôt Git :
   ```bash
   https://github.com/delmas007/bibliotheque_Atos.git
    ```
   
2. Accédez au répertoire du projet :
    ```bash
    cd bibliotheque_Atos
    ```

3. Ouvrez le fichier `application.properties` dans le dossier `src/main/resources` et configurez les paramètres de connexion à la base de données :
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/votre_base_de_donnees
   spring.datasource.username=username
   spring.datasource.password=password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Compilez et démarrez l'application en utilisant Maven :
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   
5. L'application sera accessible à l'adresse suivante : `http://localhost:8080`

6. Pour accéder à la documentation Swagger, ouvrez le lien suivant : `http://localhost:8080/swagger-ui.html`

7. Pour accéder à la documentation Postman, ouvrez le lien suivant : `https://documenter.getpostman.com/view/28612623/2sAXqqdPBz` 