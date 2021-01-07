package com.example.SFRestaurantInspections.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;

import com.example.SFRestaurantInspections.model.Date;
import com.example.SFRestaurantInspections.model.Inspection;

public class InspectionDataAccessService implements InspectionDao{

	private final JdbcTemplate jdbcTemplate;
	
	public InspectionDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int insertInspection(Inspection inspection) {
		String sql = "" + 
				"INSERT INTO inspection (" + 
				" restaurant_id," + 
				" date," + 
				" score," + 
				" violation," + 
				"risk) " + 
				"VALUES (?, ?, ?, ?, ?)";
		
		String inspDate = inspection.getDate() == null ? null : inspection.getDate().getDate();
		
		return jdbcTemplate.update(
                sql,
                inspection.getId(),
                inspDate,
                inspection.getScore(),
                inspection.getViolation(),
                inspection.getRisk()
		);
	}

	@Override
	public List<Inspection> selectAllInspections() {
		final String sql = "SELECT id, date, score, violation, risk FROM inspection"; 
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			UUID id = UUID.fromString(resultSet.getString("id"));
			String date = resultSet.getString("date");
			Integer score = Integer.parseInt(resultSet.getString("score"));
			String violation = resultSet.getString("violation");
			String risk = resultSet.getString("risk");
			
			return new Inspection(id, new Date(date), score, violation, risk);
		});
	}

	@Override
	public List<Inspection> selectInspectionsById(UUID id) {
		final String sql = "SELECT id, date, score, violation, risk FROM inspection WHERE id = ?"; 
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			UUID restaurantId = UUID.fromString(resultSet.getString("id"));
			String date = resultSet.getString("date");
			Integer score = Integer.parseInt(resultSet.getString("score"));
			String violation = resultSet.getString("violation");
			String risk = resultSet.getString("risk");
			
			return new Inspection(restaurantId, new Date(date), score, violation, risk);
		});
	}

}
