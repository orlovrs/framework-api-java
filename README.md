# framework-api-java

## Idea
The idea of this project is to implement test automation framework for API testing. To reach this goal I choose a path of Page Object pattern. Every Request Object looks like Page Object - it has parameters of request (headers, body, method, query params etc).

There's an object similar to WebDriver. I called it ApiClient. This client knows how to execute requests - how to authorize it, what is a url of service we are testing, what headers are default etc. That's the reason why every back-end service should has its own api client.

Config file are static block of code. It reads env params from .env file. Thus we can manage environment data using .env only.

Models is an objects that are represents the domain of our application. This files has 2 functions. The first is to help QA (who is writing the test) think in context of real life, not of models he has in application. The second one is to separate test logic and implementation. The side effect of this function is that we should support implementation code only in one place.

## Global folder
Contains base classes for all of listed above: ApiClient, ApiRequestObject. In case we're doing tests for several services in one project we just inherit this base classes.

## ToDoList folder
This folder contains all objects to describe ToDoList service (if service has, for example, "Super Service" name then this folder would be named "super-service"). It consists of 4 folders:

- api. It contains service API Client and its helpers. Api Client itself contains information about how we should authorize our requests with domain model and the way we should execute all requests (base url, pre and post conditions etc).
- requests. Contains files that are describing the requests to service (method, relative url, the way we map domain model to request body)
- config. Contains files to store some env-sensitive data for a service (in my case it is admin login and pass)
- models. Contains "business models". In most cases it is just a set of fields that describes our domain model

## Tests
Each test should verify the exact endpoint. The logic of folder structure duplicates endpoint we use. For example to test endpoint GET api/lists/1 we shoul use GetListTest file in todolist/lists/ folder

Test is not "the faceless". We suggest a real user should execute the request so every test has the user or role and api client associated with it. If necessary we can have a large amount of users for a test

Each test has BeforeEach block of code that is responsible for creating user for test and associate api client with the user. For creating a user I applied builder pattern with director extension. You can read more about it here - https://refactoring.guru/design-patterns/builder.

Due to Fluent Api pattern writing tests becomes pretty easy and obvious. You can choose the chapter of api you want to use for a client and then you can easily see what methods are implemented and what are not.

## What I like

- Requests have own API Client and User, so there are no problems with parallel execution and/or number of users we need for a test
- Builder with Director pattern makes easy to write and maintain the code for test data preparation
- Separated request object from api client makes it easy to change the request source
- Using factory pattern for request objects make it easy to maintain multiple versions of API requests and use several versions in tests. You can see an example with LoginApiRequestFactory file and `user can login after registration` test
- We can easily write AfterEach part of scenario to clean up the created test data

## What I do not like

- The list of assertions in tests (suggest to move it to client just to make code more readable and easier to use)
- Need to duplicate common response checks (response status, headers, json path etc). No idea how to solve it for now
- Using hashMap for requests bodies. Just made it to show concept, but it is not a final version. Suggest to use DTOs instead

## Not covered features
This framework has other dependencies and features, but there's no goal to cover all aspects of testing and reporting. So keep in mind the primary goal is to show concept of writing API tests, not to thoroughly describe the final solution. 