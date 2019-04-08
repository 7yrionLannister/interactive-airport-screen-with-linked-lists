package ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Airport;
import model.Flight;

public class AirportController {

	@FXML
	private TableView<Flight> page;

	@FXML
	private TableColumn<Flight, Double> timeTableColumn;

	@FXML
	private TableColumn<Flight, String> dateTableColumn;

	@FXML
	private TableColumn<Flight, Integer> flightTableColumn;

	@FXML
	private TableColumn<Flight, String> airlineTableColumn;

	@FXML
	private TableColumn<Flight, String> destinationTableColumn;

	@FXML
	private TableColumn<Flight, Integer> gatesColumn;

	@FXML
	private TextField numberOfFlightsTextField;

	@FXML
	private RadioButton dateAndHourSort;

	@FXML
	private RadioButton dateSort;

	@FXML
	private RadioButton hourSort;

	@FXML
	private RadioButton numberSort;

	@FXML
	private RadioButton citySort;

	@FXML
	private RadioButton airlineSort;

	@FXML
	private RadioButton gatesSort;

	@FXML
	private ToggleGroup sortCriterionToggleGroup;

	@FXML
	private RadioButton dateSearch;

	@FXML
	private RadioButton hourSearch;

	@FXML
	private RadioButton numberSearch;

	@FXML
	private RadioButton citySearch;

	@FXML
	private RadioButton airlineSearch;

	@FXML
	private RadioButton gatesSearch;

	@FXML
	private ToggleGroup searchCriterionToggleGroup;

	@FXML
	private TextField searchInputTextField;

	private ObservableList<Flight> flights;

	private Airport airport;

	@FXML
	public void initialize() {
		flights = FXCollections.observableArrayList();
		timeTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, Double>("time"));
		dateTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
		flightTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightNumber"));
		airlineTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("airline"));
		destinationTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("destinationCity"));
		gatesColumn.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("boardingGates"));

		dateAndHourSort.setUserData(Airport.ORDERED_BY_DATE_AND_TIME);
		dateSort.setUserData(Airport.ORDERED_BY_DATE);
		hourSort.setUserData(Airport.ORDERED_BY_TIME);
		numberSort.setUserData(Airport.ORDERED_BY_FLIGHT_NUMBER);
		citySort.setUserData(Airport.ORDERED_BY_DESTINATION_CITY);
		airlineSort.setUserData(Airport.ORDERED_BY_AIRLINE);
		gatesSort.setUserData(Airport.ORDERED_BY_BOARDING_GATES);

		dateSearch.setUserData(Airport.ORDERED_BY_DATE);
		hourSearch.setUserData(Airport.ORDERED_BY_TIME);
		numberSearch.setUserData(Airport.ORDERED_BY_FLIGHT_NUMBER);
		citySearch.setUserData(Airport.ORDERED_BY_DESTINATION_CITY);
		airlineSearch.setUserData(Airport.ORDERED_BY_AIRLINE);
		gatesSearch.setUserData(Airport.ORDERED_BY_BOARDING_GATES);

		airport = new Airport(flights);

		page.setItems(flights);

		//TODO Este try catch esta hermoso pero hay que quitarlo, es solo para pruebas
		try {
			airport.generateFlightList(50);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public  void generateFlightList(ActionEvent event) {
		int lenght = Integer.parseInt(numberOfFlightsTextField.getText());
		try {
			airport.generateFlightList(lenght);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void nextPage(ActionEvent event) {

	}

	@FXML
	public void previousPage(ActionEvent event) {

	}

	@FXML
	public void searchFlight(ActionEvent event) {

	}

	@FXML
	public void sortFlights(ActionEvent event) {
		int option = (int) sortCriterionToggleGroup.getSelectedToggle().getUserData();
		switch(option) {
		case Airport.ORDERED_BY_DATE_AND_TIME:
			airport.sortByDateAndTime();
			break;
		case Airport.ORDERED_BY_DATE:
			airport.sortByDate();
			break;
		case Airport.ORDERED_BY_TIME:
			airport.sortByTime();
			break;
		case Airport.ORDERED_BY_FLIGHT_NUMBER:
			airport.sortByFlightNumber();
			break;
		case Airport.ORDERED_BY_DESTINATION_CITY:
			airport.sortByDestinationCity();
			break;
		case Airport.ORDERED_BY_AIRLINE:
			airport.sortByAirline();
			break;
		case Airport.ORDERED_BY_BOARDING_GATES:
			airport.sortByBoardingGates();
			break;
		}
	}

	@FXML
    public void verifyNumberOfFlights(KeyEvent event) {
		char input =event.getCharacter().charAt(0);
		if(!((input >= '0' && input <= '9') || input == '\b')) {
			event.consume();
		}
    }
}

