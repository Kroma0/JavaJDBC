package model;

import java.sql.Connection;
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
public class TreballadorDAO {

	private Connection conexionBD;

	public TreballadorDAO(Connection conexionBD) {
		this.conexionBD = conexionBD;
	}
	
	public List<Treballador> getTreballadorList() {
		List<Treballador> treballadorList = new ArrayList<Treballador>();
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM asistencia")) {
			while (result.next()) {
				treballadorList.add(new Treballador(result.getInt("id"), result.getDate("fecha_entrada"), result.getDate("fecha_salida")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return treballadorList;
	}

	public boolean saveEntrada(Treballador treballador){
		java.util.Date entrada = treballador.getEntrada();
		java.sql.Date sqlentrada = new java.sql.Date(entrada.getTime());
		
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(treballador.getIdTreballador()) == null){
				sql = "INSERT INTO asistencia VALUES(?,?,?)";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setInt(i++, treballador.getIdTreballador());
				stmt.setDate(i++, sqlentrada);
				stmt.setDate(i++, null);
			} else{
				sql = "UPDATE asistencia SET fecha_entrada=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setDate(i++, sqlentrada);
				stmt.setInt(i++, treballador.getIdTreballador());
				
			}
			int rows = stmt.executeUpdate();
			if (rows == 1) return true;
			else return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public boolean saveSortida(Treballador treballador){
		java.util.Date sortida = treballador.getSortida();
		java.sql.Date sqlsortida = new java.sql.Date(sortida.getTime());
		
		try {
			String sql = "";
			PreparedStatement stmt = null;
			if (this.find(treballador.getIdTreballador()) == null){
				return false;
			} else{
				sql = "UPDATE asistencia SET fecha_salida=? WHERE id = ?";
				stmt = conexionBD.prepareStatement(sql);
				int i = 1;
				stmt.setDate(i++, sqlsortida);
				stmt.setInt(i++, treballador.getIdTreballador());
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
				sql = "DELETE FROM asistencia WHERE id = ?";
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

	public Treballador find(Integer id){
		if (id == null || id == 0){
			return null;
		}

		Treballador t = null;
		try (PreparedStatement stmt = conexionBD.prepareStatement("SELECT * FROM asistencia WHERE id = ?")){
			stmt.setInt(1, id); //informem el primer paràmetre de la consulta amb ?
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				t = new Treballador(result.getInt("id"), result.getDate("fecha_entrada"), result.getDate("fecha_salida"));
				t.toString();
			}	
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return t;
	}

	public void showAll(){
		try (ResultSet result = conexionBD.createStatement().executeQuery("SELECT * FROM asistencia")) {
			while (result.next()) {
				Treballador t = new Treballador(result.getInt("id"), result.getDate("fecha_entrada"), result.getDate("fecha_salida"));
				t.toString();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

