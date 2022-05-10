# Data Access in Spring

## Repositories + JPA

While there are many ways to interface with a database within Spring,
one of the fastest and most intuitive methods is to use Spring Data with JPA.

In this lesson we will start learning about the **J**ava **P**ersistence **A**PI, and how to
work with **JPA** entities in the Spring framework.

---

## Database Setup
**NOTE:** You may skip this step if a Codeup instructor is hosting your database for you

Before we can use the Spring framework's built-in data access abilities, we need
a database user for our application. Configure a new data source in your project
or login into your mysql server using your favorite mysql client (CLI, Intellij, Adminer, etc) and run the following command:

```sql
CREATE DATABASE IF NOT EXISTS movies_db;
```
---
## Configuration

**NOTE:** If a Codeup instructor is hosting your database for you, then configure `application.properties` with the database credentials that were given to you (url, username, and password)

Now we need to configure our data source with Spring.

Add the following to your `application.properties` file, located in
`src/main/resources`:

```
spring.datasource.url=jdbc:mysql://localhost/movies_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=codeup
spring.jpa.show-sql=true
```

Change the values to be specific for your database setup, if needed.

Next, add the following to your `pom.xml` then reload the project:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

The above configuration will include the necessary libraries for our
application, and set our database credentials in the `application.properties`
file so that Spring can access them.

---
## What is JPA?

***JPA (Java Persistence API)*** is a Java specification for accessing, persisting, and managing data between
Java objects and a relational database.

JPA is considered the standard industry approach for **Object to Relational
Mapping (ORM)** in the Java World.

JPA itself is just a specification, not a product; it cannot perform persistence
or anything else by itself. JPA is just a set of interfaces, and requires an
implementation. 

Hibernate is one implementation of the JPA specification, and
will be what we use.

---
## JPA Annotations

Previously, we have created POJOs (Plain old Java objects) as our *models*, that
is, a class that represents data from our database. Now we will use some JPA
annotations to formally specify the mapping of our objects to database tables.

The first step is to annotate our POJOs as JPA entities. 

Like many other dependencies, we must import the packages in order to use their functionalities.

Add `@Entity` over your `Movie` class declaration as such:


```java
import javax.persistence.*;

@Entity
public class Movie {
    /* More code here... */
}
```

Now, hover over that `@Entity` annotation and import the `javax.persistence` package. 

Be *sure* not to accidentally import the Hibernate version of `@Entity`

---
### Primary keys

### `@Id` & `@GeneratedValue`
Each entity has to have a primary key, which you annotate with the `@Id`
annotation.

If you want the database to generate automatically an identifier for each
row, add one more annotation: `@GeneratedValue(strategy = GenerationType.IDENTITY)`. Now, the database is
responsible for determining and assigning the next primary key
(`AUTO_INCREMENT` in MySQL).

```java
@Entity
public class Movie {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
```

Table names are derived from the entity names. Given the class `Movie` with a
simple `@Entity` annotation, the table name would be `movies`.

The table name can be configured via the `@Table` annotation. The `@Table`
annotation provides four attributes, allowing you to override the name of the
table. Typically, you would only provide a substitute table name thus:

```JAVA
@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
```

### Mapping Properties and Fields


### `@Column`
The `@Column` annotation is used to specify the details of the column to which a
field or property will be mapped. Some of the details are schema related, and
therefore apply only if the schema is generated from the annotated files.

You can customize the values of the following attributes:

* `name` allows us to specify the column name. The default value for a column would be
  the name of the property. You'll want to override the default behavior, for instance,
  when you have a column name `last_name`, but the property in your POJO is
  `lastName`.
* `length` permits the maximum size of the column's contents (particularly a string value) to be
  explicitly defined. The column size defaults to 255 characters.
* `nullable` permits the column to be marked `NOT NULL` when the schema is
  generated. The default permits a column's contents to be `null`, ie
  `nullable = true`.
* `unique` permits the column to be marked as containing only unique values.
  This defaults to false.

```java
@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String director;
    
    ...
}
```

This mapping is equivalent to the following MySQL table definition:

```sql
CREATE TABLE movies (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    director VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```

---
## Repositories

Repositories are a construct of the Data Access Layer. Specifically, they are a type of object known as a ***Data Access Object (DAO)***.

You may here 'repository' and 'dao' used interchageably. Just know they both are responsible for using a driver to communicate with the database.



In Spring, the Data Access Layer has a predefined parent class (also called base class) called
`JpaRepository`. 



In the `data` package, create an `Interface` called `MovieRepository`. Have the interface extend `JpaRepository` and define the type of objects it will be manipulating (`<Movie, Long>`), as well as the data type of the entity's id (`Long`).

```java
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
```

Spring Data uses your parameterized JpaRepository definition (`<Movie, Long>`) to help understand the relationsihp between your POJO entity (`Movie`) and a database table (`movies`).

Our ORM also needs to understand the data type of your primary key (in our case, `Long`) for the entity-table relationship. Basically, just match the id's Java data type to what you define in the `JpaRepository` generic parameters (`<T, Id>`).

---
## Hello again, Inheritance!
Thanks to extending `JpaRepository<T, ID>`, we have a set of predefined methods from a large web of classes from which `JpaRepository` inherits.

To get an idea, check out methods defined in the documentation on the following inherited interfaces:

- [PagingAndSortingRepository<T, ID>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html)
- [QueryByExampleExecutor<T>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/QueryByExampleExecutor.html)
- [CrudRespository<T, ID>](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)


Just by defining an interface that extends `JpaRepository`, we can start using
it in our other classes!

By extending `JpaRepository`, we inherit the CRUD functionality that the Spring
framework provides, including methods for retrieving an Iterable Interface (see Further Reading below) with all the posts (`findAll`), a
single post (`getOne`), inserting or updating a post (`save`), and deleting a post (`delete`).

---

## Further Reading

- [JPA Reference](https://www.oracle.com/technical-resources/articles/javaee/marx-jpa.html)
- [JPA Repository](https://www.baeldung.com/spring-data-repositories#jprepository)
- [Defining Custom Query Methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation)
- [Using the `@Query` Annotation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query)
- [The Object Oriented Paradigm of Data Persistence](http://www.javaworld.com/article/2077817/java-se/understanding-jpa-part-1-the-object-oriented-paradigm-of-data-persistence.html)

## Next Up: [Controller Integration](7-controller-integration.md)







