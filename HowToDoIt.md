# Spring Boot

[Ez alapján csináltam.](https://www.youtube.com/watch?v=RK6aAjUMcl0&list=PLg7lel5LdVjyO7jk-4biyr0fqPVygTLOk)

[A fenti youtube videóhoz tartozó repo.](https://github.com/bobbychansfu/springboot-render/tree/7d38d0343465661adceb6372591a8353717dfd5e)

[Korábbi saját projektem.](https://github.com/Nagraggini/animal_shelter/edit/main/HowToDoIt_Hungarian_version.md)

További link:
[Spring MVC Beginner](https://www.youtube.com/watch?v=VqptK6_icjk&list=PL82C6-O4XrHejlASdecIsroNEbZFYo_X1)

[Fejlesztői környezet és kiegészítők letöltése](https://code.visualstudio.com/docs/java/java-spring-boot)

A vs code extension részen ezeket töltsd le:

- Java Development Kit (JDK)
- Extension Pack for Java
- Spring Boot Extension Pack
- Spring Boot Tools
- Spring Initializr

# Projekt létrehozása

Előre konfigurált Spring Boot projektet lehet generáltatni, hogy ne kelljen kézzel megírni az összes kezdő fájlt és beállítást: [Spring Initializr](https://start.spring.io/)

Project: Maven
Language: Java
Spring Boot: 3.5.11 (Production-ready verzió.)
Artifact és Name: projektneve, amit az elején megadtál. Jelen esetben: student-registry-app
Packaging: Jar
Conf: Properties
Java: 17

_Spring Boot ökölszabály:_
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

# Dockerfile

Az alkalmazás győkér könyvtárába hozd létre egyből a Dockerfile, ne adj neki kiterjesztést, txt-t sem.

Ez a Dockerfile a Spring Boot alkalmazás konténerizálására szolgál.

A célja, hogy az alkalmazásodat lefordítsa és futtassa egy Docker konténerben, így bárhol ugyanúgy működjön (Render, saját gép, stb.).

Ez az egész Dockerfile arra kell, hogy:

- lefordítsa a Spring Boot projektet
- JAR fájlt készítsen
- elindítsa egy konténerben

# Projekt feltöltése githubra és render.com-ra

https://github.com/-ra regisztrálj be.

Github Desktop-t töltsd le. Utána File -> Add local repository-> keresd meg a mappát, ahova az új projektet hoztad létre. -> Add repository -> Create a repoditory -> Töltsd ki az űrlapot. -> Create repository

render.com regisztrálj -> Kösd össze a github fiókoddal.

New -> Web Service -> Válaszd ki a listából a progjekt nevét (student-registry-app)
Language: Docker
-> Deploy web service Kb 15 percig eltart a deploy.

## SSH key

Ez alapján hozd létre: https://github.com/settings/ssh/new

# Student Registry App – Architektúra

student-registry-app/src/main/java/com/example/student_registry_app/

_Controller_ → Itt történik a kérés kezelése és az átirányítás.
A controller fogadja a felhasználói kéréseket (pl. REST API hívásokat vagy űrlap beküldést), majd meghívja a service réteget. Nem tartalmaz üzleti logikát.

_Service_ → Itt van az alkalmazás logikája.
Ide kerül az üzleti logika, például hogy egy adat melyik helyre kerüljön, hogyan legyen feldolgozva, ellenőrizve vagy mentve.
Ez egy REST API esetén a “működési agy” része.

_Model_ → Itt vannak az entitások / példányok.
Ide tartoznak az adatmodellek, amik az adatbázis táblákat reprezentálják (pl. Student, Course stb.).
Ezek az objektumok tárolják az adatokat.

*DTO*→ (Data Transfer Object) Adatátviteli objektum. Gyakran használjuk Controller ↔ Service vagy API kommunikáció között.
