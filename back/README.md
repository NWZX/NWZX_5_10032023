# Yoga App Backend

## Description
The backend of the Yoga app is a web-based application built using Java 8 and MySQL Server 8. It allows users to manage their yoga practice, schedule, and progress.

## Requirements
Before installing and running the backend, make sure you have the following requirements installed on your machine:

* Java 8
* MySQL Server 8

## Test Libraries
The Yoga app uses the following test libraries for the backend:

* JUnit 5
* Mockito
* Jacoco

## Installation

To install and run the backend, follow these steps:

1. Set up a MySQL Server and create a database named "estate".

2. Import the file "ressources/sql/script.sql" to create the schema.

3. Edit the file "back/src/main/resources/application.properties" with your database credentials:

    ```
    spring.datasource.username=YOUR USERNAME
    spring.datasource.password=YOUR PASSWORD
    ```

4. Navigate to the "back" directory.

5. Run the following command:
    
    ```
    mvn clean install
    ```

## Testing
To run the tests for the backend, follow these steps:

1. Navigate to the "back" directory.

2. Run the following command:
        
    ```
    mvn clean test
    ```
3. Test coverage is available in the folder "back/target/site/jacoco/index.html".
