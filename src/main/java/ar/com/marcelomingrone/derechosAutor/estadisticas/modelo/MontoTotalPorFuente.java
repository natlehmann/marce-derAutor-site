package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

public class MontoTotalPorFuente {
	
	private Fuente fuente;
	
	private List<MontoTotalPorDerecho> montosPorDerecho;

	public Fuente getFuente() {
		return fuente;
	}

	public void setFuente(Fuente fuente) {
		this.fuente = fuente;
	}

	public List<MontoTotalPorDerecho> getMontosPorDerecho() {
		return montosPorDerecho;
	}

	public void setMontosPorDerecho(List<MontoTotalPorDerecho> montosPorDerecho) {
		this.montosPorDerecho = montosPorDerecho;
	}
	
	public void agregarMontoPorDerecho(MontoTotalPorDerecho monto) {
		if (this.montosPorDerecho == null) {
			this.montosPorDerecho = new LinkedList<>();
		}
		this.montosPorDerecho.add(monto);
	}
	
	public double getTotalPrimerTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getPrimerTrimestreSACM() + monto.getPrimerTrimestreOtros();
		}
		return total;
	}
	
	public double getTotalSegundoTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getSegundoTrimestreSACM() + monto.getSegundoTrimestreOtros();
		}
		return total;
	}
	
	public double getTotalTercerTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getTercerTrimestreSACM() + monto.getTercerTrimestreOtros();
		}
		return total;
	}
	
	public double getTotalCuartoTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getCuartoTrimestreSACM() + monto.getCuartoTrimestreOtros();
		}
		return total;
	}
	
	public double getTotal() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getTotalSACM() + monto.getTotalOtros();
		}
		return total;
	}

}
