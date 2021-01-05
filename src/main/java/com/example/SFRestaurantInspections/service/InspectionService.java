package com.example.SFRestaurantInspections.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.SFRestaurantInspections.dao.InspectionDao;
import com.example.SFRestaurantInspections.model.Inspection;

@Service
public class InspectionService {
	
	private final InspectionDao inspectionDao;
	
	@Autowired
	public InspectionService(@Qualifier("fakedao") InspectionDao inspectionDao) {
		this.inspectionDao = inspectionDao;
	}
	
	public int addInspection(Inspection inspection) {
		return inspectionDao.insertInspection(inspection);
	}
	
	public List<Inspection> getAllInspections() {
		return inspectionDao.selectAllInspections();
	}
}
