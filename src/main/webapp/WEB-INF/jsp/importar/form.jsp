<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/importar.js" />' ></script>	

<c:if test="${enProceso and not errorImportacion}">
<script type="text/javascript">
$(function() {
	iniciarConsultaStatus();
	
	if ($("#duracion_estimada").text() == '') {
		completarDuracionEstimada();
	}
});
</script>
</c:if>


<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>IMPORTAR NUEVOS DATOS</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>

	<c:if test="${not enProceso}">
	
		<div class="Grid">
	
			<div class="campo">
				<label>Seleccione un archivo para importar:</label>
				
				<select name="archivo" id="nombreArchivo">
					<c:forEach items="${archivos}" var="archivo">
						<option value="${archivo}">${archivo}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="acciones">
				<input type="button" value="Aceptar" onclick="confirmarImportacion()"/>
			</div>
			
		</div>
		
	</c:if>
	
	
	<c:if test="${enProceso}">
	
		<div class="Grid">
		
			<div class="campo">
	
				<div id="statusImportacion">
					Se ha iniciado el proceso de importación. Esto puede llevar varios minutos. Por favor espere
					hasta que le notifiquemos que el proceso ha finalizado o puede consultar la pantalla de 
					<a href='<c:url value="/admin/historialImportacion/listar"/>'>Historial de importaciones</a>.
				</div>
				
				<br/>
				
				<div class="info">Tiempo estimado: <span id="duracion_estimada">${duracionEstimada}</span></div>
				
				<br/>
				
				<div id="progressBar"></div>
				
				<div id="resultadoImportacion">
					<c:if test="${errorImportacion != null}">${errorImportacion}</c:if>
				</div>
			
			</div>
		</div>
		
	</c:if>
	
</div>	
	
	
<div id="dialog-confirmacion" style="display: none;" title="Está seguro?">
	<form action='<c:url value="/admin/iniciar_importacion" />' method="post">
		<p>
		¿Está seguro que desea iniciar la importación?
		Esto eliminará los datos actuales de la base.
		</p>
		
		<input type="hidden" name="archivo" id="archivoAImportar">
		<input type="submit" value="Aceptar">
		<input type="button" value="Cancelar" onclick="cerrarDialog('dialog-confirmacion')" />
	</form>
</div>
 
 
<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
