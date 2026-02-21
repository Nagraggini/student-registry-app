# Projekt létrehozása

Előre konfigurált Spring Boot projektet lehet generáltatni, hogy ne kelljen kézzel megírni az összes kezdő fájlt és beállítást: [Spring Initializr](https://start.spring.io/)

Project: Maven
Language: Java
Spring Boot: 3.5.11 (Production-ready verzió.)
Artifact és Name: projektneve, amit az elején megadtál. Jelen esetben: student-registry-app
Packaging: Jar 
Conf: Properties
Java: 17 

*Spring Boot ökölszabály:*
Mindig olyat válassz, ami:
- nem SNAPSHOT: Fejlesztői, instabil build.
- nem M (milestone): Előzetes kiadás, tesztelési célra.
- nem RC (release candidate): Majdnem kész.

## Dependenciák

Spring Boot DevTools
Fejlesztéshez hasznos. Gyorsabb újraindítás, automatikus reload.

Lombok
Gettereket, settereket és egyéb boilerplate kódot generál.

Spring Web
Webalkalmazás készítéséhez szükséges (@Controller, HTTP kérések kezelése).

Thymeleaf
Dinamikus HTML oldalak készítéséhez.

Spring Data JPA
ORM réteg entity osztályokhoz és adatbázis CRUD műveletekhez.

CRUD jelentése:

Create – létrehozás

Read – lekérdezés

Update – módosítás

Delete – törlés

PostgreSQL Driver
Lehetővé teszi az alkalmazás számára a PostgreSQL adatbázishoz való csatlakozást.
