# 1. go get the json file from glitch
# 2. copypasta into a new json file under /resources

# --> You may need to establish a connection to your localhost db here

# 3. create the movies_db

# 4. use the movies_db

# 5. drop the table(s) to which no other tables are dependent (none at first)

# 6. map the json movie properties to movies table columns
# --> start with just a movies table with all the columns found in the movie json properties

# 6a. Run the script to make sure it works

# 7. refactor to extract the directors to a new table with just an id and name
# --> change the movies table to reference the directors table via Foreign Key
# --> now that movies is dependent on directors, you need to move directors above movies in the script

# 8. Go add DROP IF EXIST statements for movies and directors

# 9. RUN IT!

# 1. go get the json file from glitch

# 2. copy paste into a new json file under /resources

# 3. create the movies_db
create database if not exists movies_db;

use movies_db;

drop table if exists movie_genre;
drop table if exists movie_actor;
drop table if exists actors;
drop table if exists genres;
drop table if exists movies; #drop the table(s) to which no other tables are dependent
drop table if exists directors;

# 4. map the json movie properties to movies table columns
# --> start with just a movies table with all the columns found in the movie json properties

create table if not exists directors
(
    id   int unsigned not null auto_increment primary key,
    name varchar(120)
);

describe directors;


create table if not exists movies
(
    id       int unsigned not null auto_increment primary key,
    title    varchar(255),
    year     char(4),
    plot     text,
    poster   text,
    rating   char(1),
    director_id int unsigned not null,
    foreign key (director_id) references directors(id)
);

describe movies;


create table if not exists genres
(
    id   int unsigned not null auto_increment primary key,
    name varchar(120)
);

describe genres;


create table if not exists movie_genre
(
    movie_id int unsigned not null,
    genre_id int unsigned not null,
    foreign key (movie_id) references movies(id),
    foreign key (genre_id) references genres(id)

);

describe movie_genre;

create table if not exists actors
(
    id int unsigned not null auto_increment primary key,
    name varchar(255)
);

describe actors;

create table if not exists movie_actor
(
    movie_id int unsigned not null,
    actor_id int unsigned not null,
    foreign key (movie_id) references movies (id),
    foreign key (actor_id) references actors (id)
);

describe movie_actor;


