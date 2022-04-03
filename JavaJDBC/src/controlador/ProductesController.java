package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Persona;
import model.PersonesDAO;
import model.Productes;
import model.ProductesDAO;

public class ProductesController{
	//Objecte per gestionar la persistència de les dades
	private ProductesDAO productesDAO;
	//Objecte per gestionar el objecte actual
	private Productes producte = null;
	//indicador de nou registre
	private boolean nouRegistre = false;
	//objecte per afegir les files de la taula
	ZoneId defaultZoneId = ZoneId.systemDefault();
	

	//Elements gràfics de la UI
	@FXML
	private AnchorPane anchorPane;
	private Stage ventana;
	@FXML private TextField idField;
	@FXML private TextField nameField;
	@FXML private TextField priceField;
	@FXML private TextField stockField;
	@FXML private DatePicker startCatalogueField;
	@FXML private DatePicker endingCatalogueField;
	@FXML private CheckBox packCheck;
	@FXML private GridPane packInputs;
	private boolean pack = false;

	//Validació dades
	private ValidationSupport vs;

	public void setConexionBD(Connection conexionBD) {	
		//Crear objecte DAO de persones
		productesDAO = new ProductesDAO(conexionBD);
	}
	
	/**
	 * Inicialitza la classe. JAVA l'executa automàticament després de carregar el fitxer fxml amb loader.load()
	 * i abans de rebre l'objecte conexionBD o qualsevol altre que pasem des del IniciMenuController
	 */
	@FXML private void initialize() {
		//idField.setVisible(isDatosValidos());
		//Validació dades
		//https://github.com/controlsfx/controlsfx/issues/1148
		//produeix error si no posem a les VM arguments això: --add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED
		vs = new ValidationSupport();
		vs.registerValidator(idField, true, Validator.createEmptyValidator("ID obligatori"));
		vs.registerValidator(nameField, true, Validator.createEmptyValidator("Nom obligatori"));
		vs.registerValidator(priceField, true, Validator.createEmptyValidator("Preu obligatori"));
		vs.registerValidator(stockField, true, Validator.createEmptyValidator("Stock obligatori"));
		vs.registerValidator(startCatalogueField, true, Validator.createEmptyValidator("Inici de cataleg obligatori"));
		vs.registerValidator(endingCatalogueField, true, Validator.createEmptyValidator("Fi de cataleg obligatori"));

	}

	public Stage getVentana() {
		return ventana;
	}

	public void setVentana(Stage ventana) {
		this.ventana = ventana;
	}

	@FXML private void onKeyPressedId(KeyEvent e) throws IOException {
		if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB){
			//Comprovar si existeix la persona indicada en el control idTextField
			producte = productesDAO.find(Integer.parseInt(idField.getText()));
			mostrarProducte(producte);
		}
	}

	@FXML
	void onActionEliminar(ActionEvent event) {
		if(isDatosValidos()){
			// Mostrar missatge confirmació
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Vol esborrar el producte " + idField.getText() + "?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				if(productesDAO.delete(Integer.parseInt(idField.getText()))){ 
					limpiarFormulario();
					productesDAO.showAll();
				}
			}
		}
	}
	
	@FXML private void onActionListar(ActionEvent e) throws IOException {
		//verificar si les dades són vàlides				
		System.out.println(productesDAO.getProductesList().toString());
	}

	@FXML private void onActionSortir(ActionEvent e) throws IOException {
		sortir();
		//tancar el formulari
		((BorderPane)anchorPane.getParent()).setCenter(null);
	}

	@FXML private void onActionGuardar(ActionEvent e) throws IOException {
		//verificar si les dades són vàlides
		if(isDatosValidos()){
				producte = new Productes(Integer.parseInt(idField.getText()), nameField.getText(), Integer.parseInt(priceField.getText()), Integer.parseInt(stockField.getText()),Date.from(startCatalogueField.getValue().atStartOfDay(defaultZoneId).toInstant()),Date.from(endingCatalogueField.getValue().atStartOfDay(defaultZoneId).toInstant()));
				productesDAO.save(producte);
				limpiarFormulario();
				productesDAO.showAll();
				System.out.println(producte.toString());
			}
		}

	public void sortir(){
		productesDAO.showAll();
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
	
	private void mostrarProducte(Productes producte) {
		if(producte != null){ 
			java.util.Date start = producte.getStartCatalogue();
			LocalDate localstart = new java.sql.Date(start.getTime()).toLocalDate();
			
			java.util.Date end = producte.getEndingCatalogue();
			LocalDate localend = new java.sql.Date(end.getTime()).toLocalDate();
		
			//llegir productes (posar els valors als controls per modificar-los)
			nouRegistre = false;
			idField.setText(String.valueOf(producte.getIdProduct()));
			nameField.setText(producte.getName());
			priceField.setText(producte.getPrice().toString());
			stockField.setText(producte.getStock().toString());
			
			startCatalogueField.setValue(localstart);
			endingCatalogueField.setValue(localend);
		}else{ 
			//nou registre
			nouRegistre = true;
			//idTextField.setText(""); no hem de netejar la PK perquè l'usuari ha posat un valor
			nameField.setText("");
			priceField.setText("");
			stockField.setText("");
			startCatalogueField.setValue(null);
			endingCatalogueField.setValue(null);
		}
	}

	private void limpiarFormulario(){
		idField.setText("");
		nameField.setText("");
		priceField.setText("");
		stockField.setText("");
		startCatalogueField.setValue(null);
		endingCatalogueField.setValue(null);
	}
	
	@FXML
	private void showPack(){
		
		if(!pack) {
			packInputs.setVisible(true);
			pack = true;
		} else {
			packInputs.setVisible(false);
			pack = false;
		}
		//idField.setVisible(isDatosValidos());
	}
}
