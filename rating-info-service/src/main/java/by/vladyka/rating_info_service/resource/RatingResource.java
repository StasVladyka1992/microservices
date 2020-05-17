package by.vladyka.rating_info_service.resource;

import by.vladyka.rating_info_service.model.Rating;
import by.vladyka.rating_info_service.model.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/ratingsdata/")
public class RatingResource {
    @GetMapping("{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping("users/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) throws InterruptedException {
        return new UserRating(Arrays.asList(
                new Rating("100", 4),
                new Rating("200", 3)
        ));
    }
}
