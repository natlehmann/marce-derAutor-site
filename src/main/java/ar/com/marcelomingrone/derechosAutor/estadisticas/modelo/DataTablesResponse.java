package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

public class DataTablesResponse {
	
	private Long sEcho;
	
	private long iTotalRecords;
	
	private int iTotalDisplayRecords;
	
	private List<List<String>> aaData;
	
	public DataTablesResponse() {}
	
	public DataTablesResponse(List<? extends Entidad> lista, Long sEcho, long cantidadTotal) {
		this.sEcho = sEcho;
		this.iTotalRecords = cantidadTotal;
		this.iTotalDisplayRecords = lista.size();
		
		this.aaData = new LinkedList<>();
		for (Entidad entidad : lista) {
			aaData.add(entidad.getCamposAsList());
		}
		
	}

	public Long getsEcho() {
		return sEcho;
	}

	public void setsEcho(Long sEcho) {
		this.sEcho = sEcho;
	}

	public long getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<List<String>> getAaData() {
		return aaData;
	}

	public void setAaData(List<List<String>> aaData) {
		this.aaData = aaData;
	}

}
