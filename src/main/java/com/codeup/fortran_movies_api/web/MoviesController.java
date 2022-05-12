package com.codeup.fortran_movies_api.web;

import com.codeup.fortran_movies_api.data.Director;
import com.codeup.fortran_movies_api.data.DirectorsRepository;
import com.codeup.fortran_movies_api.data.Movie;
import com.codeup.fortran_movies_api.data.MoviesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    private final MoviesRepository moviesRepository;
    private final DirectorsRepository directorsRepository;

    public MoviesController(MoviesRepository moviesRepository, DirectorsRepository directorsRepository){
        this.moviesRepository = moviesRepository;
        this.directorsRepository = directorsRepository;
    }

    // /api/movies/3 <- 3 is the path variable for id
    @GetMapping("{id}")
    public Movie getById(@PathVariable int id) {
        return moviesRepository.findById(id).orElse(null);
    }

    // TODO: put the expected path out to the side of the method annotation
    //  -> this helps to keep track so we don't have to guess if methods conflict on the same path
    @GetMapping("all") // /api/movies/all
    public List<Movie> getAll() {
        return moviesRepository.findAll(); // TODO: findAll() will return a list of objects and is provided by the JpaRepository
    }

    @GetMapping("search") // /api/movies/search?title=<movieTitle>
    public List<Movie> getByTitle(@RequestParam("title") String title){
        // TODO: we need to create the findByTitle() method in our MoviesRepository - magic!
        return moviesRepository.findByTitle(title);
    }

    @GetMapping("search/year") // api/movies/search/year
    public List<Movie> getByYearRange(@RequestParam("startYear") int startYear, @RequestParam("endYear") int endYear){
        // TODO: @RequestParam expects a query parameter in the request URL
        //  to have a param matching what is in the annotation (ie: @RequestParam("startYear"))
        return moviesRepository.findByYearRange(startYear, endYear);
    }

    @GetMapping("search/director")
    public List<Director> getByDirector(@RequestParam("name") String directorName){
        List<Director> directors =  directorsRepository.findByName(directorName);

        return directors;
    }

    @PostMapping // /api/movies POST
    public void create(@RequestBody Movie movie){
        // add to our movies list (fake db)
        moviesRepository.save(movie);
    }

    @PostMapping ("many")// /api/movies/many POST
    public void createMany(@RequestBody List<Movie> movies){ // @RequestBody is very important to knowing how the Request's body maps
        // saveAll() lets you pass a collection as an argument and commit all the objects to the database
        moviesRepository.saveAll(movies);
    }

    @PutMapping
    public void updateOne(@RequestBody Movie movie){
        moviesRepository.save(movie);
    }

    // TODO: make a delete request method here!
    @DeleteMapping("{id}") // api/movies/{id} -> api/movies/3 DELETE
    public void deleteById(@PathVariable int id) throws IOException {
        try {
            moviesRepository.deleteById(id);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No matching movie with ID: " + id);
        }
    }



}