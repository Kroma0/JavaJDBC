package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Treballador {
	
	private Integer idTreballador;
	private Date entrada;
	private Date sortida;
	
	public Treballador(Integer idTreballador, Date entrada, Date sortida) {
		super();
		this.idTreballador = idTreballador;
		this.entrada = entrada;
		this.sortida = sortida;
	}


	public Integer getIdTreballador() {
		return idTreballador;
	}

	public void setIdTreballador(Integer idTreballador) {
		this.idTreballador = idTreballador;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public Date getSortida() {
		return sortida;
	}

	public void setSortida(Date sortida) {
		this.sortida = sortida;
	}

	@Override
	public String toString() {
		return "Treballador [idTreballador=" + idTreballador + ", entrada=" + entrada + ", sortida=" + sortida + "]";
	}
	
	

	
}
