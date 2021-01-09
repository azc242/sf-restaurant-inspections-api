package com.example.SFRestaurantInspections.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Alan Chen
 * Stores restaurants in a LinkedList structure
 */

public class RestaurantList extends ArrayList <Restaurant> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public RestaurantList() {
		// do nothing to create an empty RestaurantList
	}
	
	/**
	 * stores all restaurants containing keyword into RestaurantList
	 * @param keyword
	 * @return RestaurantList
	 */
	
	public RestaurantList getMatchingRestaurants ( String keyword ) {
		
		if(keyword == null) {
			return null;
		}
		//makes searching case insensitive
		keyword = keyword.toLowerCase();
		RestaurantList temp = new RestaurantList();
		int counter = 0;

		for(int i = 0; i < this.size(); i++) {
			
			//makes restaurant name lower case for case insensitivity
			String tempRestauraunt = this.get(i).getName().toLowerCase();

			
			if(tempRestauraunt.contains(keyword.toLowerCase())) {
				//add to list if it contains the keyword
				temp.add(this.get(i));
				counter++;
			}
			
		}
		if(keyword == null || keyword.equals("")) {
			return null;
		}
		//if there were no matches, return null
		if(counter==0) {
			return null;
		}
//		temp.sort();
		Collections.sort(temp);
		return temp;
	}
	/**
	 * stores all restaurants containing keyword zip code into RestaurantList
	 * @param keyword
	 * @return RestaurantList
	 */
	public RestaurantList getMatchingZip ( String keyword ) {
		
		int counter = 0;
		RestaurantList temp = new RestaurantList();
		for(int i = 0; i < this.size(); i++) {

			String tempResName = this.get(i).getZip();

			//add to list if the element contains the keyword
			if(tempResName.equals(keyword)) {
				temp.add(this.get(i));
				counter++;
			}
		}
		
		//if there were no matches return null
		if(counter == 0) {
			return null;
		}
		
		//sort the list by alphabetical order
//		temp.sort();
		Collections.sort(temp);
		return temp;
	}


	/**
	 * returns names of restaurants in RestaurantList separated by semicolons
	 * Overrides AbstractCollection toString method
	 * @return size
	 */
	@Override
	public String toString() {
		int count = 0;
		StringBuilder ret = new StringBuilder();
		for(Restaurant b : this) {
			if(count<this.size()-1) {
				ret.append(b.getName() + "; ");
			}
			else{
				ret.append(b.getName());
			}
			count++;
		}
		return ret.toString();
	}
}
