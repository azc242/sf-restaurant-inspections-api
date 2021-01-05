package com.example.SFRestaurantInspections.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SFRestaurantInspections.model.Inspection;
import com.example.SFRestaurantInspections.service.InspectionService;

@RequestMapping("api/v1/inspection")
@RestController
public class InspectionController {

	private final InspectionService inspectionService;
	
	@Autowired
	public InspectionController(@Qualifier("inspectionService") InspectionService inspectionService) {
		this.inspectionService = inspectionService;
	}
	
	@PostMapping
	public void addInspection(@Valid @NonNull @RequestBody Inspection inspection) {
		inspectionService.addInspection(inspection);
	}
	
	@GetMapping
	public List<Inspection> getAllInspections() {
		return inspectionService.getAllInspections();
	}
}
