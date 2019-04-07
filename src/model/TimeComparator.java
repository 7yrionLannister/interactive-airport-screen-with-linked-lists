package model;

import java.util.Comparator;

public class TimeComparator implements Comparator<Flight> {

	/**
	 * 
	 * @param f1
	 * @param f2
	 */
	@Override
	public int compare(Flight f1, Flight f2) {
		int comparation = 0;
		double h1 = f1.getDate().getHour();
		double h2 = f2.getDate().getHour();
		if(h1 > h2) {
			comparation = 1;
		}
		else if(h1 < h2) {
			comparation = -1;
		}
		return comparation;
	}

}
