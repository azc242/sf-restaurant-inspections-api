package com.example.SFRestaurantInspections.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.SFRestaurantInspections.model.Restaurant;

@Repository("fakeDaoRestaurant")
public class FakeRestaurantDataAccessService implements RestaurantDao{
	
	private static List<Restaurant> DB = new ArrayList<>();

	@Override
	public int insertRestaurant(UUID id, Restaurant res) {
		System.out.println("FakeResDataAccessService");
		res.setId(id);
		DB.add(res); // if this starts to fail, create a new Restaurant object instance
		return -1;
	}

	@Override
	public List<Restaurant> selectAllRestaurants() {
		return DB;
	}

	@Override
	public Optional<Restaurant> selectRestaurantById(UUID id) {
		return DB.stream()
				.filter(res -> res.getId().equals(id))
				.findFirst();
	}

	@Override
	public int deleteRestaurantById(UUID id) {
		Optional<Restaurant> res = selectRestaurantById(id);
		if(res.isEmpty()) {
			return 0;
		}
		DB.remove(res.get());
		return 1; // signifies removed restaurant
	}

	@Override
	public int updateRestaurantById(UUID id, Restaurant res) {
		return selectRestaurantById(id)
				.map(r -> {
					int indexOfRestaurantToUpdate = DB.indexOf(r);
					if(indexOfRestaurantToUpdate >= 0) {
						DB.set(indexOfRestaurantToUpdate, res);
						return 1;
					}
					return 0;
				})
				.orElse(0);
	}

}
