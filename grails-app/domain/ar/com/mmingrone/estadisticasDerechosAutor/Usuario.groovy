package ar.com.mmingrone.estadisticasDerechosAutor

class Usuario {

    static constraints = {
		nombre nullable: false, blank: false, unique: true
		password nullable: false, blank: false
    }
	
	String nombre;
	String password;
}
