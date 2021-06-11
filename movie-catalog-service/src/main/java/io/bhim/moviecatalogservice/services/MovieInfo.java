package io.bhim.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.bhim.moviecatalogservice.models.CatalogItem;
import io.bhim.moviecatalogservice.models.Movie;
import io.bhim.moviecatalogservice.models.Rating;

@Service
public class MovieInfo {
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie m = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + rating.getRating(), Movie.class);
		return new CatalogItem(m.getName(), "Description", rating.getRating());
	}

	public CatalogItem getFallBackCatalogItem(Rating rating) {
		return new CatalogItem("Movie not found", "", rating.getRating());
	}
}
