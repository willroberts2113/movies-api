# Controller Integration & Dependency Injection

Dependency injection means that we will ***inject*** a class' ***dependencies*** instead of instantiating them at the point of use.

Behind the scenes, Spring (and many other frameworks) create what is called a **dependency injection container** to store instances of our objects which can be called upon whenever we need without having to use the `new` keyword.

Using DI (dependency injection) can be done as simply as follows:

```java
public class MoviesController {
    // ...
    private final MovieRepository movieRepository;
    
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    // ...
}
```

We can use DI in most of the classes in our Spring
application. We can even inject services into other services! 

This is how you can use it in order to get the list of all Movies.

```java
import com.codeup.restblog.data.PostRepository;

public class MoviesController {

    // These two next steps are often called dependency injection, 
    // where we create a Repository instance and 
    // initialize it in the controller class constructor.
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        movieRepository.save(movie); // BOOM!
    }

    // ...
}
```
Now THAT was easy, huh? 

## The Moment of Truth.

Now, it's time to spin up your application! 

1. Start it, then check your database to see if the `movies` table was created!

2. Manually insert a few movie records on your `POST` route.

3. Use Swagger or Postman to fetch all movies and fetch one movie.

***If you encounter exceptions, be sure to observe the stacktrace, logging statements, and debug the application with breakpoints***
        
Congratulations! You have connected your `Movie` Java classes to your database using JPA!

---
### Further Reading
- [What is Dependency Injection?](http://stackoverflow.com/questions/130794/what-is-dependency-injection)
- [Dependency Injection](https://en.wikipedia.org/wiki/Dependency_injection)
- [Spring Beans and dependency injection](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html)

## Next Up: [Seeding the Database](8-seeding-the-database.md)
