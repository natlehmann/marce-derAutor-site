package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.dto.ColumnaGrafico.TipoColumna;

public class Grafico implements Serializable {
	
	private static final long serialVersionUID = 1960661740452153296L;

	private List<ColumnaGrafico> cols;
	
	private List<FilaGrafico> rows;

	public List<ColumnaGrafico> getCols() {
		return cols;
	}

	public void setCols(List<ColumnaGrafico> cols) {
		this.cols = cols;
	}

	public List<FilaGrafico> getRows() {
		return rows;
	}

	public void setRows(List<FilaGrafico> rows) {
		this.rows = rows;
	}
	
	public void agregarColumna(ColumnaGrafico columna) {
		if (this.cols == null) {
			this.cols = new LinkedList<>();
		}
		
		this.cols.add(columna);
	}
	
	public void agregarToolTip() {
		ColumnaGrafico columnaGrafico = new ColumnaGrafico();
		columnaGrafico.setType(TipoColumna.TEXTO.getType());
		columnaGrafico.setRole("tooltip");
		agregarColumna(columnaGrafico);
	}
	
	public void agregarFila(FilaGrafico fila) {
		if (this.rows == null) {
			this.rows = new LinkedList<>();
		}
		
		this.rows.add(fila);
	}

}
