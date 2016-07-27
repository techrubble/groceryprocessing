#Ripe Fruit Grocery Processing
##Introduction

Simple Java application to scrape Ripe Fruit grocery details and output details in json format
 
##Frameworks/APIs
- Jsoup - html parser
- Apache HttpClient - low 
- Jackson - json processing
- Junit - testing 

##Build and Execution

- mvn clean test - cleans, compiles and executes unit tests
- mvn clean integration-test - as per test plus executes integration tests
- mvn clean package - as per test and builds jar file with dependencies
- java -jar target/groceryprocessing-1.0-SNAPSHOT-jar-with-dependencies.jar http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html - runs application and outputs scraped details

##Build and Execution Dependencies
- Java 8
- Maven 3+
- Internet access to maven repositories