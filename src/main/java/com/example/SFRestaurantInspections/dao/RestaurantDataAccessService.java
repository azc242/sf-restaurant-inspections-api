package com.example.SFRestaurantInspections.dao;

import java.util.ArrayList;
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
			
			Restaurant r = new Restaurant(id, name, zip, address, phone);
			return r;
		});
	}

	@Override
	public Optional<Restaurant> selectRestaurantById(UUID id) {
//		final String sql = "SELECT * FROM restaurant WHERE id = ?"; 
		final String sql = "SELECT r.*, i.* FROM restaurant r LEFT JOIN inspection i ON i.restaurant_id = r.id WHERE r.id = ?";
		List<Restaurant> res = new ArrayList<Restaurant>();
		@SuppressWarnings("deprecation")
		List<Restaurant> r = jdbcTemplate.query(
				sql, 
				new Object[] {id}, 
				(resultSet, i) -> {

			boolean restaurantAlreadyExists = false;
			for(Restaurant temp : res) {
				String name = resultSet.getString("name");
				String zip = resultSet.getString("zip");
				if(temp.getName().equals(name) && temp.getZip().equals(zip)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
					restaurantAlreadyExists = true;
				}
			}
			
			if(!restaurantAlreadyExists) {
				String name = resultSet.getString("name");
				String zip = resultSet.getString("zip");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				
				Restaurant temp = new Restaurant(id, name, zip, address, phone);
				Date date = new Date(resultSet.getString("date"));
				Integer score = resultSet.getInt("score");
				String violation = resultSet.getString("violation");
				String risk = resultSet.getString("risk");
				temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
				res.add(temp);
//				return temp;
			}
			return null;
		});
		
		Restaurant found = res.size() > 0 ? res.get(0) : null;

		return Optional.ofNullable(found);
	}


	@Override
	public List<Restaurant> selectRestaurantsByName(String name) {
//		String sql = "SELECT id, name, zip, address, phone FROM restaurant WHERE name = ?"; 
		final String sql = "SELECT r.*, i.* FROM restaurant r LEFT JOIN inspection i ON i.restaurant_id = r.id WHERE r.name = ?";
		
		List<Restaurant> res = new ArrayList<Restaurant>();
		@SuppressWarnings("deprecation")
		List<Restaurant> r = jdbcTemplate.query(
				sql, 
				new Object[] {name}, 
				(resultSet, i) -> {
			boolean restaurantAlreadyExists = false;
			for(Restaurant temp : res) {
//				String name = resultSet.getString("name");
				String zip = resultSet.getString("zip");
				if(temp.getName().equals(name) && temp.getZip().equals(zip)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
					restaurantAlreadyExists = true;
				}
			}
			
			if(!restaurantAlreadyExists) {
//				String name = resultSet.getString("name");
				String zip = resultSet.getString("zip");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				
				Restaurant temp = new Restaurant(UUID.fromString(resultSet.getString("id")), name, zip, address, phone);
				if(!(resultSet.getString("date") == null)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
				}
				res.add(temp);
				return temp;
			}
			return null;
		});
		
		return res;
		
//		return jdbcTemplate.query(sql, (resultSet, i) -> {
//			UUID restaurant_id = UUID.fromString(resultSet.getString("id"));
//			String zip = resultSet.getString("zip");
//			String address = resultSet.getString("address");
//			String phone = resultSet.getString("phone");
//			return new Restaurant(restaurant_id, name, zip, address, phone);
//		});
	}

	@Override
	public List<Restaurant> selectRestaurantsByZip(String zip) {
		final String sql = "SELECT id, name, zip, address, phone FROM restaurant WHERE zip = ?"; 
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			UUID restaurant_id = UUID.fromString(resultSet.getString("id"));
			String name = resultSet.getString("name");
			String address = resultSet.getString("address");
			String phone = resultSet.getString("phone");
//			List<Inspection> = resultSet.getString("inspection");
			return new Restaurant(restaurant_id, name, zip, address, phone);
		});
	}

	@Override
	public int deleteRestaurantById(UUID id) {
		String sql = "" +
                "DELETE FROM restaurant " +
                "WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int updateRestaurantById(UUID id, Restaurant res) {
		String sql = "" +
				"UPDATE restaurant " +
                "SET name = ?, " +
                "zip = ?, " +
                "address = ?, " + 
                "phone = ? " +
                "WHERE id = ?";
		return jdbcTemplate.update(sql, res.getName(), res.getZip(), res.getAddress(), res.getPhone(), id);
	}
}
