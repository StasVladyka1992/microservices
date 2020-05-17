package by.vladyka.movie_info_service.resource;

import by.vladyka.movie_info_service.model.Movie;
import by.vladyka.movie_info_service.model.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/movies/")
public class MovieResource {
    @Value("${apiKey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) throws InterruptedException{
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
