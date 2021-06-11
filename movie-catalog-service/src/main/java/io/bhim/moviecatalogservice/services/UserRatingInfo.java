package io.bhim.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.bhim.moviecatalogservice.models.Rating;
import io.bhim.moviecatalogservice.models.UserRating;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallBackUserRating")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		return restTemplate.getForObject("http://RATINGS-DATA-SERVICE/ratingsdata/user/" + userId, UserRating.class);
	}

	public UserRating getFallBackUserRating(@PathVariable("userId") String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
