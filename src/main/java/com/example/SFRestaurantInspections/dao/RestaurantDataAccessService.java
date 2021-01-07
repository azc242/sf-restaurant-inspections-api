package com.example.SFRestaurantInspections.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SFRestaurantInspections.model.Date;
import com.example.SFRestaurantInspections.model.Inspection;
import com.example.SFRestaurantInspections.model.Restaurant;

@Repository("postgres-restaurant")
public class RestaurantDataAccessService implements RestaurantDao{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public RestaurantDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertRestaurant(UUID id, Restaurant res) {
		res.setId(id);
		String sql = "" + 
				"INSERT INTO restaurant (" + 
				" id," + 
				" name," + 
				" zip," + 
				" address," + 
				" phone) " + 
				" VALUES (?, ?, ?, ?, ?)";
		
		return jdbcTemplate.update(
                sql,
                res.getId(),
                res.getName(),
                res.getZip(),
                res.getAddress(),
                res.getPhone()
		);
	}

	@Override
	public List<Restaurant> selectAllRestaurants() {
		final String sql = "SELECT id, name, zip, address, phone FROM restaurant"; 
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			UUID id = UUID.fromString(resultSet.getString("id"));
			String name = resultSet.getString("name");
			String zip = resultSet.getString("zip");
			String address = resultSet.getString("address");
			String phone = resultSet.getString("phone");
			
			return new Restaurant(id, name, zip, address, phone);
		});
	}

	@Override
	public Optional<Restaurant> selectRestaurantById(UUID id) {
		final String sql = "SELECT * FROM restaurant WHERE id = ?"; 
		List<Restaurant> res = jdbcTemplate.query(
				sql, 
				new Object[] {id}, 
				(resultSet, i) -> {
			UUID restaurant_id = UUID.fromString(resultSet.getString("id"));
			String name = resultSet.getString("name");
			String zip = resultSet.getString("zip");
			String address = resultSet.getString("address");
			String phone = resultSet.getString("phone");
			return new Restaurant(restaurant_id, name, zip, address, phone);
		});
		Restaurant found = res.size() > 0 ? res.get(0) : null;
		return Optional.ofNullable(found);
	}

	@Override
	public List<Restaurant> selectRestaurantsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Restaurant> selectRestaurantsByZip(String zip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteRestaurantById(UUID id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateRestaurantById(UUID id, Restaurant res) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
