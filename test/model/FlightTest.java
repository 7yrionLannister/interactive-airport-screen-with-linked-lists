package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class FlightTest {

	private Flight flight;
	
	public void setupScenary1() {
		flight = null;
	}
	
	@Test
	public void createFlightTest() {
		setupScenary1();
		Date date = new Date(1, 1, 1, 1);
		String airline = "HappyAir";
		int flightNumber = 124234;
		String destinationCity = "The moon";
		int boardingGates = 10;
		flight = new Flight(date, airline, flightNumber, destinationCity, boardingGates);
		assertTrue("The date was not assigned correctly", date.compareTo(flight.getDate()) == 0);
		assertTrue("The airline was not assigned correctly", airline.equals(flight.getAirline()));
		assertTrue("The flight number was not assigned correctly", flightNumber == flight.getFlightNumber());
		assertTrue("The destination city was not assigned correctly", destinationCity.equals(flight.getDestinationCity()));
		assertTrue("The boarding gates were not assigned correctly", boardingGates == flight.getBoardingGates());
		
		try {
			date = new Date(2,2,2,2);
			airline = "Fly for free!!!";
			flightNumber = -123;
			destinationCity = "Popayan";
			boardingGates = -23112;
			new Flight(date, airline, flightNumber, destinationCity, boardingGates);
			fail("The date should not have been created as the parameters were invalid");
		}
		catch(IllegalArgumentException iae) {
			assertTrue(true);
		}
	}
}
