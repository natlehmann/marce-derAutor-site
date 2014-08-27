package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.util.LinkedList;
import java.util.List;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.data.Fuente;

public class MontoTotalPorFuente {
	
	private Fuente fuente;
	
	private List<MontoTotalPorDerecho> montosPorDerecho = new LinkedList<>();

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
	
	public double getTotalSACMPrimerTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getPrimerTrimestreSACM();
		}
		return total;
	}
	
	public double getTotalOtrosPrimerTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getPrimerTrimestreOtros();
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
	
	public double getTotalSACMSegundoTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getSegundoTrimestreSACM();
		}
		return total;
	}
	
	public double getTotalOtrosSegundoTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getSegundoTrimestreOtros();
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
	
	public double getTotalSACMTercerTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getTercerTrimestreSACM();
		}
		return total;
	}
	
	public double getTotalOtrosTercerTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getTercerTrimestreOtros();
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
	
	public double getTotalSACMCuartoTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getCuartoTrimestreSACM();
		}
		return total;
	}
	
	public double getTotalOtrosCuartoTrimestre() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getCuartoTrimestreOtros();
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
	
	public double getTotalSACM() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getTotalSACM();
		}
		return total;
	}
	
	public double getTotalOtros() {
		
		double total = 0;
		for (MontoTotalPorDerecho monto : this.montosPorDerecho) {
			total += monto.getTotalOtros();
		}
		return total;
	}

}
