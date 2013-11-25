package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

public class VisitaTecnica implements Comparable<VisitaTecnica> {
	
	private Fuente fuente;
	
	private double puntos;

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public double getPuntos() {
		return puntos;
	}

	public void setPuntos(double puntos) {
		this.puntos = puntos;
	}

	@Override
	public int compareTo(VisitaTecnica otro) {
		if (this.puntos < otro.puntos) {
			return 1;
			
		} else {
			if (this.puntos == otro.puntos) {
				return 0;
			}
		}
		
		return -1;
	}

}
