<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="admin" name="itemMenuSeleccionado"/>
</jsp:include>

<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/historialImportacion_listar.js" />' ></script>

<c:if test="${msg != null}">
	<div class="msg">${msg}</div>
</c:if>

<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>HISTORIAL DE ARCHIVOS IMPORTADOS</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Inicio</th>
					<th>Fin</th>
					<th>Cantidad de registros importación de montos</th>
					<th>Cantidad de registros importación de unidades</th>
					<th>Estado</th>
					<th>Resultado ejecución</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />