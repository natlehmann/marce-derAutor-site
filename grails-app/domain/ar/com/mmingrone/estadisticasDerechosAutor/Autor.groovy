package ar.com.mmingrone.estadisticasDerechosAutor

class Autor {

    static constraints = {
	nombre nullable: false, blank: false
    }

    String nombre;

	Autor() {}

	Autor(Long id, String nombre) {
		this.id = id
		this.nombre = nombre
	}
}
