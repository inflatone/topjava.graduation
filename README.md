[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bae2b4b731a4462ab546d3f247435420)](https://www.codacy.com/manual/inflatone/topjava.graduation?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inflatone/topjava.graduation&amp;utm_campaign=Badge_Grade)
[![Build Status](https://api.travis-ci.org/inflatone/topjava.graduation.svg)](https://travis-ci.org/inflatone/topjava.graduation)
[![codecov](https://codecov.io/gh/inflatone/topjava.graduation/branch/master/graph/badge.svg)](https://codecov.io/gh/inflatone/topjava.graduation)
# REST API Web Service «Restaurant voting system»

Graduation project of  <a href="https://github.com/JavaWebinar/topjava">Java Enterprise Online Project (TopJava)</a>  course.

### Implemented using:
* JDK 11, Spring Boot 2.2.x, Spring 5.2.x
* <a href="https://gradle.org/">Gradle</a>, <a href="https://docs.travis-ci.com/user/tutorial/">Travis CI</a>
* Spring Data JPA/REST, Spring Security, and so on
* <a href="http://www.h2database.com/html/main.html">H2 Database</a> (in-memory + TCP)
* <a href="https://projectlombok.org/">Project Lombok</a>
* JUnit 5 + Extensions, <a href="https://site.mockito.org/">Mockito</a>, <a href="http://www.vogella.com/tutorials/AssertJ/article.html">AssertJ</a> 

### How to start:
* Build and run `boot jar`:
    * If you have gradle installed: `gradle bootRun` 
    * If gradle is not installed: `gradlew bootRun`
* After the server is running, go to `localhost:8080/rest`

### API documentation

### Examples
* Users:
    * <a href="http://localhost:8080/rest/account">GET /rest/account</a>
    * <a href="http://localhost:8080/rest/vote">GET /rest/vote</a>
    <pre>POST http://localhost:8080/rest/users
      Content-Type: application/json
      Authorization: Basic admin@javaops.ru admin
      
      {
       "email": "test@test.com",
       "firstName": "Test",
       "lastName": "Test",
       "password": "test",
       "roles": [ "ROLE_USER","ROLE_ADMIN"]
      }</pre>
    
* Admins:
    * <a href="http://localhost:8080/api/account">GET http://localhost:8080/rest/account</a>
    * <a href="http://localhost:8080/api/account">GET http://localhost:8080/rest/account</a>
    * <a href="http://localhost:8080/api/account">GET http://localhost:8080/rest/account</a>
    * <a href="http://localhost:8080/api/account">GET http://localhost:8080/rest/account</a>
   

#### Task brief

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.
<br>
<a href="graduation.md">full description and recomendations</a><br/>