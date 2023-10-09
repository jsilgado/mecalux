<style>
.fila-imagenes {
  display: flex;
  justify-content: space-between;
  margin-left: 10px;
  margin-right: 10px;
}
</style>

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
  <img src="https://img.shields.io/badge/Flyway-yellow">
</p>

It is a technical test on SpringBoot java that started with the basic concepts and little by little I have been adding technologies, design patterns and best practices.

## :file_cabinet: Project functionalities

> Management of warehouse and shelving entities (creation, modification, etc.) as well as their relationship.

> Calculation of permutations: An endpoint must be presented in which the possible permutations of racking types in a warehouse are calculated based on possible permutations of racking types in a warehouse according to its family. family of the warehouse. Ex: ["AAA"," AAB", "AAC", ...," CCC"] .

## :hammer_and_wrench: Technologies

### Open API. -> **[openapis.org](https://www.openapis.org/)**
<img src="https://github.com/jsilgado/mecalux/blob/master/docs/images/OpenApi.png" alt="OpenApi" width="80" height="80">

> OpenAPI is a standard specification for describing RESTful APIs. It allows both humans and machines to discover the capabilities of an API without the need to read documentation or understand the implementation.
[Quick Guide](docs/OpenApis.md)


### Flyway. -> **[documentation.red-gate.com (Quick start Maven) ](https://documentation.red-gate.com/fd/quickstart-maven-184127578.html)**
<img src="https://github.com/jsilgado/mecalux/blob/master/docs/images/Flyway.jpg" alt="OpenApi" width="80" height="80" align="center">

> Flyway is a database migration tool that uses SQL scripts to apply changes to a database. Flyway is an open source tool and is available for a wide range of relational databases, including MySQL, PostgreSQL, Oracle, SQL Server and MariaDB..
[Quick Guide](docs/Flyway.md)

### Query JPA Filters. -> **[https://github.com/acoboh/query-filter-jpa/wiki](https://github.com/acoboh/query-filter-jpa/wiki)**
> The QueryFilterJPA Library adds the possibility of creating custom filters with RHS Colon and LHS Brackets with Spring JPA easily. This library is useful for allowing the user to obtain data according to their requirements in an easy way for the programmer. With just a few small configuration classes, users will have the ability to create filters with infinite possibilities.
[Quick Guide](docs/QueryFilterJPA.md)

### **Test Containers** **[Test Cointainers]**
[Quick Guide](docs/TestContainer.md)


## :computer: Building and running Mecalux locally

Building and running Mecalux in your local dev environment is very easy. Be sure you have Git, Node.js, Docker and Maven installed, then follow the directions below. 

### postgres
```bash
docker run --name postgres -it -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:14.0
```

### warehouse-rest
From the command console and accessing the warehouse-rest folder, execute the following commands:

- **Install.** It will install the libraries that the project requires as dependencies.
```bash
mvn install
```
- **Migrate database.** Create the schemas in bbdd and the initial data load.
```bash
mvn flyway:migrate
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
npm run ng serve -o
```
