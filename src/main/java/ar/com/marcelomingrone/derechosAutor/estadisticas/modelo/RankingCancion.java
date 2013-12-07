package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.ManyToOne;

public class RankingCancion extends Ranking {
	
	private static final long serialVersionUID = 1910321784680554029L;
	
	public RankingCancion() {}
	
	
	public RankingCancion(Long idCancion, String nombreCancion, Long idAutor, String nombreAutor,
			Long cantidadUnidades, Double monto) {
		
		this.cancion = new Cancion();
		this.cancion.setId(idCancion);
		this.cancion.setNombre(nombreCancion);
		
		this.setAutor(new Autor());
		this.getAutor().setId(idAutor);
		this.getAutor().setNombre(nombreAutor);
		
		this.setCantidadUnidades(cantidadUnidades);
		this.setMontoPercibido(monto);
	}
	
	
	@ManyToOne
	private Cancion cancion;
	
	public Cancion getCancion() {
		return cancion;
	}
	
	public void setCancion(Cancion cancion) {
		this.cancion = cancion;
	}
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> campos = new LinkedList<>();
		campos.add(this.cancion.getNombre());
		campos.add(String.valueOf(getCantidadUnidades()));
		campos.add("$ " + getMontoPercibido());
		
		return campos;
	}

}
