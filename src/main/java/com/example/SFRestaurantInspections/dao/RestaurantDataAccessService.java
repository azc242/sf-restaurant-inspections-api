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
		
		String queryForExistingRestaurant = "" +
				"SELECT id FROM restaurant WHERE LOWER(name) = ? AND LOWER(zip) = ?";
		
		try {
			@SuppressWarnings("deprecation")
			UUID foundId = jdbcTemplate.queryForObject(queryForExistingRestaurant, new Object[] {res.getName().toLowerCase(), res.getZip().toLowerCase()}, 
					(resultSet, i) -> {
				UUID personId = UUID.fromString(resultSet.getString("id"));
				return personId;
			});
			// if we make it here, then there was a matching restaurant so we should just use the ID and insert new inspections
			return -1;
		}
		catch (Exception e) {
			// getting here means there was no restaurant that matched the same name and ZIP code
			// which means we can insert a new restaurant
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
	}

	@Override
	public List<Restaurant> selectAllRestaurants() {
		final String sql = "SELECT r.*, i.* FROM restaurant r LEFT JOIN inspection i ON i.restaurant_id = r.id";
		List<Restaurant> res = new ArrayList<Restaurant>();
		List<Restaurant> r = jdbcTemplate.query(
				sql, 
				(resultSet, i) -> {
			boolean restaurantAlreadyExists = false;
			for(Restaurant temp : res) {
				String name1 = resultSet.getString("name");
				String zip1 = resultSet.getString("zip");
				String d = resultSet.getString("date");
				if(d != null && temp.getName().equals(name1) && temp.getZip().equals(zip1)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
					restaurantAlreadyExists = true;
				}
			}
			
			if(!restaurantAlreadyExists) {
				String name1 = resultSet.getString("name");
				String zip1 = resultSet.getString("zip");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				
				Restaurant temp = new Restaurant(UUID.fromString(resultSet.getString("id")), name1, zip1, address, phone);
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
	}

	@Override
	public Optional<Restaurant> selectRestaurantById(UUID id) {
		// query requires exact ID match since this function returns just one Restaurant, if it exists
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
			}
			return null;
		});
		
		Restaurant found = res.size() > 0 ? res.get(0) : null;

		return Optional.ofNullable(found);
	}
	
	@Override
	public Optional<Restaurant> selectRestaurantByNameAndId(String name, UUID id) {
		name = name.toLowerCase();
		// query requires exact ID match since this function returns just one Restaurant, if it exists
		final String sql = "SELECT r.*, i.* FROM restaurant r LEFT JOIN inspection i ON i.restaurant_id = r.id WHERE LOWER(r.name) = ? AND r.id = ?";
		List<Restaurant> res = new ArrayList<Restaurant>();
		@SuppressWarnings("deprecation")
		List<Restaurant> r = jdbcTemplate.query(
				sql, 
				new Object[] {name, id}, 
				(resultSet, i) -> {

			boolean restaurantAlreadyExists = false;
			for(Restaurant temp : res) {
				String zip = resultSet.getString("zip");
				String name1 = resultSet.getString("name");
				if(name1 != null) name1 = name1.toLowerCase(); 
				if(temp.getName().toLowerCase().equals(name1) && temp.getZip().equals(zip)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
					restaurantAlreadyExists = true;
				}
			}
			
			if(!restaurantAlreadyExists) {
				String zip = resultSet.getString("zip");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				String name1 = resultSet.getString("name");
				
				Restaurant temp = new Restaurant(id, name1, zip, address, phone);
				try {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
				} catch(Exception e) {
					// do nothing, retaurant had no inspections
				}
				res.add(temp);
			}
			return null;
		});
		
		Restaurant found = res.size() > 0 ? res.get(0) : null;

		return Optional.ofNullable(found);
	}
	
	@Override
	public Optional<Restaurant> selectRestaurantByNameAndZip(String name, String zip) {
		name = name.toLowerCase();
		// query requires exact ID match since this function returns just one Restaurant, if it exists
		final String sql = "SELECT r.*, i.* FROM restaurant r LEFT JOIN inspection i ON i.restaurant_id = r.id WHERE LOWER(r.name) = ? AND r.zip = ?";
		List<Restaurant> res = new ArrayList<Restaurant>();
		@SuppressWarnings("deprecation")
		List<Restaurant> r = jdbcTemplate.query(
				sql, 
				new Object[] {name, zip}, 
				(resultSet, i) -> {

			boolean restaurantAlreadyExists = false;
			for(Restaurant temp : res) {
				String name1 = resultSet.getString("name");
				if(name1 != null) name1 = name1.toLowerCase(); 
				if(temp.getName().toLowerCase().equals(name1) && temp.getZip().equals(zip)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
					restaurantAlreadyExists = true;
				}
			}
			
			if(!restaurantAlreadyExists) {
				UUID id = UUID.fromString(resultSet.getString("id"));
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				String name1 = resultSet.getString("name");
				
				Restaurant temp = new Restaurant(id, name1, zip, address, phone);
				try {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
				} catch(Exception e) {
					// do nothing, retaurant had no inspections
				}
				res.add(temp);
			}
			return null;
		});
		
		Restaurant found = res.size() > 0 ? res.get(0) : null;

		return Optional.ofNullable(found);
	}

	@Override
	public List<Restaurant> selectRestaurantsByName(String name) {
		name = name.toLowerCase();
//		String sql = "SELECT id, name, zip, address, phone FROM restaurant WHERE name = ?"; 
		final String sql = "SELECT r.*, i.* FROM restaurant r LEFT JOIN inspection i ON i.restaurant_id = r.id WHERE LOWER(r.name) LIKE '%' || ? || '%'";
		
		List<Restaurant> res = new ArrayList<Restaurant>();
		@SuppressWarnings("deprecation")
		List<Restaurant> r = jdbcTemplate.query(
				sql, 
				new Object[] {name}, 
				(resultSet, i) -> {
			boolean restaurantAlreadyExists = false;
			for(Restaurant temp : res) {
				String name1 = resultSet.getString("name");
				String zip = resultSet.getString("zip");
				if(temp.getName().equals(name1) && temp.getZip().equals(zip)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
					restaurantAlreadyExists = true;
				}
			}
			
			if(!restaurantAlreadyExists) {
				String name1 = resultSet.getString("name");
				String zip = resultSet.getString("zip");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				
				Restaurant temp = new Restaurant(UUID.fromString(resultSet.getString("id")), name1, zip, address, phone);
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
	}

	@Override
	public List<Restaurant> selectRestaurantsByZip(String zip) {
		zip = zip.toLowerCase(); // not really needed since ZIP is just a string of numbers
		final String sql = "SELECT r.*, i.* FROM restaurant r LEFT JOIN inspection i ON i.restaurant_id = r.id WHERE LOWER(r.zip) LIKE '%' || ? || '%'";
		
		List<Restaurant> res = new ArrayList<Restaurant>();
		@SuppressWarnings("deprecation")
		List<Restaurant> r = jdbcTemplate.query(
				sql, 
				new Object[] {zip}, 
				(resultSet, i) -> {
			boolean restaurantAlreadyExists = false;
			for(Restaurant temp : res) {
				String name1 = resultSet.getString("name");
				String zip1 = resultSet.getString("zip");
				if(temp.getName().equals(name1) && temp.getZip().equals(zip1)) {
					Date date = new Date(resultSet.getString("date"));
					Integer score = resultSet.getInt("score");
					String violation = resultSet.getString("violation");
					String risk = resultSet.getString("risk");
					temp.getInspections().add(new Inspection(temp.getId(), date, score, violation, risk));
					restaurantAlreadyExists = true;
				}
			}
			
			if(!restaurantAlreadyExists) {
				String name1 = resultSet.getString("name");
				String zip1 = resultSet.getString("zip");
				String address = resultSet.getString("address");
				String phone = resultSet.getString("phone");
				
				Restaurant temp = new Restaurant(UUID.fromString(resultSet.getString("id")), name1, zip1, address, phone);
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
