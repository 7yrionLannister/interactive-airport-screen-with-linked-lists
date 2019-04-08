package model;

import java.util.Comparator;

public class BoardingGatesComparator implements Comparator<Flight>{

	@Override
	public int compare(Flight f1, Flight f2) {
		int comparation = 0;
		if(f1.getBoardingGates() < f2.getBoardingGates()) {
			comparation = -1;
		}
		else if(f1.getBoardingGates() > f2.getBoardingGates()) {
			comparation = 1;
		}
		return comparation;
	}

}
