package model;

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
}
