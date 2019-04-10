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
		assertTrue("", );
	}
}
