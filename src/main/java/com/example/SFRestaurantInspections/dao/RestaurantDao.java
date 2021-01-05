package com.example.SFRestaurantInspections.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.SFRestaurantInspections.model.Restaurant;

public interface RestaurantDao {
	int insertRestaurant(UUID id, Restaurant res);
	
	default int insertRestaurant(Restaurant res) {
		UUID id = UUID.randomUUID();
		return insertRestaurant(id, res);
	}
	
	List<Restaurant> selectAllRestaurants();
	
	Optional<Restaurant> selectRestaurantById(UUID id);
	
	int deleteRestaurantById(UUID id);
	
	int updateRestaurantById(UUID id, Restaurant res);

}
