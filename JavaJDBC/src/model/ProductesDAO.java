package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase per gestionar la persistència de les dades de les persones
 * @author adnan
 *
 */
public class ProductesDAO {
	
	

	private Connection conexionBD;

	public ProductesDAO(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}
	
	public List<Productes> getProductesList() {
		List<Productes> productesList = new ArrayList<Productes>();
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM producto")) {
			while (result.next()) {
				productesList.add(new Productes(result.getInt("id"), result.getString("nombre"), result.getInt("precio"), result.getInt("stock"), result.getDate("fecha_inicio"), result.getDate("fecha_fin")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return productesList;
	}

	public boolean save(Productes producte){
		java.util.Date start = producte.getStartCatalogue();
		java.sql.Date sqlstart = new java.sql.Date(start.getTime());
		
		java.util.Date end = producte.getEndingCatalogue();
		java.sql.Date sqlend = new java.sql.Date(end.getTime());
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(producte.getIdProduct()) == null){
				sql = "INSERT INTO producto VALUES(?,?,?,?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, producte.getIdProduct());
				stmt.setString(i++, producte.getName());
				stmt.setInt(i++, producte.getPrice());
				stmt.setInt(i++, producte.getStock());
				stmt.setDate(i++, sqlstart);
				stmt.setDate(i++, sqlend);
			} else{
				sql = "UPDATE producto SET id=?, nombre=?,precio=?,stock=?,fecha_inicio=?,fecha_fin=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, producte.getIdProduct());
				stmt.setString(i++, producte.getName());
				stmt.setInt(i++, producte.getPrice());
				stmt.setInt(i++, producte.getStock());
				stmt.setDate(i++, sqlstart);
				stmt.setDate(i++, sqlend);
				stmt.setInt(i++, producte.getIdProduct());
			}
			int rows = stmt.executeUpdate();
			if (rows == 1) return true;
			else return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean delete(Integer id){
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(id) != null){
				sql = "DELETE FROM producto WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, id);
			}
			int rows = stmt.executeUpdate();
			if (rows == 1) return true;
			else return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public Productes find(Integer id){
		if (id == null || id == 0){
			return null;
		}

		Productes p = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM producto WHERE id = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				p = new Productes(result.getInt("id"), result.getString("nombre"), result.getInt("precio"), result.getInt("stock"), result.getDate("fecha_inicio"), result.getDate("fecha_fin"));
				p.toString();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return p;
	}

	public void showAll(){
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM producto")) {
			while (result.next()) {
				Productes p = new Productes(result.getInt("id"), result.getString("nombre"), result.getInt("precio"), result.getInt("stock"), result.getDate("fecha_inicio"), result.getDate("fecha_fin"));
				p.toString();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

