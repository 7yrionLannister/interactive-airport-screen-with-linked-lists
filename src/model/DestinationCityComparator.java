package model;

import java.util.Comparator;

public class DestinationCityComparator implements Comparator<Flight>{

	/**
	 * 
	 * @param f1
	 * @param f2
	 */
	@Override
	public int compare(Flight f1, Flight f2) {
		int comparation = 0;
		String dc1 = f1.getDestinationCity();
		String dc2 = f2.getDestinationCity();
		if(dc1.compareTo(dc2) > 0) {
			comparation = 1;
		}
		else if(dc1.compareTo(dc2) < 0) {
			comparation = -1;
		}
		return comparation;
	}

}
