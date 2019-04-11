package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class AirportTest {

	private Airport airport;
	private ObservableList<Flight> flights;
	
	public void setupScenary1() {
		airport = null;
		flights = FXCollections.observableArrayList();
	}
	
	@Test
	public void createAirportTest() {
		setupScenary1();
		
		try {
			airport = new Airport(flights);
		} catch (IOException e) {
			fail("The airport should have been created");
		}
	}

	@Test
	public void generateFlightListTest() {
		createAirportTest();
		try {
			airport.generateFlightList(100);
		} catch (IOException e) {
			fail("The flights list should have been generated");
		}
	}
	
	@Test
	public void sortTest() {
		generateFlightListTest();
		
		DateComparator dc= new DateComparator();
		TimeComparator tc = new TimeComparator();
		AirlineComparator ac = new AirlineComparator();
		FlightNumberComparator fnc= new FlightNumberComparator();
		BoardingGatesComparator bgc = new BoardingGatesComparator();
		DestinationCityComparator dcc = new DestinationCityComparator();
		
		airport.sortByDateAndTime();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", airport.getFlights().get(i-1).compareTo(airport.getFlights().get(i)) <= 0);
		}
		
		airport.sortByDate();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", dc.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}
		
		airport.sortByTime();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", tc.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}
		
		airport.sortByAirline();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", ac.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}
		
		airport.sortByFlightNumber();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", fnc.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}
		
		airport.sortByBoardingGates();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", bgc.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}
		
		airport.sortByDestinationCity();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", dcc.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}
	}
	
	@Test
	public void searchTest() {
		createAirportTest();
		try {
			airport.generateFlightList(2000);
		} catch (IOException e) {
			fail("The flights list should have been generated");
		}
		Flight flightToSearch = airport.getFlights().get(222);
		String date = flightToSearch.getDate().toString();
		String time = flightToSearch.getTime();
		String airline = flightToSearch.getAirline();System.out.println(airline);
		int flightNumber = flightToSearch.getFlightNumber();
		String city = flightToSearch.getDestinationCity();
		int gates = flightToSearch.getBoardingGates();
		
		Flight found = airport.searchByDate(date);System.out.println(found);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the date searched", date.equals(found.getDate().toString()));
		
		found = airport.searchByTime(time);
		assertNotNull("The algorithm should have found a flight", found);
		double timeSearched = flightToSearch.getDate().getHour();
		double timeFound = found.getDate().getHour();
		double diff = Math.floor(Math.abs(timeFound-timeSearched));
		assertTrue("The flight found does not have the time searched", diff == 0);
		
		found = airport.searchByAirline(airline);System.out.println(found);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the airline searched", airline.equals(found.getAirline()));
		
		found = airport.searchByFlightNumber(flightNumber);System.out.println(found);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the number searched", flightNumber == found.getFlightNumber());
		
		found = airport.searchByDestinationCity(city);System.out.println(found);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the city searched", city.equals(found.getDestinationCity()));
		
		found = airport.searchByBoardingGates(gates);System.out.println(found);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the number of boarding gates searched", gates == found.getBoardingGates());
	}
}
