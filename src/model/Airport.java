package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Airport {

	public final static String CITIES_PATH = "resources/cities.txt";
	public final static String AIRLINES_PATH = "resources/airlines.txt";

	private ArrayList<String> cities;
	private ArrayList<String> airlines;

	private Flight firstFlight;
	private SecureRandom sr;

	/**The method allows to create an instance of Airport with the specified list of flights
	 * @param flights The list of flights that the airport will have
	 * */
	public Airport() throws IOException {
		sr = new SecureRandom();


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

	/**The method allows to organize the flights in ascending order according to its date and time. It uses selection sort
	 * */
	public void sortByDateAndTime() {
		addAuxiliaries();
		int size = getNumberOfFlights();
		for(int i = 1; i < size-2; i++) {
			int low = i;
			for(int j = i+1; j < size-1; j++) {
				Flight lowF = getFlight(low);
				Flight jF = getFlight(j);
				if(lowF.compareTo(jF) > 0) {
					low = j;
				}
			}
			Flight inf = getFlight(i);
			Flight sup = getFlight(low);

			Flight nextSup = sup.getNext();
			Flight prevSup = sup.getPrev();
			Flight nextInf = inf.getNext();
			Flight prevInf = inf.getPrev();

			if(inf.getNext() != sup) {
				sup.setNext(nextInf);
				sup.setPrev(prevInf);
				prevInf.setNext(sup);
				nextInf.setPrev(sup);

				prevSup.setNext(inf);
				inf.setPrev(prevSup);
				inf.setNext(nextSup);
				nextSup.setPrev(inf);
			} else {
				nextSup.setPrev(inf);
				inf.setNext(nextSup);
				prevInf.setNext(sup);
				sup.setPrev(prevInf);
				inf.setPrev(sup);
				sup.setNext(inf);
			}
		}
		removeAuxiliaries();
	}

	/**The method allows to organize the flights in ascending order according to its date. It uses bubble sort
	 * */
	public void sortByDate() {
		DateComparator dc = new DateComparator();
		addAuxiliaries();
		int flights = getNumberOfFlights();
		for(int i = 1; i < flights-1; i++) {
			for(int j = 1; j < flights-1-i; j++) {
				Flight inf = getFlight(j);
				Flight sup = inf.getNext();
				if(dc.compare(inf, sup) > 0) {
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
	}

	/**The method allows to organize the flights in ascending order according to its airline. It uses selection sort
	 * */
	public void sortByAirline() {
		AirlineComparator ac = new AirlineComparator();
		addAuxiliaries();
		int size = getNumberOfFlights();
		for(int i = 1; i < size-2; i++) {
			int low = i;
			for(int j = i+1; j < size-1; j++) {
				Flight lowF = getFlight(low);
				Flight jF = getFlight(j);
				if(ac.compare(lowF, jF) > 0) {
					low = j;
				}
			}
			Flight inf = getFlight(i);
			Flight sup = getFlight(low);

			Flight nextSup = sup.getNext();
			Flight prevSup = sup.getPrev();
			Flight nextInf = inf.getNext();
			Flight prevInf = inf.getPrev();

			if(inf.getNext() != sup) {
				sup.setNext(nextInf);
				sup.setPrev(prevInf);
				prevInf.setNext(sup);
				nextInf.setPrev(sup);

				prevSup.setNext(inf);
				inf.setPrev(prevSup);
				inf.setNext(nextSup);
				nextSup.setPrev(inf);
			} else {
				nextSup.setPrev(inf);
				inf.setNext(nextSup);
				prevInf.setNext(sup);
				sup.setPrev(prevInf);
				inf.setPrev(sup);
				sup.setNext(inf);
			}
		}
		removeAuxiliaries();
	}

	/**The method allows to organize the flights in ascending order according to its flight number. It uses insertion sort
	 * */
	public void sortByFlightNumber() {
		FlightNumberComparator fnc = new FlightNumberComparator();
		addAuxiliaries();
		int flights = getNumberOfFlights();
		for(int i = 2; i < flights-1; i++) {
			Flight sup = getFlight(i);
			int j = i-1;
			Flight inf = getFlight(j);
			while(j >= 1 && fnc.compare(sup, inf)  < 0) {
				inf = inf.getPrev();
				j--;
			}
			sup.getPrev().setNext(sup.getNext());
			sup.getNext().setPrev(sup.getPrev());

			inf.getNext().setPrev(sup);
			sup.setNext(inf.getNext());
			inf.setNext(sup);
			sup.setPrev(inf);
		}
		removeAuxiliaries();
	}

	/**The method allows to organize the flights in ascending order according to its destination city
	 * */
	public void sortByDestinationCity() {
		DestinationCityComparator dcc = new DestinationCityComparator();
		addAuxiliaries();
		int flights = getNumberOfFlights();
		for(int i = 2; i < flights-1; i++) {
			Flight sup = getFlight(i);
			int j = i-1;
			Flight inf = getFlight(j);
			while(j >= 1 && dcc.compare(sup, inf)  < 0) {
				inf = inf.getPrev();
				j--;
			}
			sup.getPrev().setNext(sup.getNext());
			sup.getNext().setPrev(sup.getPrev());

			inf.getNext().setPrev(sup);
			sup.setNext(inf.getNext());
			inf.setNext(sup);
			sup.setPrev(inf);
		}
		removeAuxiliaries();
	}

	/**The method allows to organize the flights in ascending order according to its number of boarding gates
	 * */
	public void sortByBoardingGates() {
		BoardingGatesComparator bgc = new BoardingGatesComparator();
		addAuxiliaries();
		int flights = getNumberOfFlights();
		for(int i = 2; i < flights-1; i++) {
			Flight sup = getFlight(i);
			int j = i-1;
			Flight inf = getFlight(j);
			while(j >= 1 && bgc.compare(sup, inf)  < 0) {
				inf = inf.getPrev();
				j--;
			}
			sup.getPrev().setNext(sup.getNext());
			sup.getNext().setPrev(sup.getPrev());

			inf.getNext().setPrev(sup);
			sup.setNext(inf.getNext());
			inf.setNext(sup);
			sup.setPrev(inf);
		}
		removeAuxiliaries();
	}

	/**The method allows to search a flight with the specified date
	 * @param date The departure date criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	public Flight searchByDate(String date) throws IllegalArgumentException {
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
		for(Flight current = firstFlight; current != null && flight == null; current = current.getNext()) {
			if(dc.compare(current,key) == 0) {
				flight = current;
			}
		}
		return flight;
	}

	/**The method allows to search a flight with the specified time
	 * @param hour The time criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	public Flight searchByTime(String hour) {
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
		
		for(Flight current = firstFlight; current != null && flight == null; current = current.getNext()) {
			if(tc.compare(current,key) == 0) {
				flight = current;
			}
		}
		return flight;
	}

	/**The method allows to search a flight with the specified airline
	 * @param airline The airline criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	public Flight searchByAirline(String airline) {
		Flight flight = null;
		Flight key = new Flight(new Date(1,1,1,1), airline, 0, "", 0);
		AirlineComparator ac = new AirlineComparator();
		
		for(Flight current = firstFlight; current != null && flight == null; current = current.getNext()) {
			if(ac.compare(current,key) == 0) {
				flight = current;
			}
		}
		return flight;
	}

	/**The method allows to search a flight with the specified flight number
	 * @param flightNumber The flight number criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	public Flight searchByFlightNumber(int flightNumber) throws IllegalArgumentException {
		if(flightNumber <= 0) {
			throw new IllegalArgumentException();
		}
		Flight key = new Flight(new Date(1, 1, 1, 1), "", flightNumber, "", 0);
		Flight flight = null;
		FlightNumberComparator fnc = new FlightNumberComparator();
		
		for(Flight current = firstFlight; current != null && flight == null; current = current.getNext()) {
			if(fnc.compare(current,key) == 0) {
				flight = current;
			}
		}
		return flight;
	}

	/**The method allows to search a flight with the specified destination city
	 * @param destinationCity The destination city criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	public Flight searchByDestinationCity(String destinationCity) {
		Flight key = new Flight(new Date(1, 1, 1, 1), "", 0, destinationCity, 0);
		Flight flight = null;
		DestinationCityComparator dcc = new DestinationCityComparator();
		
		for(Flight current = firstFlight; current != null && flight == null; current = current.getNext()) {
			if(dcc.compare(current,key) == 0) {
				flight = current;
			}
		}
		return flight;
	}

	/**The method allows to search a flight with the specified boarding gates
	 * @param bg The number of boarding gates criterion
	 * @return The flight that matches the criterion or null if there are not matches
	 * */
	public Flight searchByBoardingGates(int bg) {
		if(bg <= 0) {
			throw new IllegalArgumentException();
		}
		Flight key = new Flight(new Date(1, 1, 1, 1), "", 0, "", bg);
		Flight flight = null;
		BoardingGatesComparator bgc = new BoardingGatesComparator();
		
		for(Flight current = firstFlight; current != null && flight == null; current = current.getNext()) {
			if(bgc.compare(current,key) == 0) {
				flight = current;
			}
		}
		return flight;
	}
	 

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
			ret += "("+current.getFlightNumber()+")";

			current = current.getNext();
			if(current != null) {
				ret += ",";
			}
		}
		ret += "];";

		return ret;
	}
}
