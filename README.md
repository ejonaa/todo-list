# To-Do List Application

In today’s fast-paced world, staying organized can be a challenge. Many people struggle to remember all their important 
tasks, and not everyone has the means to hire a personal assistant. This creates a strong need for an app that allows 
users to keep track of their essential tasks and notes, helping them manage their responsibilities more effectively.

## Build with
- [Java 11](https://www.oracle.com/uk/java/technologies/javase/jdk11-archive-downloads.html)
- [Spring Boot 2.7.5](https://spring.io/blog/2022/10/20/spring-boot-2-7-5-available-now)
- H2 / PostgreSQL DB
- Maven
- Git

## How to Run

This application is packaged as a jar which has Tomcat 9 embedded. No Tomcat installation is necessary. 
You run it using the ```java -jar``` command.

* Clone this repository
* Make sure you are using JDK 11
* Change to your favourite profile in application.yml file
* You can build the project and run the tests by running 
```
.\mvnw clean verify
```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar target/crispy_be_challenge_ejona-aliaj-0.0.1-SNAPSHOT.jar
or
        .\mvnw spring-boot:run
```

Once the application runs you should see something like this

```
2017-08-29 17:31:23.091  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8091 (http)
2017-08-29 17:31:23.097  INFO 19387 --- [           main] com.khoubyari.example.Application        : Started Application in 22.285 seconds (JVM running for 23.032)
```

## About the Service

The service is just a simple TODO list REST service. It uses an in-memory database (H2) to store the data in dev profile. 
You can also do with a relational database like PostgreSQL in prod profile. If your database connection properties work, 
you can call some REST endpoints defined in ```com.crispy.crispy_be_challenge_ejona_aliaj.controller.api``` on **port 8091**. (see below)

Here is what this little application demonstrates:

* Api Authentication using JWT token
* CRUD REST APIs to manage a ToDo List
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* Using AOP and custom annotation to validate user input
* *Spring Data* Integration with JPA
* Demonstrates a simple test for account creation
* All APIs are "self-documented" by Swagger2 using annotations
* Using docker to containerize Postgres

Here are some endpoints you can call:

### Create user account

```
POST /api/create-account
Accept: application/json
Content-Type: application/json

{
    "login": "ealiaj",
    "password": "test",
    "firstName": "Ejona",
    "lastName": "Aliaj"
}

RESPONSE: HTTP 201 (Created)
```

### Retrieve a paginated list of projects

```
GET /api/1.0/projects?page=0&size=10

Response: HTTP 200
Content: paginated list 
```

### Update a task resource

```
PATCH /api/1.0/projects/{projectId}/tasks/{taskId}
Accept: application/json
Content-Type: application/json

{
    "name": "Shopping",
    "note": "some notes",
    "dueDate": "2024-11-05",
    "completed": false,
    "important": true
}

or 

{
    "dueDate": "2024-11-05",
    "completed": false
}

or any other combination.

RESPONSE: HTTP 204 (No Content)
```
### To view Swagger 2 API docs

Run the server and browse to http://localhost:8091/swagger-ui/index.html#/

### To view your H2 in-memory datbase

The 'dev' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8090/h2-console. 
Default username is 'sa' with a blank password.

### Running the project using PostgreSQL

To start a postgresql database in a docker container, run:
``` 
docker-compose -f postgresql.yml up -d
```

Run the following first
```
create database todolist;
create user todo_list_user with encrypted password 'admin';
grant all privileges on database todolist to todo_list_user;

GRANT ALL ON ALL TABLES IN SCHEMA "public" to todo_list_user;
GRANT USAGE ON SCHEMA public to todo_list_user;
GRANT ALL ON SCHEMA public to todo_list_user;

ALTER DATABASE todolist OWNER TO todo_list_user;
```

[Then you can follow instructions here](#how-to-run)

## User Manual

- Create a user account if you don't have one
- Authenticate using username/password
- Create a project e.g. "Today's tasks"
- Create the tasks you are going to do e.g.
  - Grocery
  - Shopping
- For each of the tasks you can specify some details like: 
  - note
  - due date
  - completed
  - important
- For each task you can create one or more steps
- You can read all the data by getting the project you created

## Questions and Comments: 
aliaj.ejona@gmail.com