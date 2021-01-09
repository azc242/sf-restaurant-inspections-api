package com.example.SFRestaurantInspections.model;

import java.util.Comparator;

/**	
 * Comparator for sorting inspections by date
 * @author Alan Chen
 */
public class InspectSort implements Comparator<Inspection>{
/**
 * compares inspection objects by their dates
 * @param s1, s2
 * returns day1-day2
 */
	public int compare(Inspection s1, Inspection s2) {
		return s2.getDate().compareTo(s1.getDate());
	}
}