package model;

public class Flight implements Comparable<Flight> {

	private Date date;
	private String airline;
	private int flightNumber;
	private String destinationCity;
	private int boardingGates;
	
	private Flight next;
	private Flight prev;

	/**The method allows to create an instance of flight with the corresponding characteristics
	 * @param date The date when the flight departs
	 * @param airline The airline that provides the service
	 * @param flightNumber The unique number identifier for this flight
	 * @param destinationCity The city of arrival
	 * @param boardingGates The total number of boarding gates that this flight has. It must be a positive value
	 */
	public Flight(Date date, String airline, int flightNumber, String destinationCity, int boardingGates) throws IllegalArgumentException {
		setDate(date);
		setAirline(airline);
		setFlightNumber(flightNumber);
		setDestinationCity(destinationCity);
		setBoardingGates(boardingGates);
	}

	/**The method allows to obtain the departure date of the flight
	 * @return The departure date of the flight
	 * */
	public Date getDate() {
		return this.date;
	}

	/**The method allows to change the current departure date of the flight
	 * @param date The new departure date for the flight
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**The method allows to obtain the airline that provides the service
	 * @return The airline that provides the service
	 * */
	public String getAirline() {
		return this.airline;
	}

	/**The method allows to change the current airline
	 * @param airline The new airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**The method allows to obtain the flight number or unique identifier of this flight
	 * @return The unique identifier of this flight
	 * */
	public int getFlightNumber() {
		return this.flightNumber;
	}

	/**The method allows to change the current flight number or unique identifier
	 * @param flightNumber The new identifier for this flight
	 */
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**The method allows to obtain the destination city of this flight
	 * @return The city of arrival
	 * */
	public String getDestinationCity() {
		return this.destinationCity;
	}

	/**The method allows to change the current destination city of this flight
	 * @param destinationCity The new city of arrival
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	/**The method allows to obtain the total number of boarding gates for the flight
	 * @return The number of boarding gates
	 * */
	public int getBoardingGates() {
		return this.boardingGates;
	}

	/**The method allows to change the current number of boarding gates this flight has 
	 * @param boardingGates The total number of boarding gates that this flight has. It must be a positive value
	 */
	public void setBoardingGates(int boardingGates) throws IllegalArgumentException {
		if(boardingGates < 0) {
			throw new IllegalArgumentException();
		}
		this.boardingGates = boardingGates;
	}
	
	/**The method allows to obtain the hour as a String representation in the non military format (AM/PM)
	 * @return A String representing the hour in non military format 
	 * */
	public String getTime() {
		String time = "";
		double hour = date.getHour();
		int hours = (int) (hour%12);
		if(hours == 0) {
			hours = 12;
		}
		int minutes = (int) ((hour - (int) hour)*60.0);
		time = hours + " : " + minutes + " ";
		time += hour < 12 ? "A.M." : "P.M.";
		return time;
	}
	
	/**The method allows to
	 * The method allows knowing when this Flight is equal, greater or less than another Flight<br>
	 * It defines the natural order for Flight
	 * @param flight The flight to compare this flight
	 * @return -1 if this flight is less than the other<br>
	 * 1 if this flight is greater than the other<br>
	 * 0 if the two flights are equal
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

	public Flight getNext() {//System.out.println("getNext");
		return next;
	}

	public void setNext(Flight next) {//System.out.println("setNext");
		this.next = next;
	}

	public Flight getPrev() {//System.out.println("getPrev");
		return prev;
	}

	public void setPrev(Flight prev) {//System.out.println("setPrev");
		this.prev = prev;
	}

}
