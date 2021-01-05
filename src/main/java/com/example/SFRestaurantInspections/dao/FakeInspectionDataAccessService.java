package com.example.SFRestaurantInspections.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.SFRestaurantInspections.model.Inspection;

@Repository("fakedao")
public class FakeInspectionDataAccessService implements InspectionDao{

	private static List<Inspection> DB = new ArrayList<>();

	@Override
	public int insertInspection(Inspection inspection) {
		DB.add(inspection);
//		DB.add(new Inspection(inspection.getDate(), inspection.getScore(), inspection.getViolation(), inspection.getRisk()));
		return 1;
	}

	@Override
	public List<Inspection> selectAllInspections() {
		return DB;
	}

}
