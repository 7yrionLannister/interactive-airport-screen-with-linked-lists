package model;

public class Date {

	private int day;
	private int month;
	private int year;
	private double hour;

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

	public boolean equals() {
		// TODO - implement Date.equals
		throw new UnsupportedOperationException();
	}

}