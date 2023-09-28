# Mecalux - Warehouses and Racks
<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue">
  <img src="https://img.shields.io/badge/Spring_boot-2.7.16-green"> 
  <img src="https://img.shields.io/badge/Postgres-16-blue">   
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Feign-11.8-red"> 
  <img src="https://img.shields.io/badge/Hibernate-JPA-purple"> 
  <img src="https://img.shields.io/badge/Spring Security-JWT-white">
  <img src="https://img.shields.io/badge/JUnit-Mockito-blue">
</p>

It is a technical test on SpringBoot java that started with the basic concepts and little by little I have been adding technologies, design patterns and best practices.

## :hammer: Project functionalities

> Management of warehouse and shelving entities (creation, modification, etc.) as well as their relationship.

> Calculation of permutations: An endpoint must be presented in which the possible permutations of racking types in a warehouse are calculated based on possible permutations of racking types in a warehouse according to its family. family of the warehouse. Ex: ["AAA"," AAB", "AAC", ...," CCC"] .

## :computer: Building and running Mecalux locally

### postgres
```bash
docker run --name postgres -it -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:16.0
```

### warehouse-rest
From the command console and accessing the warehouse-rest folder, execute the following commands:

- **Install.** It will install the libraries that the project requires as dependencies.
```bash
mvn install
```
- **Run.** Will raise our rest api.
```bash
mvn spring-boot:run
```

To check that it has been deployed correctly, go to the address: http://localhost:8080/swagger-ui/index.html

### mecalux-app
From the command console and accessing the mecalux-app folder, execute the following commands:
- **Install.** It will install the libraries that the project requires as dependencies.
```bash
npm install
```
- **Run.** Will open an instance of the browser with our application running..
```bash
ng serve -o
```
