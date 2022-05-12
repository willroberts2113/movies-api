package com.codeup.fortran_movies_api.data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    // TODO: to reflect the idea that MANY genres can be associated with MANY movies, we need to create a field for a list of movies!
    //  -> @ManyToMany initially signals this relationship
    //  -> @JoinTable (with the below attributes) signals to Spring/Hibernate to NOT do some really weird stuff
    //  -> We're basically being super explicit about where and how the join table should be defined
    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns =
            @JoinColumn(name = "genre_id", referencedColumnName = "id"), // the movie_genre genre_id column is a foreign key referencing movies (id) (think that it's just like our FOREIGN KEY SQL statement)
            inverseJoinColumns =
            @JoinColumn(name = "movie_id", referencedColumnName = "id") // the movie_id is a foreign key referencing movies (id)
    )
    private List<Movie> movies;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TODO: DON'T FORGET YOUR GETTER! Or else, the list of movies won't serialize
    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}