package ar.com.mmingrone.estadisticasDerechosAutor

class Usuario {

    static constraints = {
		nombre nullable: false
		password nullable: false
    }
	
	String nombre;
	String password;
}
