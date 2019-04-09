package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.ObservableList;

public class Airport {

	public final static String CITIES_PATH = "resources/cities.txt";
	public final static String AIRLINES_PATH = "resources/airlines.txt";
	
	public final static int DISORGANIZED = 1;
	public final static int ORDERED_BY_DATE_AND_TIME = 2;
	public final static int ORDERED_BY_DATE = 3;
	public final static int ORDERED_BY_FLIGHT_NUMBER = 4;
	public final static int ORDERED_BY_TIME = 5;
	public final static int ORDERED_BY_DESTINATION_CITY = 6;
	public final static int ORDERED_BY_AIRLINE = 7;
	public final static int ORDERED_BY_BOARDING_GATES = 8;
	
	private int orderType;
	private ArrayList<String> cities;
	private ArrayList<String> airlines;
	
	private ObservableList<Flight> flights;
	private SecureRandom sr;
	
	public Airport(ObservableList<Flight> flights) throws IOException {
		sr = new SecureRandom();
		orderType = DISORGANIZED;
		this.flights = flights;
		
		cities = new ArrayList<String>();
		airlines = new ArrayList<String>();
		
		File citiesFile = new File(CITIES_PATH);
		FileReader cfr = new FileReader(citiesFile);
		BufferedReader cbr = new BufferedReader(cfr);
		String city = cbr.readLine();
		while(city != null) {
			cities.add(city);
			city = cbr.readLine();
		}
		cfr.close();
		cbr.close();
		
		File airlinesFile = new File(AIRLINES_PATH);
		FileReader afr = new FileReader(airlinesFile);
		BufferedReader abr = new BufferedReader(afr);
		String airline = abr.readLine();
		while(airline != null) {
			airlines.add(airline);
			airline = abr.readLine();
		}
		afr.close();
		abr.close();
	}
	
	public ObservableList<Flight> getFlights() {
		return flights;
	}

	/**
	 * 
	 * @param flights
	 */
	public void setFlights(ObservableList<Flight> flights) {
		this.flights = flights;
	}

	public void generateFlightList(int lenght) throws IOException {
		orderType = DISORGANIZED;
		flights.clear();
		ArrayList<Integer> randomNumbers = new ArrayList<>();
		for(int i = 0; i < lenght; i++) {
			int day = sr.nextInt(31) + 1;
			int month = sr.nextInt(12) +1;
			int year = 2020 + sr.nextInt(10);
			double hour = sr.nextDouble()*24.0;
			Date date = new Date(day, month, year, hour);
			String airline = getRandomAirline();
			int flightNumber = 1 + sr.nextInt(10000000);
			while(randomNumbers.contains(flightNumber)) {
				flightNumber = 1 + sr.nextInt(10000000);
			}
			String destinationCity = getRandomCity();
			int boardingGates = 1 + sr.nextInt(10);
			
			flights.add( new Flight(date, airline, flightNumber, destinationCity, boardingGates));
		}
	}

	public void sortByDateAndTime() {
		Collections.sort(flights);
		orderType = ORDERED_BY_DATE_AND_TIME;
	}

	public void sortByDate() {
		DateComparator dc = new DateComparator();
		Collections.sort(flights, dc);
		orderType = ORDERED_BY_DATE;
	}

	//bubble
	public void sortByTime() {
		TimeComparator tc = new TimeComparator();
		for(int i = 0; i < flights.size(); i++) {
			for(int j = 0; j < flights.size()-1-i; j++) {
				if(tc.compare(flights.get(j), flights.get(j+1)) > 0) {
					Flight temp = flights.get(j);
					flights.set(j, flights.get(j+1));
					flights.set(j+1, temp);
				}
			}
		}
		orderType = ORDERED_BY_TIME;
	}

	//selection
	public void sortByAirline() {
		AirlineComparator ac = new AirlineComparator();
		int size = flights.size();
		for(int i = 0; i < size-1; i++) {
			int low = i;
			for(int j = i+1; j < size; j++) {
				if(ac.compare(flights.get(low), flights.get(j)) > 0) {
					low = j;
				}
			}
			Flight temp = flights.get(low);
			flights.set(low, flights.get(i));
			flights.set(i, temp);
		}
		orderType = ORDERED_BY_AIRLINE;
	}

	//insertion
	public void sortByFlightNumber() {
		FlightNumberComparator fnc = new FlightNumberComparator();
		for(int i = 1; i < flights.size(); i++) {
			Flight current = flights.get(i);
			int j = i-1;
			while(j >= 0 && fnc.compare(flights.get(j), current)  > 0) {
				flights.set(j+1, flights.get(j));
				j--;
			}
			flights.set(j+1, current);
		}
		orderType = ORDERED_BY_FLIGHT_NUMBER;
	}

	public void sortByDestinationCity() {
		DestinationCityComparator dcc = new DestinationCityComparator();
		Collections.sort(flights, dcc);
		orderType = ORDERED_BY_DESTINATION_CITY;
	}

	public void sortByBoardingGates() {
		Collections.sort(flights, new BoardingGatesComparator());
		orderType = ORDERED_BY_BOARDING_GATES;
	}
	
	//Collections.binarySearch
	/**
	 * 
	 * @param date
	 */
	public Flight searchByDate(Date date) {
		Flight flight = null;
		Flight key = new Flight(date, "", 0, "", 0);
		DateComparator dc = new DateComparator();
		if( orderType == ORDERED_BY_DATE_AND_TIME) {
			int index = Collections.binarySearch(flights, key, dc);
			if(index >= 0) {
				flight = flights.get(index);
			}
		}
		else if(orderType == ORDERED_BY_DATE) {System.out.println("estoy aui");
			int index = Collections.binarySearch(flights, key, dc);
			if(index >= 0) {
				flight = flights.get(index);
			}
		}
		else {
			for(int i = 0; i < flights.size() && flight == null; i++) {
				if(dc.compare(flights.get(i),key) == 0) {
					flight = flights.get(i);
				}
			}
		}
		return flight;
	}

