# Mecalux - Warehouses and Racks (Flyway)

## :hammer_and_wrench: Flyway

> Flyway is a database migration tool that uses SQL scripts to apply changes to a database. Flyway is an open source tool and is available for a wide range of relational databases, including MySQL, PostgreSQL, Oracle, SQL Server and MariaDB..

> Flyway works as follows:
>
> - Migration scripts are stored in a specific folder. The default folder is db/migration.
> - Each migration script has a unique name in the format Version_Name.sql. For example, the script for version 1 might be called V1__Initial.sql.**
> - Flyway creates a history table in the database. The history table stores information about each migration script that has been executed. **

- **Integrating.** Let's integrate Flyway into our pom.xml and configure Flyway so it can successfully connect to our postges database.
```bash
<project xmlns="...">
    <build>
        <plugins>
            <plugin>
                <groupId>org.flywaydb</groupId>
                <artifactId>flyway-maven-plugin</artifactId>
                <configuration>
                    <url>jdbc:postgresql://localhost:5432/postgres</url>
                    <user>postgres</user>
                    <password>postgres</password>
                </configuration>  
            </plugin>
        </plugins>
    </build>
</project>
```
- **Creating the first migration.** We create the migration directory src/main/resources/db/migration.

Followed by a first migration called src/main/resources/db/migration/V1__Initial.sql. 
In this script we create the tables and the inserts of the initial records.
```bash
create table WAREHOSE (
    id varchar(36) NOT NULL,
    ...
);

insert into WAREHOSE (id, ...
```



- **Migrating the database.** It's now time to execute Flyway to migrate our database:
```bash
mvn flyway:migrate
```

Flyway supports the following basic commands to manage database migrations:

- Info: Prints current status/version of a database schema. It prints which migrations are pending, which migrations have been applied, the status of applied migrations, and when they were applied.
- Migrate: Migrates a database schema to the current version. It scans the classpath for available migrations and applies pending migrations.
- Baseline: Baselines an existing database, excluding all migrations, including baselineVersion. Baseline helps to start with Flyway in an existing database. Newer migrations can then be applied normally.
- Validate: Validates current database schema against available migrations.
- Repair: Repairs metadata table.
- Clean: Drops all objects in a configured schema. Of course, we should never use clean on any production database.
