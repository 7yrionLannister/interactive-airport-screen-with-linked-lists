package model;

public class Date implements Comparable<Date> {

	private int day;
	private int month;
	private int year;
	private double hour;

	public Date(int day, int month, int year, double hour) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
	}

	public int getDay() {
		return this.day;
	}

	/**
	 * 
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return this.month;
	}

	/**
	 * 
	 * @param month
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return this.year;
	}

	/**
	 * 
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	public double getHour() {
		return this.hour;
	}

	/**
	 * 
	 * @param hour
	 */
	public void setHour(double hour) {
		this.hour = hour;
	}

	@Override
	public int compareTo(Date date) {
		int comparation = 0;
		if(year > date.year) {
			comparation = 1;
		}
		else if(year < date.year) {
			comparation = -1;
		}
		else {
			if(month > date.month) {
				comparation = 1;
			}
			else if(month < date.month) {
				comparation = -1;
			}
			else {
				if(day > date.day) {
					comparation = 1;
				}
				else if(day < date.day ) {
					comparation = -1;
				}
			}
		}
		return comparation;
	}

}