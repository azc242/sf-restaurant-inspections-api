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
	public RestaurantService(@Qualifier("fakeDaoRestaurant") RestaurantDao resDao) {
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
	
	public int deleteRestaurant(UUID id) {
		return resDao.deleteRestaurantById(id);
	}
	
	public int updateRestaurant(UUID id, Restaurant res) {
		return resDao.updateRestaurantById(id, res);
	}
}
