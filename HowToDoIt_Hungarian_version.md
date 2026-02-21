# Kezdőknek

[Alapok a webprogramozáshoz blogom.](https://nagraggini.github.io/Web-practising-and-fun/Web_Development/Practising/1-HTML%20Practising/2-Blog.html)

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

## Dockerfile tartalma

```
# Build stage: a projekt lefordítása és JAR csomagolása Maven segítségével.
# Java verzió a pom.xml-ben van beállítva (Java 17).

# A from sor létrehoz egy ideiglenes konténert.
FROM maven:3.8.5-openjdk-17 AS build
# Fordításra szolgál.
WORKDIR /app
COPY . .

# Lefordítja a Java kódot és létrehozza a JAR fájlt. Ezt a jar fájlt használjuk lentebb a copy sorban.
RUN mvn clean package -DskipTests

# Run stage: ez a konténer fogja futtatni az alkalmazást.
# Java verzió a pom.xml-ben van beállítva (Java 17).
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

# Ez átmásolja a fentebb létrehozott JAR fájlt a build stage-ből a run stage-be (/app/student-registry-app.jar).
COPY --from=build /app/target/*.jar student-registry-app.jar

# A Spring Boot alkalmazás a 8080-as porton fut.
EXPOSE 8080

# Alkalmazás futtatása. ENTRYPOINT: a konténer indításakor a JAR futtatása.
ENTRYPOINT ["java","-jar","student-registry-app.jar"]
```

# Adatbázisok

Az adatbázisokat alapvetően két fő kategóriába soroljuk:

- strukturált adatbázisok
- nem strukturált adatbázisok (A kezdők struktúráltat használnak, lekérdezéshez pedig sql-t.)

Postgresql-t [innen](https://www.postgresql.org/download/) tudod letölteni. Verzió: 16.11 Egyezzen lentebb létrehozott render.com-os adatbázissal.

A PostgreSQL működhet:

- szerverként (adatbázis szerver)
- kliensként (adatbázis kezelő eszköz)

Ebben az esetben neked csak a kliensre lesz szükséged, mert az adatbázis szerver a render.com platformon fog futni, és ahhoz távolról fogsz csatlakozni.

### Windows-on:

Környezeti változó beállítása: PowerShell:setx PATH "$env:PATH;C:\Program Files\PostgreSQL\16\bin" 

Lecsekkoljuk a verziót -> Cmd:cd "C:\Program Files\PostgreSQL\16\bin"psql --version Terminálban.

### Linux-on:

Terminálban:
sudo apt update
sudo apt install postgresql

[Postgesql hivatalos honlapja](https://www.postgresql.org/download/linux/ubuntu/)

## Adatbázis létrehozása a Render.com-on

A render.com-on hozz létre egy Postgres-t. A név legyen database. A verzió 16-os, a lényeg hogy egyezzen a gépre feltepített verzióval. Region: EU Instance Type: Free -> Create Database

Miután elkészült szükséged lesz az External Database URL-re, Username, Database, Password-re.

Terminálban, csatlakoztasd Postgres-t a render.com-os adatbázissal:
psql -h "@-utáni résztől....frankfurt-postgres.render.com-ig" -U "Username" -d "Database"
Entert nyomj.
pl.: psql -h dpg-d69k87buibrs739i5fu0-a.frankfurt-postgres.render.com -U database_olpd_user -d database_olpd
A jelszónak az oldalon lévő password-t másold be. Nem fogja mutatni. Majd entert nyomj.

Ez egy teljesen üres adatbázis táblák nélkül. Később létrehozzuk a táblákat és adattagokat is.

# application.properties beállítasa

Az application.properties fájlban rendeljük össze a weboldalt a render.com-os adatbázissal.

```
spring.application.name=student-registry-app

# Debughoz engedélyezzük a Tomcat access logot
server.tomcat.accesslog.enabled=true

# Automatikusan módosítja az adatbázist.
#Ha hozzáadsz egy új oszlopot az egyik tábládhoz, akkor ez automatikusan feltölti az új oszlopot az adatbázisba.
spring.jpa.hibernate.ddl-auto=update
# Minden SQL lekérdezést kiír a konzolra (debughoz hasznos).
spring.jpa.show-sql=true

#Formázza és kommenteli az SQL lekérdezéseket a konzolban.
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

#Protokoll, // után másold be a render-com-ról az Extend Database @ utáni részét.
#Ez nem annyira biztonságos módszer.
spring.datasource.url=jdbc:postgresql://dpg-d69k87buibrs739i5fu0-a.frankfurt-postgres.render.com/database_olpd

#Ez a biztonságosabb módszer, mert nem látják az url részt.
# TODO: Át kell javítanom a linket:
#spring.datasource.url=jdbc:postgresql://dpg-d69k87buibrs739i5fu0-a/database_olpd

# render.com-ról másold be ezeket:
spring.datasource.username=database_olpd_user

#Alapjáraton a jelszót elszokták rejteni, de ez csak demo weboldal.
spring.datasource.password=sekoojWQ5YUGrgC3080avcnkVvgY4LSQ

# Elmentjük a logokat egy fájlba. A logokat a Controllers osztályból szedi. Kb a syso-k összegyűjtése.
logging.file.name=logs/student-registry-app.log
logging.level.com.example.animal_shelter.controllers=INFO

#Miután a fentieket beállítottad, újra kell indítani a szervert.

```

# Projekt feltöltése githubra és render.com-ra

https://github.com/-ra regisztrálj be.

Github Desktop-t töltsd le. Utána File -> Add local repository-> keresd meg a mappát, ahova az új projektet hoztad létre. -> Add repository -> Create a repoditory -> Töltsd ki az űrlapot. -> Create repository

render.com regisztrálj -> Kösd össze a github fiókoddal.

New -> Web Service -> Válaszd ki a listából a progjekt nevét (student-registry-app)
Language: Docker
-> Deploy web service Kb 15 percig eltart a deploy.

## Build failed

Ha valamit elrontottál is kijavítod, utána a render.com-on -> Manual Deploy -> Deploy latest commit

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
