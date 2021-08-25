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

# MS-AUTH

Test users are created, also for each user are created and assigned customers with appropriate PIN. In case if the card order will be done using another PIn , error will be thrown.
```
MEHDIYEVCS
username: mehdiyevcs
password: mehdiyevcs

PIN: TEST111, TEST112

COMPANY
username: company
password: company
PIN: TEST113, TEST114
```
### Request Examples:

Request:
``
curl -X POST "http://localhost:8081/api/auth/login" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"password\": \"mehdiyevcs\",  \"username\": \"mehdiyevcs\"}"
``
Response:
``
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6Im1laGRpeWV2Y3MiLCJhdWQiOiJhcGktY2xpZW50cyIsImlzcyI6IkNvbXBhbnkiLCJleHAiOjE2Mjk5MTgyNTh9.0q_jw_GG8yR_3kfYnP9inRB2Xs86XApiuSgSILkYWY6gh4LMhuQR3AFjbfQRfBHki848WY5IydQOx5m8KFtttw"
}
``

# MS-CARD-ORDER

Reqest examples:

### Create Card Order

Request: 
``
curl -X POST "http://localhost:8080/api/card/order/create" -H  "accept: */*" -H  "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6Im1laGRpeWV2Y3MiLCJhdWQiOiJhcGktY2xpZW50cyIsImlzcyI6IkNvbXBhbnkiLCJleHAiOjE2Mjk5MTgyNTh9.0q_jw_GG8yR_3kfYnP9inRB2Xs86XApiuSgSILkYWY6gh4LMhuQR3AFjbfQRfBHki848WY5IydQOx5m8KFtttw" -H  "Content-Type: application/json" -d "{  \"cardHolderFullName\": \"Jalal Mehdiyev\",  \"cardHolderPin\": \"TEST112\",  \"cardType\": \"MC\",  \"codeWord\": \"string\",  \"period\": 12,  \"urgent\": true}"
``
Response:
``
{
  "id": 6,
  "status": "CREATED",
  "cardType": "MC",
  "cardHolderFullName": "Jalal Mehdiyev",
  "period": 12,
  "urgent": true,
  "codeWord": "string",
  "cardHolderPin": "TEST112",
  "createdAt": "2021-08-25T22:52:35.812346",
  "updatedAt": null,
  "username": "anonymous"
}
``

### Submit Card Order
Request
``
curl -X PUT "http://localhost:8080/api/card/order/6/submit" -H  "accept: */*" -H  "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6Im1laGRpeWV2Y3MiLCJhdWQiOiJhcGktY2xpZW50cyIsImlzcyI6IkNvbXBhbnkiLCJleHAiOjE2Mjk5MTgyNTh9.0q_jw_GG8yR_3kfYnP9inRB2Xs86XApiuSgSILkYWY6gh4LMhuQR3AFjbfQRfBHki848WY5IydQOx5m8KFtttw"
``
Response
``
{
  "id": 6,
  "status": "SUBMITTED",
  "cardType": "MC",
  "cardHolderFullName": "Jalal Mehdiyev",
  "period": 12,
  "urgent": true,
  "codeWord": "string",
  "cardHolderPin": "TEST112",
  "createdAt": "2021-08-25T22:52:35.812346",
  "updatedAt": null,
  "username": "anonymous"
}
``

### Get Card Order
Request
``
curl -X GET "http://localhost:8080/api/card/order/6" -H  "accept: */*" -H  "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6Im1laGRpeWV2Y3MiLCJhdWQiOiJhcGktY2xpZW50cyIsImlzcyI6IkNvbXBhbnkiLCJleHAiOjE2Mjk5MTgyNTh9.0q_jw_GG8yR_3kfYnP9inRB2Xs86XApiuSgSILkYWY6gh4LMhuQR3AFjbfQRfBHki848WY5IydQOx5m8KFtttw"
``
Response
``
{
  "id": 6,
  "status": "COMPLETED",
  "cardType": "MC",
  "cardHolderFullName": "Jalal Mehdiyev",
  "period": 12,
  "urgent": true,
  "codeWord": "string",
  "cardHolderPin": "TEST112",
  "createdAt": "2021-08-25T22:52:35.812346",
  "updatedAt": null,
  "username": "anonymous"
}
``

# MS-CARD

### Get Card By Order Id
Request
``
curl -X GET "http://localhost:8082/api/card?orderId=6" -H  "accept: */*"
``
Response
``
{
  "id": 4,
  "orderId": 6,
  "customerId": 12345,
  "createdAt": "2021-08-25T22:53:53.561019",
  "cardType": "MC",
  "cardNumber": "0626043677745089",
  "accountNumber": "0626650917919488"
}
``
