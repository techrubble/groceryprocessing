Introduction

Simple Java application to scrape Ripe Fruit grocery details and out details in json format
 
Frameworks/APIs
- Jsoup - html parser
- Jackson - json processing
- Junit - testing

Build and Execution

- mvn clean test - cleans, compiles and executes unit tests
- mvn clean integration-test - as per test plus executes integration tests
- mvn clean package - as per integration-test and builds jar file
- java -jar target/fff fff - runs application and outpust scraped details

Build and Execution Dependencies
- Java 8
- Maven 3+
- Internet access to maven repositories