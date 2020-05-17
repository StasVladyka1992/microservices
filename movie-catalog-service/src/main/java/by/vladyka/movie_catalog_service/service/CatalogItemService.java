package by.vladyka.movie_catalog_service.service;

import by.vladyka.movie_catalog_service.model.CatalogItem;
import by.vladyka.movie_catalog_service.model.Movie;
import by.vladyka.movie_catalog_service.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CatalogItemService {
    @Autowired
    private RestTemplate template;

    //метод должен иметь ту же сигнатуру, как и метод, который он заменит
    //тут показан пример использования Bulk pattern. Что произойдет, если один и тот
    //же метод дергнут все потоки? В таком случае мы вводим ограничение на количество
    //max количество потоков и максимальное количество очередей, ожидающих освобождения
    //потока из ThreadPool
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
    threadPoolKey = "getCatalogPool",
    threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = template.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(rating.getRating(), movie.getName(), movie.getDescription());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem(0, "Movie not found", "");
    }
}
