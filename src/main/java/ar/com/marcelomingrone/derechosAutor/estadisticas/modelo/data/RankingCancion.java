package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data;

import java.util.LinkedList;
import java.util.List;

import ar.com.marcelomingrone.derechosAutor.estadisticas.controllers.Utils;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Ranking;

public class RankingCancion extends Ranking {
	
	private static final long serialVersionUID = 1910321784680554029L;
	
	public RankingCancion() {}
	
	
	public RankingCancion(Long idCancion, String nombreCancion, Long idAutor, String nombreAutor,
			Long cantidadUnidades, Double monto) {
		
		this.cancion = new Cancion();
		this.cancion.setId(idCancion);
		this.cancion.setNombre(nombreCancion);
		
		this.setIdAutor(idAutor);
		this.setNombreAutor(nombreAutor);
		
		this.setCantidadUnidades(cantidadUnidades);
		this.setMontoPercibido(monto);
	}
	
	
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
		campos.add("$ " + Utils.formatear(getMontoPercibido()));
		
		return campos;
	}
	
	public void setIdCancion(Long idCancion) {
		if (this.cancion == null) {
			this.cancion = new Cancion();
		}
		this.cancion.setId(idCancion);
	}
	
	public void setNombreCancion(String nombreCancion) {
		if (this.cancion == null) {
			this.cancion = new Cancion();
		}
		this.cancion.setNombre(nombreCancion);
	}

}