	//my binary search
	/**
	 * 
	 * @param hour
	 */
	public Flight searchByTime(String hour) {
		Flight flight = null;
		TimeComparator tc = new TimeComparator(); 
		
		int h1 = Integer.parseInt(hour.split(":")[0].trim());
		int m1 = Integer.parseInt(hour.split(":")[1].substring(0, 3).trim());
		String ampm1 = hour.trim().toUpperCase().endsWith("A.M.")?"A.M.":"P.M.";
		
		if(h1 == 0) {
			h1 = 12;
		}
		
		double time = m1/60.0;
		if(ampm1.equalsIgnoreCase("A.M.")) {
			time += h1==12?0:h1;
		}
		else {
			time += h1==12?12:12+h1;
		}
		
		Flight key = new Flight(new Date(1, 1, 1, time), "", 0, "", 0);
		if(orderType == ORDERED_BY_TIME) {
			int low = 0;
			int high = flights.size()-1;
			while(low <= high && flight == null) {
				int mid = (low+high)/2;
				if(tc.compare(key, flights.get(mid)) < 0) {
					high = mid-1;
				}
				else if(tc.compare(key, flights.get(mid)) > 0) {
					low = mid+1;
				}
				else {
					flight = flights.get(mid);
				}
			}
		}
		else {
			for(int i = 0; i < flights.size() && flight == null; i++) {
				if(tc.compare(key, flights.get(i)) == 0) {
					flight = flights.get(i);
				}
			}
		}
		return flight;
	}

	/**
	 * 
	 * @param airline
	 */
	public Flight searchByAirline(String airline) {
		Flight flight = null;
		Flight key = new Flight(new Date(1,1,1,1), airline, 0, "", 0);
		AirlineComparator ac = new AirlineComparator();
		if( orderType == ORDERED_BY_AIRLINE) {
			int low = 0;
			int high = flights.size()-1;
			while(low <= high && flight == null) {
				int mid = (low+high)/2;
				if(ac.compare(key, flights.get(mid)) > 0) {
					low = mid+1;
				}
				else if(ac.compare(key, flights.get(mid)) < 0) {
					high = mid-1;
				}
				else {
					flight = flights.get(mid);
				}
			}
		}
		else {
			for(int i = 0; i < flights.size() && flight == null; i++) {
				if(ac.compare(key, flights.get(i)) == 0) {
					flight = flights.get(i);
				}
			}
		}
		return flight;
	}

	/**
	 * 
	 * @param flightNumber
	 */
	public Flight searchByFlightNumber(int flightNumber) {
		Flight key = new Flight(new Date(1, 1, 1, 1), "", flightNumber, "", 0);
		Flight flight = null;
		FlightNumberComparator fnc = new FlightNumberComparator();
		if(orderType == ORDERED_BY_FLIGHT_NUMBER) {
			int low = 0;
			int high = flights.size()-1;
			while(low <= high && flight == null) {
				int mid = (low+high)/2;
				if(fnc.compare(flights.get(mid),key) < 0) {
					low = mid+1;
				}
				else if(fnc.compare(flights.get(mid), key) > 0) {
					high = mid-1;
				}
				else {
					flight = flights.get(mid);
				}
			}
		}
		else {
			for(int i = 0; i < flights.size() && flight == null; i++) {
				if(fnc.compare(flights.get(i), key) == 0) {
					flight = flights.get(i);
				}
			}
		}
		return flight;
	}

	/**
	 * 
	 * @param destinationCity
	 */
	public Flight searchByDestinationCity(String destinationCity) {
		Flight key = new Flight(new Date(1, 1, 1, 1), "", 0, destinationCity, 0);
		Flight flight = null;
		DestinationCityComparator dcc = new DestinationCityComparator();
		if(orderType == ORDERED_BY_DESTINATION_CITY) {
			int low = 0;
			int high =flights.size()-1;
			while(low <= high && flight == null) {
				int mid = (low+high)/2;
				if(dcc.compare(flights.get(mid), key) < 0) {
					low = mid+1;
				}
				else if(dcc.compare(flights.get(mid), key) > 0) {
					high = mid-1;
				}
				else {
					flight = flights.get(mid);
				}
			}
		}
		else {
			for(int i = 0; i < flights.size() && flight == null; i++) {
				if(dcc.compare(key, flights.get(i)) == 0) {
					flight = flights.get(i);
				}
			}
		}
		return flight;
	}
	
	public Flight searchByBoardingGates(int bg) {
		Flight key = new Flight(new Date(1, 1, 1, 1), "", 0, "", bg);
		Flight flight = null;
		BoardingGatesComparator bgc = new BoardingGatesComparator();
		if(orderType == ORDERED_BY_BOARDING_GATES) {
			int index = Collections.binarySearch(flights, key, bgc);
			if(index >= 0) {
				flight = flights.get(index);
			}
		}
		else {
			for(int i = 0; i < flights.size() && flight == null; i++) {
				if(bgc.compare(flights.get(i), key) == 0) {
					flight = flights.get(i);
				}
			}
		}
		return flight;
	}
	
	private String getRandomCity() {
		int c = sr.nextInt(cities.size());
		return cities.get(c);
	}
	
	private String getRandomAirline() {
		int a = sr.nextInt(airlines.size());
		return airlines.get(a);
	}

}
