<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<script type="text/javascript" src='<c:url value="/js/importar.js" />' ></script>	

	<input type="hidden" id="urlImportacion" value='<c:url value="/admin/iniciar_importacion" />' />
 
	<label>Seleccione un archivo para importar:</label>
	
	<select name="archivo" id="nombreArchivo">
		<c:forEach items="${archivos}" var="archivo">
			<option value="${archivo}">${archivo}</option>
		</c:forEach>
	</select>
	
	<input type="button" value="Aceptar" onclick="confirmarImportacion()"/>
	
	
	<div id="dialog-confirmacion" style="display: none;" title="Está seguro?">
		<p>
		¿Está seguro que desea iniciar la importación?
		Esto eliminará los datos actuales de la base.
		</p>
		<input type="button" value="Aceptar" onclick="iniciarImportacion()">
		<input type="button" value="Cancelar" onclick="cerrarDialog('dialog-confirmacion')" />
	</div>
	
	
	<div id="statusImportacion" style="display: none;">
		Se ha iniciado el proceso de importación. Esto puede llevar varios minutos. Por favor espere
		hasta que le notifiquemos que el proceso ha finalizado.
	</div>
	
	<div id="resultadoImportacion"></div>
 
 
<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
