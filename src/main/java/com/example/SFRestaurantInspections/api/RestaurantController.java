package com.example.SFRestaurantInspections.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SFRestaurantInspections.model.Restaurant;
import com.example.SFRestaurantInspections.service.RestaurantService;
import com.example.SFRestaurantInspections.dao.RestaurantDao;

@RequestMapping("api/v1/restaurant")
@RestController
public class RestaurantController {
	private final RestaurantService resService;
	
	@Autowired
	public RestaurantController(RestaurantService resService) {
		this.resService = resService;
	}
	
	@PostMapping
	public void addRestaurant(@Valid @NonNull @RequestBody Restaurant res) {
		resService.addRestaurant(res);
	}
	
	@GetMapping
	public List<Restaurant> getAllRestaurants() {
		return resService.getAllRestaurants();
	}
	
	@GetMapping(path = "{id}")
	public Restaurant getRestaurantById(@PathVariable("id") UUID id) {
		return resService.getRestaurantById(id)
				.orElse(null);
	}
	
	@GetMapping(path = "{id}", params = {"name"})
	public Restaurant getRestaurantByNameAndId(@RequestParam String name, @PathVariable("id") UUID id) {
		return resService.getRestaurantByNameAndId(name, id)
				.orElse(null);
	}
	
	@GetMapping(params = {"name", "zip"})
	public Restaurant getRestaurantByNameAndZip(@RequestParam String name, @RequestParam String zip) {
		return resService.getRestaurantByNameAndZip(name, zip)
				.orElse(null);
	}
	
	@GetMapping(params = {"name"})
	public List<Restaurant> getRestaurantByName(@RequestParam String name) {
		return resService.getRestaurantByName(name);
	}
	
	@GetMapping(params = {"zip"})
	public List<Restaurant> getRestaurantByZip(@RequestParam String zip) {
		return resService.getRestaurantByZip(zip);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteRestaurantById(@PathVariable("id") UUID id) {
		resService.deleteRestaurant(id);
	}
	
	@PutMapping(path = "{id}")
	public void updateRestaurant(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Restaurant res) {
		resService.updateRestaurant(id, res);
	}
}
