# LAPR3 - G33 - 2020/2021

This repository contains didactic artefacts related to the project being developed in the Course Unit LAPR3 edition of 2020-2021 of [Licenciatura em Engenharia Informática (LEI)](http://www.isep.ipp.pt/Course/Course/26) from [Instituto Superior de Engenharia do Porto (ISEP)](http://www.isep.ipp.pt).

#### Table of contents:

* Project Assignment
* Software Engineering
* Report
* References
* Java source files
* Maven Files
* Notes
* Oracle Repository

# Project Assignment

* [Project Assignment ](LAPR3 - Project Assignment [2020-2021] v2.1.pdf)

  

# Software Engineering 

* [Requirements Engineering](Documents/RequirementsEngineering.md)

* [Engineering Analysis](Documents/EngineeringAnalysis.md) 

* [Engineering Design](Documents/EngineeringDesign.md)

  

# Report

### Abstract

### Introduction

### Solution

#### Administrator

**Pharmacies**

* Add Pharmacy

When the administrator adds a Pharmacy he can choose to add a Park for Electric Scooters, for Drones or for both.

**Couriers**

* Add Courier
* Remove Courier

When the administrator adds a Courier he is associated with the pharmacy he will work for. When the administrator removes a Courier everything associated with him is removed from the database.

**Electric Scooters/ Drones**

* Add Vehicle
* Remove Vehicle

When the administrator adds a Vehicle he has to choose if he wants to add an Electric Scooter or a Drone. When the administrator removes a Vehicle everything associated with it is removed from the database.

**Medicines**

* Add Medicine
* Remove Medicine

When the administrator removes a Medicine everything associated with it is removed from the database.

**Deliveries**

* Create Restock Orders Delivery 
* Create Client Orders Delivery 

When the administrator creates a delivery run he has to choose the Pharmacy he wants. If the Pharmacy doesn't have client/restock orders to be delivered a info message is issued.

#### Courier

**Deliveries**

* Pickup Order
* Pickup Scooter
* Park Scooter

#### Client

**Orders**

* Add to Cart
* Remove from Cart
* Checkout

#### System

* Send e-mails
* Calculate necessary energy
* Estimate charge time
* Generate invoice
* Add credits



### Conclusion

# References



# Java source files

Java source and test files are located in folder src.

# Maven file

Pom.xml file controls the project build.

# Notes
In this file, DO NOT EDIT the following elements:

* groupID
* artifactID
* version
* properties

Beside, students can only add dependencies to the specified section of this file.

## Eclipse files

The following files are solely used by Eclipse IDE:

* .classpath
* .project

## IntelliJ Idea IDE files

The following folder is solely used by Intellij Idea IDE :

* .idea

## How was the .gitignore file generated?
.gitignore file was generated based on https://www.gitignore.io/ with the following keywords:

  - Java
  - Maven
  - Eclipse
  - NetBeans
  - Intellij

## How do I use Maven?

### How to run unit tests?

Execute the "test" goals.

`$ mvn test`

### How to generate the javadoc for source code?

Execute the "javadoc:javadoc" goal.

`$ mvn javadoc:javadoc`

This generates the source code javadoc in folder "target/site/apidocs/index.html".

### How to generate the javadoc for test cases code?

Execute the "javadoc:test-javadoc" goal.

`$ mvn javadoc:test-javadoc`

This generates the test cases javadoc in folder "target/site/testapidocs/index.html".

### How to generate Jacoco's Code Coverage Report?

Execute the "jacoco:report" goal.

`$ mvn test jacoco:report`

This generates a jacoco code coverage report in folder "target/site/jacoco/index.html".

### How to generate PIT Mutation Code Coverage?

Execute the "org.pitest:pitest-maven:mutationCoverage" goal.

`$ mvn test org.pitest:pitest-maven:mutationCoverage`

This generates a PIT Mutation coverage report in folder "target/pit-reports/YYYYMMDDHHMI".

### How to combine different maven goals in one step?

You can combine different maven goals in the same command. For example, to locally run your project just like on jenkins, use:

`$ mvn clean test jacoco:report org.pitest:pitest-maven:mutationCoverage`

### How to perform a faster pit mutation analysis?

Do not clean build => remove "clean"

Reuse the previous report => add "-Dsonar.pitest.mode=reuseReport"

Use more threads to perform the analysis. The number is dependent on each computer CPU => add "-Dthreads=4"

Temporarily remove timestamps from reports.

Example:

`$ mvn test jacoco:report org.pitest:pitest-maven:mutationCoverage -DhistoryInputFile=target/fasterPitMutationTesting-history.txt -DhistoryOutputFile=target/fasterPitMutationTesting-history.txt -Dsonar.pitest.mode=reuseReport -Dthreads=4 -DtimestampedReports=false`

### Where do I configure my database connection?

Each group should configure their database connection on file: 

* src/main/resources/application.properties

# Oracle repository

If you get the following error:

```
[ERROR] Failed to execute goal on project 
bike-sharing: Could not resolve dependencies for project 
lapr3:bike-sharing:jar:1.0-SNAPSHOT: 
Failed to collect dependencies at 
com.oracle.jdbc:ojdbc7:jar:12.1.0.2: 
Failed to read artifact descriptor for 
com.oracle.jdbc:ojdbc7:jar:12.1.0.2: 
Could not transfer artifact 
com.oracle.jdbc:ojdbc7:pom:12.1.0.2 
from/to maven.oracle.com (https://maven.oracle.com): 
Not authorized , ReasonPhrase:Authorization Required. 
-> [Help 1]
```

Follow these steps:

https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides

You do not need to set a proxy.

You can use existing dummy Oracle credentials available at http://bugmenot.com.