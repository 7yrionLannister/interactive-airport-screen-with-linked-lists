package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

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
		try {
			int f = 200;
			airport.generateFlightList(f);
			assertTrue("The airport did not generate the requested number of flights", f == airport.getNumberOfFlights());
		} catch (IOException e) {
			fail("The flights list should have been generated");
		}
	}
	
	/*@Test
	public void sortByDateAndTimeTest() {
		generateFlightListTest();
		airport.sortByDateAndTime();
		Flight current = airport.getFirstFlight().getNext();
		while(current != null) {
			assertTrue("The flights list is not sorted", current.getPrev().compareTo(current) <= 0);
		}
	}*/
	
	/*@Test
	public void sortByAirlineTest() {
		generateFlightListTest();
		AirlineComparator ac = new AirlineComparator();
		airport.sortByAirline();
		Flight current = airport.getFirstFlight().getNext();
		while(current != null) {
			assertTrue("The flights list is not sorted", ac.compare(current.getPrev(), current) <= 0);
		}
	}*/
	
	/*@Test
	public void sortByTimeTest() {
		generateFlightListTest();
		TimeComparator tc = new TimeComparator();
		airport.sortByTime();
		Flight current = airport.getFirstFlight().getNext();
		while(current != null) {
			assertTrue("The flights list is not sorted", tc.compare(current.getPrev(), current) <= 0);
		}
	}*/
	
	/*@Test
	public void sortByDestinationCityTest() {
		generateFlightListTest();
		DestinationCityComparator dcc = new DestinationCityComparator();
		airport.sortByDestinationCity();
		Flight current = airport.getFirstFlight().getNext();
		while(current != null) {
			assertTrue("The flights list is not sorted", dcc.compare(current.getPrev(), current) <= 0);
		}
	}*/
	
	/*@Test
	public void sortByFlightNumberTest() {
		generateFlightListTest();
		FlightNumberComparator fnc= new FlightNumberComparator();
		airport.sortByFlightNumber();
		Flight current = airport.getFirstFlight().getNext();
		while(current != null) {
			assertTrue("The flights list is not sorted", fnc.compare(current.getPrev(), current) <= 0);
		}
	}*/
	
	/*@Test
	public void sortByBoardingGatesTest() {
		generateFlightListTest();
		BoardingGatesComparator bgc = new BoardingGatesComparator();
		airport.sortByBoardingGates();
		Flight current = airport.getFirstFlight().getNext();
		while(current != null) {
			assertTrue("The flights list is not sorted", bgc.compare(current.getPrev(), current) <= 0);
		}
	}*/
	
	/*@Test
	public void sortByDateTest() {
		generateFlightListTest();
		DateComparator dc= new DateComparator();
		airport.sortByDate();
		Flight current = airport.getFirstFlight().getNext();
		while(current != null) {
			assertTrue("The flights list is not sorted", dc.compare(current.getPrev(), current) <= 0);
		}
	}*/
	
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
