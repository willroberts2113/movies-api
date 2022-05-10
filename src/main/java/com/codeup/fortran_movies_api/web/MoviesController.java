package com.codeup.fortran_movies_api.web;

import com.codeup.fortran_movies_api.data.Movie;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    // Let's set up a temporary backing field to give us a list of movies to work with.
    // We'll remove this once we integrate with the database
    private List<Movie> sampleMovies = setMovies();

    @GetMapping
    public Movie one() {
        return sampleMovies.get(1);
    }

    @GetMapping("all") // Path becomes: /api/movies/all
    public List<Movie> getAll() {
        return sampleMovies;
    }

    @GetMapping("{id}") // Define the path variable to use here
    public Movie getById(@PathVariable int id) { // Actually use the path variable here by annotating a parameter with @PathVariable
        return sampleMovies.stream().filter((movie) -> {
                    return movie.getId() == id; // filter out non-matching movies
                })
                .findFirst() // isolate to first match
                .orElse(null); // prevent errors by returning null... not the greatest practice, but it'll do for now
    }

    @PostMapping // /api/movies POST
    public void create(@RequestBody Movie movie){
        // add to our movies list (fake db)
        sampleMovies.add(movie);
    }

    @PostMapping ("many")// /api/movies/many POST
    public void createMany(@RequestBody List<Movie> movies){ // @RequestBody is very important to knowing how the Request's body maps
        sampleMovies.addAll(movies); // addAll (on the Collection object) allows us to add all the elements from one collection to another in a single line
    }

    // This utility method simply sets up and populates our sampleMovies backing field
    // Will remove once we integrate with the database
    private List<Movie> setMovies() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie(2, "Pulp Fiction", "1994", "Quentin Tarantino",
                "Samuel L. Jackson, Uma Therman, Bruce Willis, John Travolta, Ving Rhames",
                "10", "action, drama, suspense, cult classic, crime",
                "Vincent Vega (John Travolta) and Jules Winnfield (Samuel L. Jackson) are hitmen with a penchant for philosophical discussions. " +
                        "In this ultra-hip, multi-strand crime movie, their storyline is interwoven with those of their boss, " +
                        "gangster Marsellus Wallace (Ving Rhames) ; his actress wife, Mia (Uma Thurman) ; " +
                        "struggling boxer Butch Coolidge (Bruce Willis) ; master fixer Winston Wolfe (Harvey Keitel) and a nervous pair of armed robbers, " +
                        "\"Pumpkin\" (Tim Roth) and \"Honey Bunny\" (Amanda Plummer)."));
        movies.add(new Movie(1, "The Big Lebowski",
                "1995", "The Cohen Bros",
                "Jeff Bridges, John Goodman, Steve Buscemi",
                "idk bro", "comedy, drama?",
                "the dude just wanted to relax and go bowling"));

        return movies;
    }
}
