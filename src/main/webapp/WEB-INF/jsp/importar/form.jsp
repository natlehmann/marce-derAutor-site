<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<script type="text/javascript" src='<c:url value="/js/importar.js" />' ></script>	

<c:if test="${enProceso and not errorImportacion}">
<script type="text/javascript">
$(function() {
	iniciarConsultaStatus();
});
</script>
</c:if>

	<c:if test="${not enProceso}">
	
		<label>Seleccione un archivo para importar:</label>
		
		<select name="archivo" id="nombreArchivo">
			<c:forEach items="${archivos}" var="archivo">
				<option value="${archivo}">${archivo}</option>
			</c:forEach>
		</select>
		
		<input type="button" value="Aceptar" onclick="confirmarImportacion()"/>
		
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
		
	</c:if>
	
	
	<c:if test="${enProceso}">
	
		<div id="statusImportacion">
			Se ha iniciado el proceso de importación. Esto puede llevar varios minutos. Por favor espere
			hasta que le notifiquemos que el proceso ha finalizado.
		</div>
		
		<div id="progressBar"></div>
		
		<div id="resultadoImportacion">
			<c:if test="${errorImportacion != null}">${errorImportacion}</c:if>
		</div>
		
	</c:if>
 
 
<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
