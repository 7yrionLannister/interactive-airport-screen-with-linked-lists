package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	private int orderType;
	
	private Flight[] flights;
	private SecureRandom sr;
	
	public Airport() {
		sr = new SecureRandom();
		orderType = DISORGANIZED;
	}
	
	public Flight[] getFlights() {
		return flights;
	}

	/**
	 * 
	 * @param flights
	 */
	public void setFlights(Flight[] flights) {
		this.flights = flights;
	}

	public void generateFlightList(int lenght) throws IOException {
		orderType = DISORGANIZED;
		flights = new Flight[lenght];
		ArrayList<Integer> randomNumbers = new ArrayList<>();
		for(int i = 0; i < lenght; i++) {
			int day = sr.nextInt(31) + 1;
			int month = sr.nextInt(12) +1;
			int year = 2020 + sr.nextInt(10);
			double hour = sr.nextDouble()*24.0;
			Date date = new Date(day, month, year, hour);
			String airline = getRandomAirline();
			int flightNumber = sr.nextInt();
			while(randomNumbers.contains(flightNumber)) {
				flightNumber = sr.nextInt();
			}
			String destinationCity = getRandomCity();
			int boardingGates = sr.nextInt();
			
			flights[i] = new Flight(date, airline, flightNumber, destinationCity, boardingGates);
		}
	}

	public void sortByDateAndTime() {
		Arrays.sort(flights);
		orderType = ORDERED_BY_DATE_AND_TIME;
	}

	public void sortByDate() {
		DateComparator dc = new DateComparator();
		Arrays.sort(flights, dc);
		orderType = ORDERED_BY_DATE;
	}

	//bubble
	public void sortByTime() {
		TimeComparator tc = new TimeComparator();
		for(int i = 0; i < flights.length; i++) {
			for(int j = 0; j < flights.length-1-i; j++) {
				if(tc.compare(flights[j], flights[j+1]) > 0) {
					Flight temp = flights[j];
					flights[j] = flights[j+1];
					flights[j+1] = temp;
				}
			}
		}
		orderType = ORDERED_BY_TIME;
	}

	//selection
	public void sortByAirline() {
		AirlineComparator ac = new AirlineComparator();
		for(int i = 0; i < flights.length; i++) {
			int low = i;
			for(int j = i+1; j < flights.length; j++) {
				if(ac.compare(flights[low], flights[j]) > 0) {
					low = j;
				}
			}
			Flight temp = flights[low];
			flights[low] = flights[i];
			flights[i] = temp;
		}
		orderType = ORDERED_BY_AIRLINE;
	}

	//insertion
	public void sortByFlightNumber() {
		FlightNumberComparator fnc = new FlightNumberComparator();
		for(int i = 1; i < flights.length; i++) {
			Flight current = flights[i];
			int j = i-1;
			while(j >= 0 && fnc.compare(flights[j], current)  > 0) {
				flights[j+1] = flights[j];
				j--;
			}
			flights[j+1] = current;
		}
		orderType = ORDERED_BY_FLIGHT_NUMBER;
	}

	public void sortByDestinationCity() {
		DestinationCityComparator dcc = new DestinationCityComparator();
		Arrays.sort(flights, dcc);
		orderType = ORDERED_BY_DESTINATION_CITY;
	}

	/**
	 * 
	 * @param date
	 */
	public Flight searchByDate(Date date) {
		Flight flight = null;
		Flight key = new Flight(date, "", 0, "", 0);
		if( orderType == ORDERED_BY_DATE_AND_TIME) {
			int index = Arrays.binarySearch(flights, key);
			if(index >= 0) {
				flight = flights[index];
			}
		}
		else if(orderType == ORDERED_BY_DATE) {
			int index = Arrays.binarySearch(flights, key, new DateComparator());
			if(index >= 0) {
				flight = flights[index];
			}
		}
		else {
			for(int i = 0; i < flights.length && flight == null; i++) {
				if(flights[i].compareTo(key) == 0) {
					flight = flights[i];
				}
			}
		}
		return flight;
	}

	/**
	 * 
	 * @param hour
	 */
	public Flight searchByTime(double hour) {
		Flight flight = null;
		TimeComparator tc = new TimeComparator(); 
		if(orderType == ORDERED_BY_TIME) {
			int low = 0;
			int hight = flights.length-1;
			int mid = (low+hight)/2;
			//while() {
				//TODO implementar todo
			//}
		}
		else {
			for(int i = 0; i < flights.length; i++) {
				
			}
		}
	}

	/**
	 * 
	 * @param airline
	 */
	public Flight searchByAirline(String airline) {
		Flight flight = null;
		Flight key = new Flight(new Date(1,1,1,1), airline, 0, "", 0);
		if( orderType == ORDERED_BY_AIRLINE) {
			
		}
		else {
			for(int i = 0; i < flights.length && flight == null; i++) {
				if(flights[i].compareTo(key) == 0) {
					flight = flights[i];
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
		// TODO - implement Airport.searchByFlightNumber
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param destinationCity
	 */
	public Flight searchByDestinationCity(String destinationCity) {
		// TODO - implement Airport.searchByDestinationCity
		throw new UnsupportedOperationException();
	}
	
	public String getRandomCity() throws IOException {
		int c = sr.nextInt(98);
		File file = new File(CITIES_PATH);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String city = br.readLine();
		int i = 0;
		while(i <= c) {
			city = br.readLine();
		}
		fr.close();
		br.close();
		return city;
	}
	
	public String getRandomAirline() throws IOException {
		int a = sr.nextInt(14);
		File file = new File(AIRLINES_PATH);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String airline = br.readLine();
		int i = 0;
		while(i <= a) {
			airline = br.readLine();
		}
		fr.close();
		br.close();
		return airline;
	}

}
