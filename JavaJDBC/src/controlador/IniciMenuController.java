package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class IniciMenuController extends Application {
	
	private Connection conexionBD;

	@FXML
    private BorderPane borderPane;
	
	public IniciMenuController() {
		try{
			//Establir la connexio amb la BD
			String urlBaseDades = "jdbc:postgresql://localhost/postgres";
			String usuari = "postgres";
			String contrasenya = "7533";
			conexionBD = DriverManager.getConnection(urlBaseDades , usuari, contrasenya);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		//Carrega el fitxer amb la interficie d'usuari inicial (Scene)
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/IniciMenuView.fxml"));
		Scene fm_inici = new Scene(loader.load());

		//Li assigna la escena a la finestra inicial (primaryStage) i la mostra
		primaryStage.setScene(fm_inici);
		primaryStage.setTitle("Agenda");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}


	@FXML
	void onActionMenuItemPersones(ActionEvent event) throws IOException {
		//Carrega el fitxer amb la interficie d'usuari
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/PersonesView.fxml"));
		Pane panell = (AnchorPane)loader.load();
		//Crear un objecte de la clase PersonasController ja que necessitarem accedir al mÃ¨todes d'aquesta classe
		PersonesController personesControler = (PersonesController)loader.getController();
		personesControler.setConexionBD(conexionBD);
		
		borderPane.setCenter(panell); 
	}
	
	@FXML
	void onActionMenuItemClients(ActionEvent event) throws IOException {
		//Carrega el fitxer amb la interficie d'usuari
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ClientsView.fxml"));
		Pane panell = (AnchorPane)loader.load();
		//Crear un objecte de la clase PersonasController ja que necessitarem accedir al mÃ¨todes d'aquesta classe
		PersonesController personesControler = (PersonesController)loader.getController();
		personesControler.setConexionBD(conexionBD);
		
		borderPane.setCenter(panell); 
	}
	
	@FXML
	void onActionMenuItemProductes(ActionEvent event) throws IOException {
		//Carrega el fitxer amb la interficie d'usuari
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ProductesView.fxml"));
		Pane panell = (AnchorPane)loader.load();
		//Crear un objecte de la clase PersonasController ja que necessitarem accedir al mÃ¨todes d'aquesta classe
		ProductesController productesControler = (ProductesController)loader.getController();
		productesControler.setConexionBD(conexionBD);
		
		borderPane.setCenter(panell); 
	}
	
	@FXML
	void onActionMenuItemFitxar(ActionEvent event) throws IOException {
		//Carrega el fitxer amb la interficie d'usuari
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FitxarView.fxml"));
		Pane panell = (AnchorPane)loader.load();
		//Crear un objecte de la clase PersonasController ja que necessitarem accedir al mÃ¨todes d'aquesta classe
		TreballadorController treballadorControler = (TreballadorController)loader.getController();
		treballadorControler.setConexionBD(conexionBD);
		
		borderPane.setCenter(panell); 
	}


	@FXML
	void onActionMenuItemSortir(ActionEvent event) {
		Platform.exit();
	}
	

	@Override
	public void stop() throws Exception {
		super.stop();
		
		try {
			if (conexionBD != null) conexionBD.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
