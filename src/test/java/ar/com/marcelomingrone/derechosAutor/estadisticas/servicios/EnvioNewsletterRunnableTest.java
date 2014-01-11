package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Usuario;

public class EnvioNewsletterRunnableTest {
	
	private static final String NEWSLETTER_FOLDER_NAME = "newsletter";
	private static final String IMG_PREFIX = "prefijo_";
	private static final String NEWSLETTER_HOME = "/var/www/newsletter";
	private static final String LINK_DESUSCRIPCION = "<div id='suscripcion'>Para dejar de recibir newsletters de BMAT, por favor hacer <a href=''>click aqui</a></div>";
	private static final String BASE_URL = "http://localhost:8080/derechosAutor";
	
	private EnvioNewsletterRunnable servicio;
	private Map<String, String> imagenes;
	
	@Before
	public void init() {
		
		servicio = new EnvioNewsletterRunnable();
		servicio.setNewsletterFolderName(NEWSLETTER_FOLDER_NAME);
		servicio.setImgPrefix(IMG_PREFIX);
		servicio.setNewsletterHome(NEWSLETTER_HOME);
		servicio.setLinkDesuscripcion(LINK_DESUSCRIPCION);
		servicio.setBaseUrl(BASE_URL);
		
		imagenes = new LinkedHashMap<>();
	}

	@Test
	public void reemplazarUnaImagenContenido() {
		
		String html = "<html><body><img id='img' src=\"http://www.marcelomingrone.com.ar/newsletter/2014-01-01/imgA.jpg\" "
				+ "width='123' height='321' border='0' /></body></html>";
		
		Document document = Jsoup.parse(html);
		
		servicio.reemplazarImagenesContenido(document, imagenes);
		
		String nuevaUrlImagen = document.getElementById("img").attr("src");
		
		assertEquals("cid:" + IMG_PREFIX + "2014-01-01_imgA.jpg", nuevaUrlImagen);
		
		assertEquals(1, imagenes.size());
		assertEquals(NEWSLETTER_HOME + "/2014-01-01/imgA.jpg", imagenes.get(IMG_PREFIX + "2014-01-01_imgA.jpg"));
	}
	
	@Test
	public void reemplazarConRutaInvalida() {
		
		String html = "<html><body><img id='img' src=\"http://www.marcelomingrone.com.ar/INVALIDO/2014-01-01/imgA.jpg\" "
				+ "width='123' height='321' border='0' /></body></html>";
		
		Document document = Jsoup.parse(html);
		
		servicio.reemplazarImagenesContenido(document, imagenes);
		
		String urlImagen = document.getElementById("img").attr("src");
		
		assertEquals("http://www.marcelomingrone.com.ar/INVALIDO/2014-01-01/imgA.jpg", urlImagen);
		
		assertEquals(0, imagenes.size());
	}
	
	@Test
	public void agregarLinksUsuario() {
		
		Usuario usuario = new Usuario();
		usuario.setId(3L);
		
		String documentoConUnTag = "<html><body><div>HOLA</div></body></html>";
		Document document = Jsoup.parse(documentoConUnTag);
		
		servicio.agregarLinksUsuario(document, usuario);
		
		StringBuffer esperado = new StringBuffer();
		esperado
			// comienzo documento
			.append("<html><head></head><body><div>HOLA</div>")
			// abre link desuscripcion
			.append("<div id=\"suscripcion\">Para dejar de recibir newsletters de BMAT, por favor hacer<a href=\"")
			// se agrego la URL para el usuario
			.append(BASE_URL + "/newsletter/desuscribir/3")
			// cierra link desuscripcion
			.append("\">click aqui</a></div>")
			// cierra documento
			.append("</body></html>");
		
	    String output = document.html().replaceAll("[\\r\\n]+\\s", "").replaceAll(">\\s*", ">").replaceAll("\\s*<", "<");
		
		assertEquals(esperado.toString(), output);
	}
	
	
	@Test
	public void agregarLinksUsuarioCuandoYaExistiaOtro() {
		
		Usuario usuario = new Usuario();
		usuario.setId(4L);
		
		String documento = "<html><body><div>HOLA</div><div id=\"suscripcion\">"
				+ "Para dejar de recibir newsletters de BMAT, por favor hacer<a href=\"" 
				+ BASE_URL + "/newsletter/desuscribir/3\">click aqui</a></div>" + "</body></html>";
		
		Document document = Jsoup.parse(documento);
		
		servicio.agregarLinksUsuario(document, usuario);
		
		StringBuffer esperado = new StringBuffer();
		esperado
			// comienzo documento
			.append("<html><head></head><body><div>HOLA</div>")
			// abre link desuscripcion
			.append("<div id=\"suscripcion\">Para dejar de recibir newsletters de BMAT, por favor hacer<a href=\"")
			// se agrego la URL para el usuario
			.append(BASE_URL + "/newsletter/desuscribir/4")
			// cierra link desuscripcion
			.append("\">click aqui</a></div>")
			// cierra documento
			.append("</body></html>");
		
	    String output = document.html().replaceAll("[\\r\\n]+\\s", "").replaceAll(">\\s*", ">").replaceAll("\\s*<", "<");
		
		assertEquals(esperado.toString(), output);
	}

}
