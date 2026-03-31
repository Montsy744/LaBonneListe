# Architecture du projet

## Vue globale

Application full stack composée de :

* Backend Java (Servlets sur Tomcat)
* Frontend React
* Base de données PostgreSQL

---

## Communication

React communique avec le backend via une API REST :

```
React → HTTP → Servlet → Service → DAO → PostgreSQL
```

---

## Structure backend

```
controller/  → gestion HTTP (Servlets)
service/     → logique métier
model/dao/         → accès base de données (JDBC)
model/       → objets métier
model/util/        → outils (DB, helpers)
```

---

## Structure frontend

```
pages/       → pages principales
components/  → composants UI
services/    → appels API
```

---

## Base de données

### Tables principales

* users
* tasks

Relation :

* 1 utilisateur → plusieurs tâches

---

## Choix techniques

### Backend

* Java JEE (Servlets)
* JDBC (pas d’ORM)

### Frontend

* React (hooks)
* Fetch API

### Base de données

* PostgreSQL

---

## Objectif de l’architecture

* Séparer les responsabilités
* Faciliter la maintenance
* Avoir un code clair et structuré

---

## Améliorations possibles

* Ajout de DTO
* Mise en place d’une authentification sécurisée
* Passage à Spring Boot
* Dockerisation
