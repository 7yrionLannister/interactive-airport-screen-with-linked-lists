package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class DateTest {

	private ArrayList<Date> dates;
	
	private SecureRandom sr = new SecureRandom();
	
	public void setupScenary1() {
		dates = new ArrayList<>();
	}
	
	public void setupScenary2() {
		setupScenary1();
		int day, month, year;
		double hour;
		for(int i = 0; i < 7; i++) {
			day = sr.nextInt(31) + 1;
			month = sr.nextInt(12) + 1;
			year = sr.nextInt(10) + 2020;
			hour = sr.nextDouble()*24.0;
			dates.add(new Date(day, month, year, hour));
		}
	}
	
	@Test
	public void createDateTest() {
		setupScenary1();
		int day = sr.nextInt(31) + 1;
		int month = sr.nextInt(12) + 1;
		int year = sr.nextInt(10) + 2020;
		double hour = sr.nextDouble()*24.0;
		dates.add(new Date(day, month, year, hour));
		Date current = dates.get(0);
		assertTrue("The day was not assigned correctly", day == current.getDay());
		assertTrue("The month was not assigned correctly", month == current.getMonth());
		assertTrue("The year was not assigned correctly", year == current.getYear());
		assertTrue("The hour was not assigned correctly", hour == current.getHour());
		
		day = 35;
		month = -4;
		year = 2019;
		hour = 48;
		try {
			new Date(day, month, year, hour);
			fail("The date should not have been created as the parameters were invalid");
		}
		catch(IllegalArgumentException iae) {
			assertTrue(true);
		}
	}

	@Test
	public void compareAndSortDateTest() {
		setupScenary2();
		Collections.sort(dates);
		for(int i = 1; i < dates.size(); i++) {
			assertTrue("The natural order is not defined correctly", dates.get(i).compareTo(dates.get(i-1)) > 0);
		}
	}
}
