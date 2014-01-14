package ar.com.marcelomingrone.derechosAutor.estadisticas.servicios;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.EnvioNewsletter;
import ar.com.marcelomingrone.derechosAutor.estadisticas.modelo.Usuario;

public class EnvioNewsletterRunnableTest {
	
	private static final String NEWSLETTER_FOLDER_NAME = "newsletter";
	private static final String IMG_PREFIX = "prefijo_";
	private static final String NEWSLETTER_HOME = "/var/www/newsletter";
	private static final String LINK_DESUSCRIPCION = "<div id='suscripcion'>Para dejar de recibir newsletters de BMAT, por favor hacer <a href=''>click aqui</a></div>";
	private static final String BASE_URL = "http://localhost:8080/derechosAutor";
	private static final String LINK_NOTIFICACION = "<img id='img_notificacion' src='' width='1' height='1' border='0'/>";
	
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
		servicio.setLinkNotificacion(LINK_NOTIFICACION);
		
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
		
		EnvioNewsletter envio = new EnvioNewsletter();
		envio.setId(9L);
		
		String documentoConUnTag = "<html><body><div>HOLA</div></body></html>";
		Document document = Jsoup.parse(documentoConUnTag);
		
		servicio.agregarLinksUsuario(document, usuario, envio);
		
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
			// abre link notificacion
			.append("<img id=\"img_notificacion\" src=\"")
			// se agrego URL notificacion
			.append(BASE_URL + "/newsletter/notificar/9/3")
			// cierra link notificacion
			.append("\" width=\"1\" height=\"1\" border=\"0\"/>")
			// cierra documento
			.append("</body></html>");
		
	    String output = document.html().replaceAll("[\\r\\n]+\\s", "").replaceAll(">\\s*", ">")
	    		.replaceAll("\\s*/>\\s*", "/>").replaceAll("\\s*<", "<");
		
		assertEquals(esperado.toString(), output);
	}
	
	
	@Test
	public void agregarLinksUsuarioCuandoYaExistiaOtro() {
		
		Usuario usuario = new Usuario();
		usuario.setId(4L);
		
		EnvioNewsletter envio = new EnvioNewsletter();
		envio.setId(6L);
		
		String documento = "<html><body><div>HOLA</div><div id=\"suscripcion\">"
				+ "Para dejar de recibir newsletters de BMAT, por favor hacer<a href=\"" 
				+ BASE_URL + "/newsletter/desuscribir/3\">click aqui</a></div><img id='img_notificacion' src='" 
				+ BASE_URL + "/newsletter/notificar/5/3' width='1' height='1' border='0'/>"
				+ "</body></html>";
		
		Document document = Jsoup.parse(documento);
		
		servicio.agregarLinksUsuario(document, usuario, envio);
		
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
			// abre link notificacion
			.append("<img id=\"img_notificacion\" src=\"")
			// se agrego URL notificacion
			.append(BASE_URL + "/newsletter/notificar/6/4")
			// cierra link notificacion
			.append("\" width=\"1\" height=\"1\" border=\"0\"/>")
			// cierra documento
			.append("</body></html>");
		
	    String output = document.html().replaceAll("[\\r\\n]+\\s", "").replaceAll(">\\s*", ">")
	    		.replaceAll("\\s*/>\\s*", "/>").replaceAll("\\s*<", "<");
		
		assertEquals(esperado.toString(), output);
	}

}
