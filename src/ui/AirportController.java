package ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.Flight;

public class AirportController {

	public final static int ITEMS_PER_PAGE = 18;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Button previousButton;
	
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
	
	private int currentPage;

	@FXML
	public void initialize() {
		currentPage = 0;
		
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

		try {
			airport = new Airport();
		} catch (IOException e) {
			e.printStackTrace();
		}
		flights = FXCollections.observableArrayList(airport.getFlights());
	}

	@FXML
	public  void generateFlightList(ActionEvent event) {
		try {
			int lenght = Integer.parseInt(numberOfFlightsTextField.getText());
			airport.generateFlightList(lenght);
			flights = FXCollections.observableArrayList(airport.getFlights());
		} catch (IOException|NumberFormatException e) {

		}
		setupPage();
	}

	@FXML
	public void nextPage(ActionEvent event) {
		currentPage += 1;
		setupPage();
	}

	@FXML
	public void previousPage(ActionEvent event) {
		currentPage -= 1;
		setupPage();
	}

	@FXML
	public void searchFlight(ActionEvent event) {
		int option = (int) searchCriterionToggleGroup.getSelectedToggle().getUserData();
		Flight match;
		long timeBeforeSearch = System.currentTimeMillis();
		long timeAfterSearch;
		long timeSearching;
		switch(option) {
		case Airport.ORDERED_BY_DATE:
			try {
				match = airport.searchByDate(searchInputTextField.getText());
				timeAfterSearch = System.currentTimeMillis();
				timeSearching = timeAfterSearch - timeBeforeSearch;
				showResult(match, timeSearching);
			}
			catch(IllegalArgumentException e) {
				showDialog("Invalid input: day/month/year");
			}
			break;
		case Airport.ORDERED_BY_TIME:
			try {
				int h = Integer.parseInt(searchInputTextField.getText().split(":")[0].trim());
				int m = Integer.parseInt(searchInputTextField.getText().split(":")[1].substring(0, 3).trim());
				String ampm = searchInputTextField.getText().toUpperCase().trim().endsWith("A.M.")?"A.M.":"P.M.";

				if(h == 0) {
					h = 12;
				}

				String key = h + " : " + m + " " +ampm;
				match = airport.searchByTime(key);
				timeAfterSearch = System.currentTimeMillis();
				timeSearching = timeAfterSearch - timeBeforeSearch;
				showResult(match, timeSearching);
			}
			catch(NullPointerException|ArrayIndexOutOfBoundsException|IllegalArgumentException e) {
				showDialog("Invalid input: hours : minutes (either A.M. or P.M.)");
			}
			break;
		case Airport.ORDERED_BY_FLIGHT_NUMBER:
			try {
				int flight = Integer.parseInt(searchInputTextField.getText());
				match = airport.searchByFlightNumber(flight);
				timeAfterSearch = System.currentTimeMillis();
				timeSearching = timeAfterSearch - timeBeforeSearch;
				showResult(match, timeSearching);
			}
			catch(IllegalArgumentException iae) {
				showDialog("Invalid input: Positive integer");
			}
			break;
		case Airport.ORDERED_BY_DESTINATION_CITY:
			match = airport.searchByDestinationCity(searchInputTextField.getText());
			timeAfterSearch = System.currentTimeMillis();
			timeSearching = timeAfterSearch - timeBeforeSearch;
			showResult(match, timeSearching);
			break;
		case Airport.ORDERED_BY_AIRLINE:
			match = airport.searchByAirline(searchInputTextField.getText());
			timeAfterSearch = System.currentTimeMillis();
			timeSearching = timeAfterSearch - timeBeforeSearch;
			showResult(match, timeSearching);
			break;
		case Airport.ORDERED_BY_BOARDING_GATES:
			try {
				int gates = Integer.parseInt(searchInputTextField.getText());
				match = airport.searchByBoardingGates(gates);
				timeAfterSearch = System.currentTimeMillis();
				timeSearching = timeAfterSearch - timeBeforeSearch;
				showResult(match, timeSearching);
			}
			catch(IllegalArgumentException iae) {
				showDialog("Invalid input: Positive integer");
			}
			break;
		}
	}

	@FXML
	public void sortFlights(ActionEvent event) {
		int option = (int) sortCriterionToggleGroup.getSelectedToggle().getUserData();
		long timeBeforeSorting = System.currentTimeMillis();
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
		long timeAfterSorting = System.currentTimeMillis();
		flights = FXCollections.observableArrayList(airport.getFlights());
		setupPage();
		showDialog("Time sorting: " + (timeAfterSorting-timeBeforeSorting) + " miliseconds");
	}

	@FXML
	public void verifyNumberOfFlights(KeyEvent event) {
		char input =event.getCharacter().charAt(0);
		if(!((input >= '0' && input <= '9') || input == '\b')) {
			event.consume();
		}
	}

	public void showResult(Flight f, long timeSearching) {
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
			popUp.setTitle("Result obtained in " + timeSearching + " miliseconds");
			popUp.setResizable(false);
			popUp.show();
		}
		else {
			showDialog("Your flight has not been found");
		}
	}

	public void showDialog(String message) {
		Dialog dialog = new Dialog();
		dialog.setContentText(message);
		if(message.substring(0, 4).equalsIgnoreCase("time")) {
			dialog.setTitle("Time sorting");
		}
		else if(message.substring(0, 4).equalsIgnoreCase("your")) {
			dialog.setTitle("Unsuccessful search");
		}
		else {
			dialog.setTitle("Invalid input");
		}
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> window.hide());
		dialog.showAndWait();
	}

	public void setupPage() {
		int fromIndex = currentPage * ITEMS_PER_PAGE;
		int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, flights.size());
		ObservableList<Flight> pageItems = FXCollections.observableArrayList(flights.subList(fromIndex, toIndex));
		page.setItems(pageItems);
		if(toIndex < flights.size()) {
			nextButton.setDisable(false);
		}
		else {
			nextButton.setDisable(true);
		}
		
		if(fromIndex != 0) {
			previousButton.setDisable(false);
		}
		else {
			previousButton.setDisable(true);
		}
	}
}

