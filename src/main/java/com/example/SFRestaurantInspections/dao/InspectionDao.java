package com.example.SFRestaurantInspections.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.SFRestaurantInspections.model.Inspection;
import com.example.SFRestaurantInspections.model.Restaurant;

public interface InspectionDao {
	int insertInspection(Inspection inspection);
	
	List<Inspection> selectAllInspections();
	
	List<Inspection> selectInspectionsById(UUID id);

}
