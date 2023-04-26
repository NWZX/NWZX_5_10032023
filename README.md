# YOGA APP

This is the README file for the Yoga app. The app is a web-based application built using Java 8 and NodeJS 16, with a MySQL Server 8 database. The app allows users to manage their yoga practice, schedule, and progress.

## Requirements
Before installing and running the Yoga app, make sure you have the following requirements installed on your machine:

 - JAVA 8
 - NodeJS 16
 - MySQL Server 8

## Test Libraries
The Yoga app uses the following test libraries:

### Frontend
 - Jest
### Backend
 - JUnit 5
 - Mockito
 - Jacoco


## Installation

To install and run the app, follow these steps:

1. Set up a MySQL Server and create a database named "estate".

2. Import the file "ressources/sql/script.sql" to create the schema.

3. For the frontend (VSCode):

    a. Navigate to the "front" directory.
    
    b. Run the following commands:
    
       ```
       npm install
       npm run start
       ```

4. For the backend (IntelliJ):

    a. Edit the file "back/src/main/resources/application.properties" with your database credentials.

        ```
        spring.datasource.username=YOUR USERNAME
        spring.datasource.password=YOUR PASSWORD
        ```
    
    b. Run the following command:
    
       ```
       cd back
       mvn clean install
       ```

## Testing

To run the tests for the Yoga app, follow these steps:

### Frontend

1. Navigate to the "front" directory.

2. Run the following command:

```
npm run test
```

3. Test coverage is available in the folder "front/coverage/lcov-report/index.html".

### Backend

1. Navigate to the "back" directory.

2. Run the following command:

```
mvn clean test
```

3. Test coverage is available in the folder "back/target/site/jacoco/index.html".

### Integration

To run integration tests:

1. Start your MySQL server.

2. Launch the backend.

3. Navigate to the "front" directory.

4. Run the following command:

```
npm run e2e:coverage
```

5. Test coverage is available in your terminal.


