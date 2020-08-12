# Web Quiz Engine
### Web Quiz Engine - a multi-users web service for creating and solving quizzes using REST API, an embedded database, security, and other technologies. Web Quiz Engine implemented in Java using:
+ Spring Boot;
+ Spring MVC;
+ Spring Security;
+ H2 Database Engine;
+ Gradle.


## Register a user
Basic auth was created using Spring Security. The passwords are encrypted and stored in the database using BCrypt.
To register a new user, the client needs to send a JSON with email and password via **POST** request to **/api/register**:
```json
{
  "email": "test@gmail.com",
  "password": "secret"
}
```

## Create a new quiz
To create a new quiz, the client needs to send a JSON as the request's body via **POST** to **/api/quizzes**.
Here is a new JSON quiz as an example:
```json
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```
## Get a quiz by id
To get a quiz by id, the client sends the **GET** request to **/api/quizzes/{id}**.

## Get all quizzes with paging
To get all existing quizzes in the service, the client sends the **GET** request to **/api/quizzes**.
The API support the navigation through pages by passing the page parameter (**/api/quizzes?page=1**). The first page is 0 since pages start from zero.
The response contains a JSON with quizzes (inside content) and some additional metadata:
```json
{
  "totalPages":1,
  "totalElements":3,
  "last":true,
  "first":true,
  "sort":{ },
  "number":0,
  "numberOfElements":3,
  "size":10,
  "empty":false,
  "pageable": { },
  "content":[
      {"id":102,"title":"Test 1","text":"Text 1","options":["a","b","c"]},
      {"id":103,"title":"Test 2","text":"Text 2","options":["a", "b", "c", "d"]},
      {"id":202,"title":"The Java Logo","text":"What is depicted on the Java logo?",
      "options":["Robot","Tea leaf","Cup of coffee","Bug"]}
  ]
}
```
## Solving a quiz
To solve a quiz, the client sends the **POST** request to **/api/quizzes/{id}/solve** with a JSON that contains the indexes of all chosen options as the answer. This looks like a regular JSON object with key "answer" and value as the array: {"answer": [0,2]}.
The service returns a JSON with two fields: success (true or false) and feedback (just a string). There are three possible responses.
+ If the passed answer is correct:
    
```json
{"success":true,"feedback":"Congratulations, you're right!"}
```
+ If the answer is incorrect:
```json
{"success":false,"feedback":"Wrong answer! Please, try again."}
```
+ If the specified quiz does not exist, the server returns the 404 (Not found) status code.

## Get all completions of quizzes with paging

Service providing operation for getting all completions of quizzes for a specified user by sending the **GET** request to **/api/quizzes/completed** together with the user auth data.
A response is separated by pages since the service may return a lot of data. It contains a JSON with quizzes (inside content) and some additional metadata.
Here is a response example:
```json
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":103,"completedAt":"2019-10-29T21:13:53.779542"},
    {"id":102,"completedAt":"2019-10-29T21:13:52.324993"},
    {"id":101,"completedAt":"2019-10-29T18:59:58.387267"},
    {"id":101,"completedAt":"2019-10-29T18:59:55.303268"},
    {"id":202,"completedAt":"2019-10-29T18:59:54.033801"}
  ]
}
```

## Delete a quiz
A user can delete their quiz by sending the **DELETE** request to **/api/quizzes/{id}**.

<br>

### This web service is the implementation of the [Web Quiz Engine project](https://hyperskill.org/projects/91) from [JetBrains Academy](https://hyperskill.org/)

