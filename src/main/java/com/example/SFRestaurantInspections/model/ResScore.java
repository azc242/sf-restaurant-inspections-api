package com.example.SFRestaurantInspections.model;

import java.util.Comparator;

/**
 * Comparator to sort restaurants by inspection score
 * @author Alan Chen
 *
 */
public class ResScore implements Comparator<Restaurant>{
/**
 * Compares Restaurants by their scores
 * @param Restaurant s1, s2
 */
public int compare(Restaurant s1, Restaurant s2) {
	if(s1.getInspections().size() > 0 && s2.getInspections().size() > 0) {
	   int score1 = s1.getInspections().get(0).getScore();
	   int score2 = s2.getInspections().get(0).getScore();
	   return score2-score1;
	}
	else {
		return 0;
	}
   
  // return score2-score1;
}
}