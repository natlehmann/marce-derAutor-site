package ar.com.mmingrone.estadisticasDerechosAutor

class Usuario {

    static constraints = {
		nombre nullable: false, blank: false
		password nullable: false, blank: false
    }
	
	String nombre;
	String password;
}
