package com.example.SFRestaurantInspections.model;

/**
 * This class represents the date of an inspection (represented by a String)
 * @author Alan Chen
 * 
 */

public class Date implements Comparable<Date>{
	private String Date;
	private int month;
	private int day;
	private int year;
	
	/**
	 * gets string date
	 * @return date
	 */
	public String getDate() {
		return Date;
	}
	/**
	 * gets month
	 * @return int month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * gets day
	 * @return int day
	 */

	public int getDay() {
		return day;
	}

	/**
	 * gets year
	 * @return int year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 
	 * @param date String to be added to Inspection class
	 * @throws IllegalArgumentException if date is null
	 * or incorrect length/format
	 */
	public Date ( String date ) throws IllegalArgumentException{
		if(date == null) throw new IllegalArgumentException("date is null");

		if(date.length() != 10 && date.length() != 8) {
			throw new IllegalArgumentException("invalid date format: incorrect length. Expected length 8 or 10 and got length " + date.length());
		}
		else {
			for(int i = 0; i<date.length(); i++) {
					if(!Character.isDigit(date.charAt(i))) {
						if(i != 2 && i != 5) {
							throw new IllegalArgumentException("invalid date format");
						}
					}
			}
			
		}
		
		 Date = date;
		 //separating the String into month, day and year
		 month = Integer.parseInt(date.substring(0,2));
		 day = Integer.parseInt(date.substring(3,5));
		 if(date.length() == 8) {date = date.substring(0,6) + "20" + date.substring(6);}
		 year = Integer.parseInt(date.substring(6));
		 
		 //checking for valid day, month, and year
		 if(month > 12 || month < 1) {
				throw new IllegalArgumentException("Invalid month");
			}
			if(year < 2000 || year > 2025) {
				throw new IllegalArgumentException("Invalid year");
			}
			if(month == 2) {
				if(day > 29) {
					throw new IllegalArgumentException("Invalid date in February: number larger than 29");
				}
				if(day > 28) {
					for(int y = 2000; y <= 2024 ; y += 4) {
						if(year == y) {
							continue;
						}
					}
					throw new IllegalArgumentException("Invalid date in February");
				}
			}
			if(day == 31 && (month != 1 && month != 3 && month != 5 && month != 7 && month!= 8 &&
					month != 10 && month != 12)) {
				throw new IllegalArgumentException("the month does not have 31 days");
			}
	}
	/**
	 * 
	 * @param month
	 * @param day
	 * @param year
	 * @throws IllegalArgumentException if month isnt within 1-12 (inclusive),
	 * days are not within month's range
	 */
	public Date (int month, int day, int year) throws IllegalArgumentException { 
		//checking for valid arguments for month, day, year
		if(month > 12 || month < 1) {
			throw new IllegalArgumentException("Invalid month");
		}
		if(year < 2000 || year > 2025) {
			throw new IllegalArgumentException("Invalid year");
		}
		//checking for leap year cases and invalid arguments
		if(month == 2) {
			if(day > 29) {
				throw new IllegalArgumentException("Invalid date in February: number larger than 29");
			}
			if(day > 28) {
				boolean isLeap = false;
				for(int y = 2000; y <= 2024 ; y += 4) {
					if(year == y) {
						isLeap = true;
					}
				}
				//throws IllegalArgumentException if day is larger than 28 
				//and the year is not leap
				if(!isLeap) {
					throw new IllegalArgumentException("Invalid date in February: not a leap year");
				}
			}
		}
		if(day == 31 && (month != 1 && month != 3 && month != 5 && month != 7 && month!= 8 &&
				month != 10 && month != 12)) {
			throw new IllegalArgumentException("the month does not have 31 days");
		}
		if(day > 31 || day < 1) {
			throw new IllegalArgumentException("Invalid day parameter: too large or small");
		}
		Date = month+"/"+day+"/"+year;
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	/**
	 * @param x
	 * @return total_days
	 * overrides compareTo method to compare dates, larger date is later
	 */
	@Override
	public int compareTo (Date x) {	
		int total_days = 0;
		int yearDiff = this.year-x.year;
		int numleaps = 0;

		if(this.getYear()<x.getYear()) {
			for(int i = this.getYear(); i < x.getYear(); i++) {
				if (i%4==0) numleaps++;
			}
		}
		else {
			for(int i = x.getYear(); i < this.getYear(); i++) {
				if (i%4==0) numleaps++;
			}
		}
			
		//adding the total days together
		total_days += (yearDiff*365)+numleaps;
		//finding the difference between the number of days in the year that have passed
		total_days += (DaysThisYear(this) - DaysThisYear(x));
	
		return total_days;
	}
	/**
	 * @param x
	 * @return the number of days in the months and days of that year
	 * Uses a for loop to add days to the return total depending on which month it is
	 * loop runs up to the month of the date given, then adds the days
	 */

	/**
	 * finds the number of days in the year so far given
	 * @param x
	 * @return total_days
	 */
	private int DaysThisYear(Date x) {
		int total_days = 0;
		for(int mon = 1; mon < x.getMonth(); mon++) {
			if(mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
				
				total_days += 31;
			}
			else if(mon == 4 || mon == 6 || mon == 9 || mon == 11) {
				total_days +=30;
			}

			else if(mon == 2) {
				//if leap year
				if(x.getYear()%4 == 0) { //changed from 365 to 364
					total_days += 29;
				}
				//if not leap year
				else {
					total_days += 28;
				}
			}
		}
		total_days += x.getDay();
		return total_days;
	}
	/**
	 * overrides toString method
	 * @return Date
	 */
	@Override
	public String toString(){
		return Date;
	}
}