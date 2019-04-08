package ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Airport;
import model.Date;
import model.Flight;

public class AirportController {

	@FXML
	private TableView<Flight> page;

	@FXML
	private TableColumn<Flight, String> timeTableColumn;

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
		timeTableColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("time"));
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
		int option = (int) searchCriterionToggleGroup.getSelectedToggle().getUserData();
		switch(option) {
		case Airport.ORDERED_BY_DATE:
			String[] input = searchInputTextField.getText().split("/");
			if(input.length == 3) {
				try {
					int day = Integer.parseInt(input[0]);
					int month = Integer.parseInt(input[1]);
					int year = Integer.parseInt(input[2]);
					Date search = new Date(day, month, year, 0);
					Flight match = airport.searchByDate(search);
					showResult(match);
				}
				catch(NumberFormatException nfe) {
					//TODO ventana emergente diciendo que madure
				}
			}
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
	
	public void showResult(Flight f) {
		if(f != null) {
			ObservableList<Flight> flightResults = FXCollections.observableArrayList();
			flightResults.add(f);
			
			TableView<Flight> result = new TableView<Flight>();
			TableColumn<Flight, String> timeC = new TableColumn<Flight, String>("Time");
			TableColumn<Flight, String> dateC = new TableColumn<Flight, String>("Date");
			TableColumn<Flight, Integer> flightC = new TableColumn<Flight, Integer>("Flight");
			TableColumn<Flight, String> airlineC = new TableColumn<Flight, String>("Airline");
			TableColumn<Flight, String> destinationC = new TableColumn<Flight, String>("To");
			TableColumn<Flight, Integer> gatesC = new TableColumn<Flight, Integer>("Boarding gates");
			
			timeC.setCellValueFactory(new PropertyValueFactory<Flight, String>("time"));
			dateC.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
			flightC.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightNumber"));
			airlineC.setCellValueFactory(new PropertyValueFactory<Flight, String>("airline"));
			destinationC.setCellValueFactory(new PropertyValueFactory<Flight, String>("destinationCity"));
			gatesC.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("boardingGates"));
			
			result.getColumns().addAll(timeC, dateC, flightC, airlineC, destinationC, gatesC);
			result.setItems(flightResults);
			
			Stage popUp = new Stage();
			Scene scene = new Scene(result);
			popUp.setWidth(600);
			popUp.setHeight(90);
			popUp.setScene(scene);
			popUp.setResizable(false);
			popUp.show();
		}
		else {
			Dialog dialog = new Dialog();
			dialog.setContentText("Your flight has not been found");
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> window.hide());
			dialog.show();
		}
	}
}

