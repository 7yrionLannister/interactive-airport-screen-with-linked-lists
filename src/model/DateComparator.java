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
		
		if(d1.getYear() > d2.getYear()) {
			comparation = 1;
		}
		else if(d1.getYear() < d2.getYear()) {
			comparation = -1;
		}
		else {
			if(d1.getMonth() > d2.getYear()) {
				comparation = 1;
			}
			else if(d1.getMonth() < d2.getMonth()) {
				comparation = -1;
			}
			else {
				if(d1.getDay() > d2.getDay()) {
					comparation = 1;
				}
				else if(d1.getDay() < d2.getDay()) {
					comparation = -1;
				}
			}
		}
		
		return comparation;
	}

}
