package model;

import java.util.Comparator;

public class DateComparator implements Comparator<Flight> {

	/**
	 * 
	 * @param f1
	 * @param f2
	 */
	@Override
	public int compare(Flight f1, Flight f2) {
		Date d1 = f1.getDate();
		Date d2 = f2.getDate();
		int comparation = 0;
		if(d1.compareTo(d2) > 0) {
			comparation = 1;
		}
		else if(d1.compareTo(d2) < 0) {
			comparation = -1;
		}
		return comparation;
	}

}
