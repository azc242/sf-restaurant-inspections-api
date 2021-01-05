package com.example.SFRestaurantInspections.model;
import java.util.Comparator;

/**
@author Alan Chen
* Stores information about the restaurant inspection
*/

public class Inspection{
	
	private Date date;
	private int score;
	private String violation;
	private String risk;
	
	/**
	 * @throws IllegalArgumentException when date is null or 
	 * when score is not from 0-100 (inclusive)
	 * */
	public Inspection (Date date, int score, String violation, String risk) throws IllegalArgumentException{
		if(date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		if(score < 0 || score > 100) {
			throw new IllegalArgumentException("Invalid score range");
		}
		this.date = date;
		this.score = score;
		this.violation = violation;
		this.risk = risk;
	}
	/**
	 * gets date
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * gets inspection score
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * gets inspection violation
	 * @return violation
	 */
	public String getViolation() {
		return violation;
	}
	/**
	 * gets inspection risk
	 * @return risk
	 */
	public String getRisk() {
		return risk;
	}
}


/**	
 * Comparator for sorting inspections by date
 * @author Alan Chen
 */
class InspectSort implements Comparator<Inspection>{
/**
 * compares inspection objects by their dates
 * @param s1, s2
 * returns day1-day2
 */
	public int compare(Inspection s1, Inspection s2) {

		return s2.getDate().compareTo(s1.getDate());
		}
	}
