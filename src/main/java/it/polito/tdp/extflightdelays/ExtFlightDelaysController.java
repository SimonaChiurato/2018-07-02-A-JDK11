/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Adiacenza;
import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="distanzaMinima"
	private TextField distanzaMinima; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizza"
	private Button btnAnalizza; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxAeroportoPartenza"
	private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

	@FXML // fx:id="btnAeroportiConnessi"
	private Button btnAeroportiConnessi; // Value injected by FXMLLoader

	@FXML // fx:id="numeroVoliTxtInput"
	private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaItinerario"
	private Button btnCercaItinerario; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaAeroporti(ActionEvent event) {
		this.txtResult.clear();
		this.cmbBoxAeroportoPartenza.getItems().remove(0,this.cmbBoxAeroportoPartenza.getItems().size()-1);
		String input = this.distanzaMinima.getText();
		int x = 0;
		if (!input.matches("[0-9]+")) {
			txtResult.appendText("Devi inserire un valore numerico intero");
			return;
		} else {
			x = Integer.parseInt(input);
		}
		this.model.creaGrafo(x);
		txtResult.appendText("Arco creato con # vertici: " + this.model.vertici().size() + " # archi: "
				+ this.model.archi().size() + "\n");
		this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.vertici());
	}

	@FXML
	void doCalcolaAeroportiConnessi(ActionEvent event) {
		this.txtResult.clear();
		Airport partenza = this.cmbBoxAeroportoPartenza.getValue();
		if(partenza==null) {
			txtResult.appendText("Devi selezionare un aeroporto!\n");
			return;
		}
		List<Adiacenza> adiacenze = this.model.connessi(partenza);
		txtResult.appendText("Aeroporti connessi con: " + partenza + "\n");
		for (Adiacenza a : adiacenze) {
			txtResult.appendText(a + "\n");
		}
	}

	@FXML
	void doCercaItinerario(ActionEvent event) {
		this.txtResult.clear();
		Airport partenza = this.cmbBoxAeroportoPartenza.getValue();
		if(partenza==null) {
			txtResult.appendText("Devi selezionare un aeroporto!\n");
			return;
		}
		String input = this.numeroVoliTxtInput.getText();
		int migliaTotali=0;
		if (!input.matches("[0-9]+")) {
			txtResult.appendText("Devi inserire un valore numerico intero");
			return;
		}else {
			migliaTotali= Integer.parseInt(input);
		}

		List<Airport> result= this.model.percorso(partenza, migliaTotali);
		this.txtResult.appendText("Percorso con maggiori scali e con numero di miglia percorse "+this.model.getMiglia()+"\n");
		for(Airport a: result) {
			this.txtResult.appendText(a+"\n");
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
		assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;

	}
}
