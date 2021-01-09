package com.example.SFRestaurantInspections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;

import com.example.SFRestaurantInspections.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
public class SfRestaurantInspectionsApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(SfRestaurantInspectionsApplication.class, args);
		
//		long startTime = System.nanoTime();
//		
//		//initializing ArrayLists to store each Object Type
//		RestaurantList resList = new RestaurantList();
//		
//		//read file	
//		Scanner userInput = new Scanner(System.in);
//
//		//print error message for no command line
//		if(args.length == 0) {
//			System.err.println("No command line argument specified");
//			System.exit(5);
//		}
//		File j = new File(args[0]); 
//		
//		//print error message if file doesn't exist
//		try{
//			Scanner in = new Scanner(j);	
//		} catch(FileNotFoundException e) {
//			System.err.println("File provided as the argument does not exist");
//			System.exit(5);
//		}
//		Scanner in = new Scanner(j);
//		in.nextLine();
//		
//		//ArrayList to store ArrayList of Strings from CSV File		
//		ArrayList <String> CSVRow;
//		Restaurant tempRestaurant;
//		
//		//creates ArrayLists from the CSV file
//		while(in.hasNextLine()) {
//			CSVRow = splitCSVLine(in.nextLine());
//
//			//creates Date class
//			//creates Restaurant class
//			int score;
//			String risk = "";
//			boolean duplicate = false;
//		
//			String actualDate = "";
//			String tempDate = CSVRow.get(11);
//			if(!CSVRow.get(12).contentEquals("")) {
//				score = Integer.parseInt(CSVRow.get(12));
//			} else {
//				score = 0;
//			}
//			String violation = CSVRow.get(15);
//			if(CSVRow.size()==16) {
//				risk = "";
//			} else{
//				risk = CSVRow.get(16); 
//			}
//			
//			//cuts String off at space
//			for(int i = 7; i < 13; i++) {
//				if (tempDate.charAt(i) == ' ') {
//					actualDate = tempDate.substring(0, i);
//				}
//			}
//			String name = CSVRow.get(1);
//			String zip = CSVRow.get(5);
//			String address = CSVRow.get(2);
//			String phone = CSVRow.get(9);
//			
//			//creates uniformity for date in MM/DD/YYYY format
//			if(actualDate.charAt(1) == '/') {
//				actualDate = "0" + actualDate;
//			}
//			if(actualDate.charAt(4) == '/') {
//				actualDate = actualDate.substring(0,3) + "0" + actualDate.substring(3);
//			}
//
//			//creates a Date
//			Date date = new Date(actualDate);
//			
//			//creates an Inspection
//			Inspection inspect = new Inspection(UUID.randomUUID(), date, score, violation, risk);
//			
//			//creates Restaurant and adds to RestaurantList
//			try{
//				tempRestaurant = new Restaurant(UUID.randomUUID(), name, zip, address, phone);
//			} catch(IllegalArgumentException e) {
//				//quietly ignore bad ZIP data and skip over restaurant
//				continue;
//			}			
//			Iterator<Restaurant> itr = resList.iterator();
//			while(itr.hasNext()) {
//				Restaurant r = itr.next();
//				if(tempRestaurant.equals(r)){
//					duplicate = true;
//					//adds inspection to existing Restaurant object
//					r.getInspections().add(inspect);
//					break;
//				}
//			}
//			if(!duplicate) { //the current restaurant was not a duplicate
//				
//				//add the restaurant and its inspection to resList
//				tempRestaurant.addInspection(inspect);
//				resList.add(tempRestaurant); 
//			}
//			duplicate = false;
//		}
//
//		//C:\Users\Alan\Downloads\SF_restaurant_scores_full.csv
//		
//		
//		long endTime = System.nanoTime();
//		long remainder = (endTime - startTime)%1000000000;
//		long duration = (endTime - startTime)/1000000000; //convert milliseconds to seconds
//		System.out.println("Duration: " + duration + "." + remainder + " seconds");
//		
//		// time to make POST request
//		System.out.println(resList.size());
		
//		var values = new HashMap<String, String>() {{
//			put("name", "Ippudo Ramen");
//			put("zip", "94103");
//		}};
//		
//		
//		var objectMapper = new ObjectMapper();
//		String requestBody = objectMapper
//				.writeValueAsString(values);
//		
//		HttpClient client = HttpClient.newHttpClient();
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(URI.create("http://localhost:8080/api/v1/restaurant"))
//				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
//				.build();
//		
//		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		
//		System.out.println(response.body());
		
		
		
		
//		boolean x = true;
		//loop that allows user to continue entering search queries
