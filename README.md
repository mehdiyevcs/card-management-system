To start project,
Setup the necessary infrastructure for microservices

```
$ cd infra/
$ docker-compose up -d
```

After build necessary infra for microservices,
To run the microservices, use following commands

```
$ ./gradlew :CARD-STREAM:bootRun

$ ./gradlew :MS-ACCOUNT:bootRun

$ ./gradlew :MS-CARD:bootRun

$ ./gradlew :MS-CUSTOMER:bootRun

$ ./gradlew :MS-EVENT:bootRun
```


```

```

FYI: You can access appropriate microservice swagger
```
CardOrder Service:
http://localhost:8080/swagger-ui.html/

Customer Service
http://localhost:8082/swagger-ui.html/

Auth Service
http://localhost:8081/swagger-ui.html/

Card Rest API
http://localhost:8083/swagger-ui.html/

```
