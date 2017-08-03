package com.example.MovieProject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Ethan on 8/2/17.
 */
@Controller
public class MovieController {

    String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb";

    @RequestMapping (path = "/", method = RequestMethod.GET)
    public String home () {
        return "home";
    }

    @RequestMapping (path = "/now-playing", method = RequestMethod.GET)
    public String nowPlaying (Model model) {
        List<Movie> nowPlaying = getMovies();
        model.addAttribute("movies", nowPlaying);
        return "now-playing";
    }

    @RequestMapping (path = "/medium-popular-long-name", method = RequestMethod.GET)
    public String mediumPopular (Model model) {
        List<Movie> medPopMovies = getMovies();
        medPopMovies = medPopMovies.stream()
                .filter(entry -> entry.popularity >= 30)
                .filter(entry -> entry.popularity <= 80)
                .filter(entry -> entry.title.length() >= 10)
                .collect(Collectors.toList());
        model.addAttribute("movies", medPopMovies);
        return "medium-popular-long-name";
    }

    @RequestMapping (path = "/overview-mashup", method = RequestMethod.GET)
    public String mashupString (Model model) {
        // DOESN'T WORK YET
        Random random = new Random();

        List<String> randomSentencesList = new ArrayList<>();
        List<Movie> movieOverviews = getMovies();
        List<String> fiveMovieOverviews = new ArrayList<>();
        String randomSentence = "";
        for (int i=0; i<5; i++) {
            fiveMovieOverviews.add(movieOverviews.get(random.nextInt(movieOverviews.size())).overview);
            randomSentence = fiveMovieOverviews.get(i).split("\\.").toString();
            randomSentencesList.add(randomSentence);
        }
        System.out.println(fiveMovieOverviews);
        System.out.println(randomSentencesList);

        return "overview-mashup";
    }

    public List<Movie> getMovies() {
        RestTemplate restTemplate = new RestTemplate();
        List<Movie> movieList = restTemplate.getForObject(API_URL, ResultsPage.class).getResults();

        return movieList;
    }



}
