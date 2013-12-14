package ar.com.marcelomingrone.derechosAutor.estadisticas.modelo;

import java.io.Serializable;

public class MontoTotalPorDerecho implements Serializable {
	
	private static final long serialVersionUID = -1682367537803211852L;

	private Derecho derecho;
	
	private double primerTrimestreSACM;
	
	private double primerTrimestreOtros;
	
	private double segundoTrimestreSACM;
	
	private double segundoTrimestreOtros;
	
	private double tercerTrimestreSACM;
	
	private double tercerTrimestreOtros;
	
	private double cuartoTrimestreSACM;
	
	private double cuartoTrimestreOtros;

	public Derecho getDerecho() {
		return derecho;
	}

	public void setDerecho(Derecho derecho) {
		this.derecho = derecho;
	}

	public double getPrimerTrimestreSACM() {
		return primerTrimestreSACM;
	}

	public void setPrimerTrimestreSACM(double primerTrimestreSACM) {
		this.primerTrimestreSACM = primerTrimestreSACM;
	}

	public double getPrimerTrimestreOtros() {
		return primerTrimestreOtros;
	}

	public void setPrimerTrimestreOtros(double primerTrimestreOtros) {
		this.primerTrimestreOtros = primerTrimestreOtros;
	}

	public double getSegundoTrimestreSACM() {
		return segundoTrimestreSACM;
	}

	public void setSegundoTrimestreSACM(double segundoTrimestreSACM) {
		this.segundoTrimestreSACM = segundoTrimestreSACM;
	}

	public double getSegundoTrimestreOtros() {
		return segundoTrimestreOtros;
	}

	public void setSegundoTrimestreOtros(double segundoTrimestreOtros) {
		this.segundoTrimestreOtros = segundoTrimestreOtros;
	}

	public double getTercerTrimestreSACM() {
		return tercerTrimestreSACM;
	}

	public void setTercerTrimestreSACM(double tercerTrimestreSACM) {
		this.tercerTrimestreSACM = tercerTrimestreSACM;
	}

	public double getTercerTrimestreOtros() {
		return tercerTrimestreOtros;
	}

	public void setTercerTrimestreOtros(double tercerTrimestreOtros) {
		this.tercerTrimestreOtros = tercerTrimestreOtros;
	}

	public double getCuartoTrimestreSACM() {
		return cuartoTrimestreSACM;
	}

	public void setCuartoTrimestreSACM(double cuartoTrimestreSACM) {
		this.cuartoTrimestreSACM = cuartoTrimestreSACM;
	}

	public double getCuartoTrimestreOtros() {
		return cuartoTrimestreOtros;
	}

	public void setCuartoTrimestreOtros(double cuartoTrimestreOtros) {
		this.cuartoTrimestreOtros = cuartoTrimestreOtros;
	}
	
	public double getTotalSACM() {
		return this.primerTrimestreSACM + this.segundoTrimestreSACM + this.tercerTrimestreSACM
				+ this.cuartoTrimestreSACM;
	}
	
	public double getTotalOtros() {
		return this.primerTrimestreOtros + this.segundoTrimestreOtros + this.tercerTrimestreOtros
				+ this.cuartoTrimestreOtros;
	}

	public void setMontoSACM(double monto, int trimestre) {
		
		switch(trimestre) {
		
		case 1: this.setPrimerTrimestreSACM(monto);
				break;
		
		case 2: this.setSegundoTrimestreSACM(monto);
				break;
		
		case 3: this.setTercerTrimestreSACM(monto);
				break;
		
		case 4: this.setCuartoTrimestreSACM(monto);
				break;
		}
		
	}

	public void setMontoOtros(double monto, int trimestre) {
		
		switch(trimestre) {
		
		case 1: this.setPrimerTrimestreOtros(monto);
				break;
		
		case 2: this.setSegundoTrimestreOtros(monto);
				break;
		
		case 3: this.setTercerTrimestreOtros(monto);
				break;
		
		case 4: this.setCuartoTrimestreOtros(monto);
				break;
		}
		
	}

}
