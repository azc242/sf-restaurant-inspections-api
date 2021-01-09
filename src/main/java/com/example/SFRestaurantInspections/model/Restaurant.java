package com.example.SFRestaurantInspections.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *@author Alan Chen
 *@throws IllegalArgumentException if zip code is not 5 digits
 * or if restaurant name is an empty string or null
 */

public class Restaurant implements Comparable<Restaurant> {
	private UUID id;
	@NotBlank
	private String name;
	private String zip;
	private String address;
	private String phone;
	private List <Inspection> inspections = new ArrayList<Inspection>();

	@Autowired(required=true)
	public Restaurant(@JsonProperty("id") UUID id, 
			@JsonProperty("name") String name, 
			@JsonProperty("zip") String zip, 
			@JsonProperty("address") String address, 
			@JsonProperty("phone") String phone) throws IllegalArgumentException {
		checkExceptions(name, zip);
		this.id = id;
		this.name = name;
		this.zip = zip;
		this.address = address;
		this.phone = phone;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setInspections(List<Inspection> inspections) {
		this.inspections = inspections;
	}

	/**
	 * Method that checks whether the name and ZIP code parameters are valid,
	 * throws exception if not
	 * @throws IllegalArgumentException 
	 * if name is a non empty string and if the zip code does not contain exactly 5 digits
	 * @param name of the restaurant
	 * @param zip code of the restaurant
	 * @param address of restaurant
	 * @param phone number of restaurant
	 */
	private void checkExceptions(String name, String zip) throws IllegalArgumentException {
		if(name == null) {
			throw new IllegalArgumentException("String name cannot be null");
		}
		if(name.equals("")) {
			throw new IllegalArgumentException("String name cannot be empty");
		}
		if(zip.length() > 5 && zip.charAt(5) == '-') {
			zip = zip.substring(0, 5);
		}
		if(zip == null) {
			// do nothing
		}
		else if(zip.length() !=5) {
			if(zip.equals("")) {
				//quietly ignore lack of zip code
			}
			else {
				throw new IllegalArgumentException("ZIP code must be exactly 5 digits long");
			}
		}
		else {
			for(int i = 0; i < 5; i++) {
				if(!Character.isDigit(zip.charAt(i))) {
					throw new IllegalArgumentException("ZIP code must contain only digits");
					}
				}
			}
		}
	
	 /**
	  * throws exception if there are attempts at adding a null Inspector object
	  * @param inspect
	  * @throws IllegalArgumentException if there are attempts at adding a null Inspector object
	  */
	public void addInspection(Inspection inspect) throws IllegalArgumentException {
		if(inspect == null) {
			throw new IllegalArgumentException("Inspection parameter cannot be null");
		}
		inspections.add(inspect);
	}
	/**
	 * overrides compareTo method to compare Restaurants by name
	 * if names are the same, compares by zip code
	 * @param o
	 * @return a
	 */
	@Override
	public int compareTo(Restaurant o) throws IllegalArgumentException {
	
		int a = this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
		if(a == 0){	
			a = this.getZip().compareTo(o.getZip());
		}
		return a;
	}

	public int compare(Restaurant s1, Restaurant s2) {

		   int score1 = s1.getInspections().get(0).getScore();
		   int score2 = s2.getInspections().get(0).getScore();
		   
		   return score1-score2;
	   }
	
	/**
	 * toString prints out data about restaurants if prompted
	 * @return formatted String f
	 */
	@Override
	public String toString() {
		
		if(inspections != null) {
			//sorts inspections
			Collections.sort(inspections, new InspectSort());
			//removes inspection if there is no score
		}
		
		
		String addy = this.getAddress();
		if(addy == null) addy = "";
		
		String zip = this.getZip();
		if(zip == null) zip = "";
		
		String phon = this.getPhone();
		if(phon == null) phon = "";
		
		String f = String.format("\n" + this.getName() +  "\n------------------------------------\n" + 
		"address\t\t: %1s\nzip\t\t: %2s\nphone\t\t: %3s\nrecent inspection results" + 
				":"  , addy, zip, phon);

		//initializing score, date, risk, violation for inspection 1
		int score = 0;
		String date = "";
		String risk = "";
		String violation = "";
		//initializing score, date, risk, violation for inspection 2
		int score2 = 0;
		String date2 = "";
		String risk2 = "";
		String violation2 = "";
		
		//retrieves variables for string.format
		if(inspections != null && inspections.size() > 0) {
			score = inspections.get(0).getScore();
			date =  inspections.get(0).getDate().getDate();
			risk = ", " + inspections.get(0).getRisk();
			violation = ", " + inspections.get(0).getViolation();
			
			if(violation.equals(", ")) {
				violation = "";
					}
			if(risk.equals(", ")) {
				risk = "";
				}
			if(date.equals(", ")) {
				date = "";
				}
			//there is more than 1 inspection in the restaurant
			if(inspections.size()!=1) {
				score2 = inspections.get(1).getScore();
				date2 = inspections.get(1).getDate().getDate();
				risk2 = ", " + inspections.get(1).getRisk();
				violation2 = ", " + inspections.get(1).getViolation();
				if(violation2.equals(", ")) {
					violation2 = "";
					}
				if(risk2.equals(", ")) {
					risk2 = "";
					}
				if(date2.equals(", ")) {
					date2 = "";
					}
			}
		}
		
		
		//if score exists, add the line to String f for return otherwise ignore the inspection
		if(score != 0) {
			f+= String.format("\n%1s, %2s %3s %4s", score , date , risk, violation + "\n");
		}
		if(score2 != 0) {
			f+= String.format("%1s, %2s %3s %4s", score2 , date2 , risk2, violation2 + "\n");
		}
		//initializing score, date, risk, violation for inspection in case inspection1/2 have same dates
		int tscore = 0;
		String tdate = "";
		String trisk = "";
		String tviolation = "";
		//checks if date1 == date 2
		if(date.equals(date2)) {
			//checks if there are more inspections to print
			if(inspections != null && inspections.size() > 1) {
				tscore = inspections.get(2).getScore();
				tdate =  inspections.get(2).getDate().getDate();
				trisk = ", " + inspections.get(2).getRisk();
				tviolation = ", " + inspections.get(2).getViolation();
				
				if(tviolation.equals(", ")) {
					tviolation = "";
						}
				if(trisk.equals(", ")) {
					trisk = "";
					}
				if(tdate.equals(", ")) {
					tdate = "";
					}
			}
				
		}
		if(tscore != 0) {
			f+= String.format("%1s, %2s %3s %4s", tscore , tdate , trisk, tviolation + "\n");
		}
		return f;
	}

		
	/**
	 * returns whether or not 2 restaurants are equal by name and zip
	 * @param z
	 */
		
	public boolean equals(Restaurant z) {

		if(z == null) {
			return false;
		}
		return (this.compareTo(z) == 0);
	}
	
	/**
	 * gets name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets zip
	 * @return zip
	 */
	
	public String getZip() {
		return zip;
	}
	
	/**
	 * gets address
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * gets phone
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * gets ArrayList of inspections
	 * @return inspections
	 */
	public List<Inspection> getInspections() {
		return inspections;
	}

}

//	/**
//	 * Comparator to sort restaurants by inspection score
//	 * @author Alan Chen
//	 *
//	 */
//	public class ResScore implements Comparator<Restaurant>{
//	/**
//	 * Compares Restaurants by their scores
//	 * @param Restaurant s1, s2
//	 */
//	public int compare(Restaurant s1, Restaurant s2) {
//		if(s1.getInspections().size() > 0 && s2.getInspections().size() > 0) {
//		   int score1 = s1.getInspections().get(0).getScore();
//		   int score2 = s2.getInspections().get(0).getScore();
//		   return score2-score1;
//		}
//		else {
//			return 0;
//		}
//	   
//	  // return score2-score1;
//   }
//}