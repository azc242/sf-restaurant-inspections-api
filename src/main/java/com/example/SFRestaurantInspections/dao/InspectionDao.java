package com.example.SFRestaurantInspections.dao;

import java.util.List;
import java.util.UUID;

import com.example.SFRestaurantInspections.model.Inspection;

public interface InspectionDao {
	int insertInspection(Inspection inspection);
	
	List<Inspection> selectAllInspections();

}
