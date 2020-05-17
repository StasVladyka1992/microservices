package by.vladyka.movie_catalog_service.resource;

import by.vladyka.movie_catalog_service.model.CatalogItem;
import by.vladyka.movie_catalog_service.model.UserRating;
import by.vladyka.movie_catalog_service.service.CatalogItemService;
import by.vladyka.movie_catalog_service.service.UserRatingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/catalog/")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate template;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private UserRatingService userRatingService;

    @Autowired
    private CatalogItemService catalogItemService;

    @GetMapping("{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        exploringDiscoveryClient();

        UserRating userRating = userRatingService.getUserRating(userId);
        return userRating.getRatings().stream().map(this.catalogItemService::getCatalogItem).collect(Collectors.toList());

//        Использование web client более новый способ и он скоро заменит RestTemplate
//        return ratings.stream()
//                .map(rating -> {
//                    Movie movie = webClientBuilder.build()
//                            .get()
//                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                            .retrieve()
//                            .bodyToMono(Movie.class)
//                            .block();
//                    return new CatalogItem(rating.getRating(), movie.getMovieName(), "Test");
//                })
//                .collect(Collectors.toList());

    }

    @HystrixCommand
    private List<CatalogItem> getFallbackCatalog(@PathVariable String userId) {
        return Arrays.asList(new CatalogItem(0, "No movie", ""));
    }

    private void exploringDiscoveryClient() {
        List<ServiceInstance> ratingInfo = discoveryClient.getInstances("rating-info-service");
        List<ServiceInstance> movieInfo = discoveryClient.getInstances("movie-info-service");

        ratingInfo.stream().forEach(serviceInstance -> {
            System.out.println(serviceInstance.getHost());
            System.out.println(serviceInstance.getInstanceId());
            System.out.println(serviceInstance.getMetadata());
            System.out.println(serviceInstance.getPort());
            System.out.println(serviceInstance.getUri());
            System.out.println(serviceInstance.isSecure());
            System.out.println(serviceInstance.getScheme());
        });

    }
}
