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
		String t1 = f1.getTime();
		String t2 = f2.getTime();
		int h1 = Integer.parseInt(t1.split(":")[0].trim());
		int m1 = Integer.parseInt(t1.split(":")[1].substring(0, 3).trim());
		String ampm1 = t1.trim().endsWith("A.M.")?"A.M.":"P.M.";
		
		if(h1 == 0) {
			h1 = 12;
		}
		
		int h2 = Integer.parseInt(t2.split(":")[0].trim());
		int m2 = Integer.parseInt(t2.split(":")[1].substring(0, 3).trim());
		String ampm2 = t2.trim().endsWith("A.M.")?"A.M.":"P.M.";
		
		if(h2 == 0) {
			h2 = 12;
		}
		
		if(ampm1.compareToIgnoreCase(ampm2) > 0) {
			comparation = 1;
		}
		else if(ampm1.compareToIgnoreCase(ampm2) < 0) {
			comparation = -1;
		}
		else {
			if(h1 > h2) {
				comparation = 1;
			}
			else if(h1 < h2) {
				comparation = -1;
			}
			else {
				if(m1 > m2) {
					comparation = 1;
				}
				else if(m1 < m2) {
					comparation = -1;
				}
			}
		}
		return comparation;
	}

}
