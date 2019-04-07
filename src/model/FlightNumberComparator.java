package model;

import java.util.Comparator;

public class FlightNumberComparator implements Comparator<Flight> {

	/**
	 * 
	 * @param f1
	 * @param f2
	 */
	@Override
	public int compare(Flight f1, Flight f2) {
		int comparation = 0;
		int fn1 = f1.getFlightNumber();
		int fn2 = f2.getFlightNumber();
		if(fn1 > fn2) {
			comparation = 1;
		}
		else if(fn1 < fn2) {
			comparation = -1;
		}
		return comparation;
	}

}