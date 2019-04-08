package model;

import java.util.Comparator;

public class AirlineComparator implements Comparator<Flight> {

	/**
	 * 
	 * @param f1
	 * @param f2
	 */
	@Override
	public int compare(Flight f1, Flight f2) {
		int comparation = 0;
		String a1 = f1.getAirline();
		String a2 = f2.getAirline();
		if(a1.compareToIgnoreCase(a2) > 0) {
			comparation = 1;
		}
		else if(a1.compareToIgnoreCase(a2) < 0) {
			comparation = -1;
		}
		return comparation;
	}

}
