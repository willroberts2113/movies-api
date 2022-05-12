package com.codeup.fortran_movies_api.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    // TODO: You will need to create the directors table and add a director before completing this part
    // TODO: How can we see the list of movies in a response?
    @OneToMany(mappedBy = "director") // 1 director can be associated with many movies
    // mappedBy indicates which field on the Movie POJO links the Director and Movie together
    @JsonIgnoreProperties("director")
    private List<Movie> directedMovies;

    public Director(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Director() {
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

    public List<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(List<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    @Override
    public String toString() {
        return "Director{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
