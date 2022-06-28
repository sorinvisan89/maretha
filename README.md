# Playlists Microservice API
This is a POC Playlists implemented based on the given requirements.
It consists of a REST controller which can be used to search playlists by content id and country name.


## How to run

### Dockerized Application
- Docker engine must be installed.
- You can get it from the official 


- From the root project folder, you can bring up all dependencies using the following:
 ```
    docker-compose -f docker-compose.yaml up
 ```

- This should bring up a MySQL container and a container with the Java application

### Native Java Application

- You will need to bring up by yourself a MySQL database

- From the root project directory, you need to issue the following command, depending on your environment:


- **UNIX**:
  ```
  ./mvnw spring-boot:run
  ```

- **Windows**:
  ```
  ./mvnw.cmd spring-boot:run
  ```

## Screenshots

### Entity Relationship Diagram (ERD)

The entities have the following relationship:

1) **Contents** has a **Many-to-Many** relationship with **Videos**
   - A Content can have one or more videos
   - A Video can be associated to one or more contents
2) **Contents** has a **Many-to-Many** relationship with **Prerolls**
   - A Content can have one or more prerolls
   - A Preroll can be associated to one or more contents
3) **Prerolls** has a **Many-to-Many** relationship with **Videos**
    - A Preroll can have one or more videos
   - A Video can be associated to one or more prerolls
4) **Videos** has a **Many-to-Many** relationship with **Countries**
   - A Video can be associated to one or more countries
   - A Aountry can be associated to one or more videos

![ERD](https://github.com/sorinvisan89/maretha/blob/main/images/erd.png)

### Swagger
The swagger endpoints are available at:

http://localhost:8080/swagger-ui.html

![Swagger_UI_1](https://github.com/sorinvisan89/maretha/blob/main/images/swagger-ui-1.png)


![Swagger_UI_1](https://github.com/sorinvisan89/maretha/blob/main/images/swagger-ui-2.png)

### Tests
The tests can be run with: 
```
mvn clean install
```

![System_Tests](https://github.com/sorinvisan89/maretha/blob/main/images/system-tests.png)