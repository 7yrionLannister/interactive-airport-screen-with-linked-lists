package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
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
	
	private Flight firstFlight;
	private SecureRandom sr;
	
	/**The method allows to create an instance of Airport with the specified list of flights
	 * @param flights The list of flights that the airport will have
	 * */
	public Airport() throws IOException {
		sr = new SecureRandom();
		orderType = DISORGANIZED;
		
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
	
	/**The method allows to obtain the list of flights
	 * @return The list of flights of this airport
	 * */
	public ArrayList<Flight> getFlights() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		Flight current = firstFlight;
		while(current != null) {
			flights.add(current);
			current = current.getNext();
		}
		return flights;
	}

	/**The method allows to fill the list of flights with "length" randomly generated flights
	 * */
	public void generateFlightList(int lenght) throws IOException {
		orderType = DISORGANIZED;
		firstFlight = null;
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
			
			addFlight(date, airline, flightNumber, destinationCity, boardingGates);
		}
	}

	private void addFlight(Date date, String airline, int flightNumber, String destinationCity, int boardingGates) {
		 Flight addMe = new Flight(date, airline, flightNumber, destinationCity, boardingGates);
		 if(firstFlight == null) {
			 firstFlight = addMe;
		 } else {
			 firstFlight.setPrev(addMe);
			 addMe.setNext(firstFlight);
			 firstFlight = addMe;
		 }
	}

	/**The method allows to organize the flights in ascending order according to its date and time
	 * */
	public void sortByDateAndTime() {
		Collections.sort(getFlights());
		orderType = ORDERED_BY_DATE_AND_TIME;
	}

	/**The method allows to organize the flights in ascending order according to its date
	 * */
	public void sortByDate() {
		DateComparator dc = new DateComparator();
		Collections.sort(getFlights(), dc);
		orderType = ORDERED_BY_DATE;
	}

	/**The method allows to organize the flights in ascending order according to its time. It uses bubble sort
	 * */
	public void sortByTime() {
		TimeComparator tc = new TimeComparator();
		addAuxiliaries();
		int flights = getNumberOfFlights();
		for(int i = 1; i < flights-1; i++) {
			for(int j = 1; j < flights-1-i; j++) {
				Flight inf = getFlight(j);
				Flight sup = inf.getNext();
				if(tc.compare(inf, sup) > 0) {
					sup.getNext().setPrev(inf);
					inf.setNext(sup.getNext());
					inf.getPrev().setNext(sup);
					sup.setPrev(inf.getPrev());
					inf.setPrev(sup);
					sup.setNext(inf);
				}
			}
		}
		removeAuxiliaries();
		orderType = ORDERED_BY_TIME;
	}

	/**The method allows to organize the flights in ascending order according to its airline. It uses selection sort
	 * */
	public void sortByAirline() {
		//FIXME nunca paro, parezco recursivo pero juro que no lo soy
		AirlineComparator ac = new AirlineComparator();
		addAuxiliaries();
		int size = getNumberOfFlights();
		for(int i = 1; i < size-2; i++) {
			System.out.println(this);
			int low = i;
			for(int j = i+1; j < size-1; j++) {
				Flight lowF = getFlight(low);
				Flight jF = getFlight(j);
				if(lowF.getAirline().compareToIgnoreCase(jF.getAirline()) > 0) {
					low = j;
				}
			}
			Flight inf = getFlight(i);
			Flight sup = getFlight(low);
			
			Flight nextSup = sup.getNext();
			Flight prevSup = sup.getPrev();
			
			Flight nextInf = inf.getNext();
			Flight prevInf = inf.getPrev();
			
			sup.setNext(nextInf);
			sup.setPrev(prevInf);
			prevInf.setNext(sup);
			nextInf.setPrev(sup);
			
			prevSup.setNext(inf);
			inf.setPrev(prevSup);
			inf.setNext(nextSup);
			nextSup.setPrev(inf);
		}
		removeAuxiliaries();
		System.out.println("YA SALIIIIIIIII");
		orderType = ORDERED_BY_AIRLINE;
	}

	/**The method allows to organize the flights in ascending order according to its flight number
	 * */
	/*public void sortByFlightNumber() {
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
	}*/

	/**The method allows to organize the flights in ascending order according to its destination city
	 * */
	/*public void sortByDestinationCity() {
		DestinationCityComparator dcc = new DestinationCityComparator();
		Collections.sort(flights, dcc);
		orderType = ORDERED_BY_DESTINATION_CITY;
	}*/

	/**The method allows to organize the flights in ascending order according to its number of boarding gates
	 * */
	/*public void sortByBoardingGates() {
		Collections.sort(flights, new BoardingGatesComparator());
		orderType = ORDERED_BY_BOARDING_GATES;
	}*/
	
	/**The method allows to search a flight with the specified date
	 * @param date The departure date criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	/*public Flight searchByDate(String date) throws IllegalArgumentException {
		Flight flight = null;
		String input[] = date.split("/");
		if(input.length != 3) {
			throw new IllegalArgumentException();
		}
		int day = Integer.parseInt(input[0]);
		int month = Integer.parseInt(input[1]);
		int year = Integer.parseInt(input[2]);
		Date search = new Date(day, month, year, 0);
		Flight key = new Flight(search, "", 0, "", 0);
		DateComparator dc = new DateComparator();
		if( orderType == ORDERED_BY_DATE_AND_TIME) {
			int index = Collections.binarySearch(flights, key, dc);
			if(index >= 0) {
				flight = flights.get(index);
			}
		}
		else if(orderType == ORDERED_BY_DATE) {
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
	}*/

	/**The method allows to search a flight with the specified time
	 * @param hour The time criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	/*public Flight searchByTime(String hour) {
		Flight flight = null;
		TimeComparator tc = new TimeComparator(); 
		
		int h1 = Integer.parseInt(hour.split(":")[0].trim());
		int m1 = Integer.parseInt(hour.split(":")[1].substring(0, 3).trim());
		String ampm1 = hour.toUpperCase().trim().endsWith("A.M.")?"A.M.":"P.M.";
		
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
	}*/

	/**The method allows to search a flight with the specified airline
	 * @param airline The airline criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	/*public Flight searchByAirline(String airline) {
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
	}*/

	/**The method allows to search a flight with the specified flight number
	 * @param flightNumber The flight number criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	/*public Flight searchByFlightNumber(int flightNumber) throws IllegalArgumentException {
		if(flightNumber <= 0) {
			throw new IllegalArgumentException();
		}
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
	}*/

	/**The method allows to search a flight with the specified destination city
	 * @param destinationCity The destination city criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	/*public Flight searchByDestinationCity(String destinationCity) {
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
	}*/
	
	/**The method allows to search a flight with the specified boarding gates
	 * @param bg The number of boarding gates criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	/*public Flight searchByBoardingGates(int bg) {
		if(bg <= 0) {
			throw new IllegalArgumentException();
		}
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
	*/
	
	/**The method allows to obtain a randomly selected city from a text file
	 * @return A randomly selected city
	 * */
	private String getRandomCity() {
		int c = sr.nextInt(cities.size());
		return cities.get(c);
	}
	
	/**The method allows to obtain a randomly selected airline from a text file
	 * @return A randomly selected airline
	 * */
	private String getRandomAirline() {
		int a = sr.nextInt(airlines.size());
		return airlines.get(a);
	}
	
	private void removeAuxiliaries() {
		firstFlight = firstFlight.getNext();
		firstFlight.setPrev(null);
		Flight current = firstFlight;
		while(current.getNext() != null) {
			current = current.getNext();
		}
		current.getPrev().setNext(null);
		current.setPrev(null);
	}

	private void addAuxiliaries() {
		Flight start = new Flight(new Date(1, 1, 1, 1), "Vuelaya", 823417, "abc", 123123);
		Flight end = new Flight(new Date(1, 1, 1, 1), "Vuelahora", 823222, "def", 321123);
		start.setNext(firstFlight);
		firstFlight.setPrev(start);
		firstFlight = start;
		Flight current = firstFlight;
		while(current.getNext() != null) { 
			current = current.getNext();
		}
		current.setNext(end);
		end.setPrev(current);
	}	

	private Flight getFlight(int index) {
		Flight current = firstFlight;
		while(index > 0) {
			current = current.getNext();
			index--;
		}	
		return current;
	}

	public int getNumberOfFlights() {
		Flight current = firstFlight;
		int num = 0;
		while(current != null) {
			num++;
			current = current.getNext();
		}
		return num;
	}

	@Override
	public String toString() {
		String ret = "[";
		Flight current = firstFlight;
		while(current != null) {
			ret += "("+current.getAirline()+")";
			
			current = current.getNext();
			if(current != null) {
				ret += ",";
			}
		}
		ret += "];";
		
		return ret;
	}
}
