package com.example.SFRestaurantInspections.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.SFRestaurantInspections.dao.RestaurantDao;
import com.example.SFRestaurantInspections.model.Restaurant;

@Service
public class RestaurantService {
	
	private final RestaurantDao resDao;
	
	@Autowired
	public RestaurantService(@Qualifier("postgres-restaurant") RestaurantDao resDao) {
		this.resDao = resDao;
	}
	
	public int addRestaurant(Restaurant res) {
		return resDao.insertRestaurant(res);
	}
	
	public List<Restaurant> getAllRestaurants() {
		return resDao.selectAllRestaurants();
	}
	
	public Optional<Restaurant> getRestaurantById(UUID id) {
		return resDao.selectRestaurantById(id);
	}
	
	public Optional<Restaurant> getRestaurantByNameAndId(String name, UUID id) {
		return resDao.selectRestaurantByNameAndId(name, id);
	}
	
	public Optional<Restaurant> getRestaurantByNameAndZip(String name, String zip) {
		return resDao.selectRestaurantByNameAndZip(name, zip);
	}
	
	public List<Restaurant> getRestaurantByName(String name) {
		return resDao.selectRestaurantsByName(name);
	}
	
	public List<Restaurant> getRestaurantByZip(String Zip) {
		return resDao.selectRestaurantsByZip(Zip);
	}
	
	public int deleteRestaurant(UUID id) {
		return resDao.deleteRestaurantById(id);
	}
	
	public int updateRestaurant(UUID id, Restaurant res) {
		return resDao.updateRestaurantById(id, res);
	}
}
