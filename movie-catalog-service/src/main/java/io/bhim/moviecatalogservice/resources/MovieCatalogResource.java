package io.bhim.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.bhim.moviecatalogservice.models.CatalogItem;
import io.bhim.moviecatalogservice.models.Movie;
import io.bhim.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(String userId) {

		// This response we're getting from Rating data API
		UserRating userRating = restTemplate.getForObject("http://RATINGS-DATA-SERVICE/ratingsdata/users/" + userId,
				UserRating.class);

		return userRating.getUserRating().stream().map(rating -> {
			Movie m = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + rating.getRating(), Movie.class);
			return new CatalogItem(m.getName(), "Description", rating.getRating());
		}).collect(Collectors.toList());

	}

}
