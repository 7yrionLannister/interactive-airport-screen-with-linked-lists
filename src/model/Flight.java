package model;

public class Flight implements Comparable<Flight> {

	private Date date;
	private String airline;
	private int flightNumber;
	private String destinationCity;
	private int boardingGates;

	/**
	 * 
	 * @param date
	 * @param airline
	 * @param flightNumber
	 * @param destinationCity
	 * @param boardingGates
	 */
	public Flight(Date date, String airline, int flightNumber, String destinationCity, int boardingGates) {
		this.date = date;
		this.airline = airline;
		this.flightNumber = flightNumber;
		this.destinationCity =destinationCity;
		this.boardingGates = boardingGates;
	}

	public Date getDate() {
		return this.date;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String getAirline() {
		return this.airline;
	}

	/**
	 * 
	 * @param airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	public int getFlightNumber() {
		return this.flightNumber;
	}

	/**
	 * 
	 * @param flightNumber
	 */
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getDestinationCity() {
		return this.destinationCity;
	}

	/**
	 * 
	 * @param destinationCity
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public int getBoardingGates() {
		return this.boardingGates;
	}

	/**
	 * 
	 * @param boardingGates
	 */
	public void setBoardingGates(int boardingGates) {
		this.boardingGates = boardingGates;
	}
	
	public String getTime() {
		String time = "";
		double hour = date.getHour();
		int hours = (int) (hour%12);
		if(hours == 0) {
			hours = 12;
		}
		int minutes = (int) ((hour - (int) hour)*60);
		time = hours + " : " + minutes + " ";
		time += hour < 12 ? "A.M." : "P.M.";
		return time;
	}
	/**
	 * 
	 * @param flight
	 */
	public int compareTo(Flight flight) {
		int comparation = 0;
		if(date.compareTo(flight.date) > 0) {
			comparation =1;
		}
		else if(date.compareTo(flight.date) < 0) {
			comparation = -1;
		}
		return comparation;
	}

}
