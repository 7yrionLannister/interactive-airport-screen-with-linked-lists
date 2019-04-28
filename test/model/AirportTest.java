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
	
	public void setupScenary1() {
		airport = null;
	}
	
	@Test
	public void createAirportTest() {
		setupScenary1();
		
		try {
			airport = new Airport();
		} catch (IOException e) {
			fail("The airport should have been created");
		}
	}

	@Test
	public void generateFlightListTest() {
		createAirportTest();
		try {System.out.println("hola mundo");
			airport.generateFlightList(5);System.out.println("ya genere prro");
		} catch (IOException e) {
			fail("The flights list should have been generated");
		}
	}
	
	@Test
	public void sortTest() {
		generateFlightListTest();
		ObservableList<Flight> flights;
		DateComparator dc= new DateComparator();
		TimeComparator tc = new TimeComparator();
		AirlineComparator ac = new AirlineComparator();
		FlightNumberComparator fnc= new FlightNumberComparator();
		BoardingGatesComparator bgc = new BoardingGatesComparator();
		DestinationCityComparator dcc = new DestinationCityComparator();
		
		/*
		airport.sortByDateAndTime();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", airport.getFlights().get(i-1).compareTo(airport.getFlights().get(i)) <= 0);
		}
		
		airport.sortByDate();
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", dc.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}
		*/
		System.out.println("before sort");
		airport.sortByTime();
		System.out.println("after sort");
		System.out.println(airport);
		/*System.out.println("before getflights");
		 flights = airport.getFlights();
		 System.out.println("after getflights");
		for(int i = 1; i < flights.size(); i++) {
			assertTrue("The flights list is not sorted", tc.compare(airport.getFlights().get(i-1), airport.getFlights().get(i)) <= 0);
		}*/
		/*
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
		*/
	}
	
	/*@Test
	public void searchTest() {
		generateFlightListTest();
		Flight flightToSearch = airport.getFlights().get(222);
		String date = flightToSearch.getDate().toString();
		String time = flightToSearch.getTime();
		String airline = flightToSearch.getAirline();
		int flightNumber = flightToSearch.getFlightNumber();
		String city = flightToSearch.getDestinationCity();
		int gates = flightToSearch.getBoardingGates();
		
		Flight found = airport.searchByDate(date);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the date searched", date.equals(found.getDate().toString()));
		
		found = airport.searchByTime(time);
		assertNotNull("The algorithm should have found a flight", found);
		double timeSearched = flightToSearch.getDate().getHour();
		double timeFound = found.getDate().getHour();
		double diff = Math.floor(Math.abs(timeFound-timeSearched));
		assertTrue("The flight found does not have the time searched", diff == 0);
		
		found = airport.searchByAirline(airline);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the airline searched", airline.equals(found.getAirline()));
		
		found = airport.searchByFlightNumber(flightNumber);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the number searched", flightNumber == found.getFlightNumber());
		
		found = airport.searchByDestinationCity(city);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the city searched", city.equals(found.getDestinationCity()));
		
		found = airport.searchByBoardingGates(gates);
		assertNotNull("The algorithm should have found a flight", found);
		assertTrue("The flight found does not have the number of boarding gates searched", gates == found.getBoardingGates());
	}*/
}
