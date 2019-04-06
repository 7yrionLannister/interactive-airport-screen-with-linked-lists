package model;

public class Flight {

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
		// TODO - implement Flight.Flight
		throw new UnsupportedOperationException();
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

	/**
	 * 
	 * @param flight
	 */
	public int compareTo(Flight flight) {
		// TODO - implement Flight.compareTo
		throw new UnsupportedOperationException();
	}

}
