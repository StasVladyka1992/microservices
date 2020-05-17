package by.vladyka.movie_catalog_service.service;

import by.vladyka.movie_catalog_service.model.Rating;
import by.vladyka.movie_catalog_service.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserRatingService {
    @Autowired
    RestTemplate template;

    //метод должен иметь ту же сигнатуру, как и метод, который он заменит
    @HystrixCommand(fallbackMethod = "getFallbackUserRating", commandProperties = {
            //конфиги работают, но лучше указывать более длительные промежутки.
            //работает. делал Thread.sleep в сервисе, который вызывался.
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "100"),
            //не посылаются новые запросы, если три последних запроса закончились с ошибкой.
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000")
    })
    public UserRating getUserRating(@PathVariable String userId) {
        return template.getForObject("http://rating-info-service/ratingsdata/users/" + userId, UserRating.class);
    }

    private UserRating getFallbackUserRating(@PathVariable String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Collections.singletonList(new Rating("0", 0)));
        return userRating;
    }
}