//		while(x) {			
//			System.out.println("enter search query (name or ZIP code) or \"quit\"");
//			String search = userInput.nextLine();
//
//			//exit
//			if(search.equals("quit")) {
//				System.exit(9);
//			}
//			//checks for valid query
//			else if(search.length() < 4) {
//				System.out.println("This is not a valid query. Try again.\n");
//			}
//			else if((!search.substring(0,4).equals("name")) && (!search.substring(0,3).equals("zip"))) {
//				System.out.println("This is not a valid query. Try again.\n");
//			}
//			
//			//searches through keywords instead of integer
//			else if(search.substring(0,4).equals("name")) {
//				//removes "name" from search keyword
//				search = search.substring(5);
//				RestaurantList tempRes = new RestaurantList();
//				if(resList.getMatchingRestaurants(search) == null) {
//					System.out.println("No matches found.\n");
//					continue;
//				}
//				tempRes = resList.getMatchingRestaurants(search);
//				
//				
//				//sort inspections by date
//				for(int sortIns = 0; sortIns < tempRes.size(); sortIns++) {
//					Collections.sort(tempRes.get(sortIns).getInspections(), new InspectSort());
//				}
//				//prints out each element in the ArrayList of restaurants
//				for(Restaurant b : tempRes) {
//					System.out.println(b);
//				}
//			}
//			
//			//if the user wants to search by zip code
//			else if(search.substring(0,3).equals("zip")) {
//				//gets String keyword
//				search = search.substring(4);
//				//create temporary list to store restaurants
//				RestaurantList tempRes = new RestaurantList();
//				
//				if(resList.getMatchingZip(search) == null) {
//					System.out.println("No matches found.\n");
//					continue;
//				}
//				//returns list containing zip code sorted in natural order
//				tempRes = resList.getMatchingZip(search);
//				//sorts list by inspection date to display from highest to lowest score
//				tempRes.sort(new ResScore());
//
//				//sort the inspections by date
//				for(int sortIns = 0; sortIns < tempRes.size(); sortIns++) {
//					Collections.sort(tempRes.get(sortIns).getInspections(), new InspectSort());
//				}
//
//				//remove restaurants that don't have inspections so it won't print
//				boolean print = false;
//				Iterator<Restaurant> itr = tempRes.iterator();
//				while(itr.hasNext()) {
//					System.out.println("didn't throw exception this time");
//					Restaurant b = itr.next();
////				for(Restaurant b: tempRes) {
//					if(b.getInspections().size()==1 && b.getInspections().get(0).getScore() == 0) {
////						tempRes.remove(b);
//						itr.remove(); // safely remove without concurrent modification exception
//						print = true;
//					}
//				}
//				if (print) {
//					System.out.println("No matches found.\n");
//					continue;
//				}
//
//				int printCount = 0;
//				for(Restaurant b : tempRes) {
//
//					if(printCount==3) {
//						break;
//					}
//					System.out.println(b);
//					printCount++;
//					if(printCount >= 3) {
//						break;
//					}
//				}
//			}
//			
//			if(search.equals("quit")) {
//				x = false;
//			}			
//		}	
		
	}
	
	
	/**
	* Splits the given line of a CSV file according to commas and double quotes
	* (double quotes are used to surround multi-word entries so that they may contain commas)
	* @param textLine a line of text to be passed
	* @return an ArrayList object containing all individual entries found on that line
	* @author Joanna Klukowska
	*/
	public static ArrayList<String> splitCSVLine(String textLine){
		if (textLine == null ) return null;
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;
	
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
	
			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					
					insideQuotes = false;
					insideEntry = false;
				}
				else {
						insideQuotes = true;
						insideEntry = true;
				}
			} 
			else if (Character.isWhitespace(nextChar)) {
				
				if ( insideQuotes || insideEntry ) {
					// add it to the current entry
					nextWord.append( nextChar );
				}
				else { // skip all spaces between entries
					continue;
				}
			} 
			else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar);
				} 
				else { // end of entry found
					insideEntry = false;	
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} 
			else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
				}
			}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}
	
		return entries;
	}

}
