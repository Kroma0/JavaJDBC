package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.time.ZoneId;
import java.util.Date;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProductesDAO;
import model.Treballador;
import model.TreballadorDAO;

public class TreballadorController {
	
	private TreballadorDAO treballador;
	
	private Stage ventana;
	@FXML private TextField idField;
	
	ZoneId defaultZoneId = ZoneId.systemDefault();
	
	private ValidationSupport vs;
	
	public void setConexionBD(Connection conexionBD) {	
		//Crear objecte DAO de persones
		treballador = new TreballadorDAO(conexionBD);
	}
	
	@FXML private void initialize() {
		//idField.setVisible(isDatosValidos());
		//Validació dades
		//https://github.com/controlsfx/controlsfx/issues/1148
		//produeix error si no posem a les VM arguments això: --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED
		vs = new ValidationSupport();
		vs.registerValidator(idField, true, Validator.createEmptyValidator("ID obligatori"));

	}
	
	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}
	
	@FXML private void onActionGuardarEntrada(ActionEvent e) throws IOException {
		//verificar si les dades són vàlides				
		if(isDatosValidos()){
			Treballador t = new Treballador(Integer.parseInt(idField.getText()),Date.from(java.time.LocalDate.now().atStartOfDay(defaultZoneId).toInstant()), null);
			treballador.saveEntrada(t);
			limpiarFormulario();
			treballador.showAll();
			System.out.println(t.toString());
		}
	}
	
	@FXML private void onActionGuardarSalida(ActionEvent e) throws IOException {
		//verificar si les dades són vàlides				
		if(isDatosValidos()){
			Treballador t = new Treballador(Integer.parseInt(idField.getText()),Date.from(java.time.LocalDate.now().atStartOfDay(defaultZoneId).toInstant()),Date.from(java.time.LocalDate.now().atStartOfDay(defaultZoneId).toInstant()));

			treballador.saveSortida(t);
			limpiarFormulario();
			treballador.showAll();
			System.out.println(t.toString());
		}
	}
	
	@FXML private void onActionListar(ActionEvent e) throws IOException {
		//verificar si les dades són vàlides				
		System.out.println(treballador.getTreballadorList().toString());
	}

	@FXML private void onActionEliminar(ActionEvent e) throws IOException {
			if(treballador.delete(Integer.parseInt(idField.getText()))){
				limpiarFormulario();
				treballador.showAll();
			}
	}

	@FXML private void onActionSortir(ActionEvent e) throws IOException {

		sortir();
		
		ventana.close();
	}

	public void sortir(){
		treballador.showAll();
	}
	
	private boolean isDatosValidos() {

		//Comprovar si totes les dades són vàlides
		if (vs.isInvalid()) {
			String errors = vs.getValidationResult().getMessages().toString();
			// Mostrar finestra amb els errors
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(ventana);
			alert.setTitle("Camps incorrectes");
			alert.setHeaderText("Corregeix els camps incorrectes");
			alert.setContentText(errors);
			alert.showAndWait();
		
			return false;
		}

		return true;

	}
	
	private void limpiarFormulario(){
		idField.setText("");
	}
}
