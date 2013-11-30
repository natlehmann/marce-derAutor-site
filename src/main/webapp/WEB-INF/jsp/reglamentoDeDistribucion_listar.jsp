<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="reglamentoDeDistribucion" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/reglamentoDeDistribucion_listar.js" />' ></script>

<sec:authorize access="hasRole('administrador')">
	<div class="msg">${msg}</div>
	
	<button type="button" onclick="irAbsoluto('admin/reglamentoDeDistribucion/crear')">Nuevo reglamento</button>
</sec:authorize>

Seleccionar fuente:
<select name="fuente" id="fuenteSeleccionada" onchange="filtrarListado()">
	<c:forEach items="${fuentes}" var="fuente">
		<option value="${fuente.nombre}" ${fuente.nombre eq fuenteSeleccionada ? "selected='selected'" : "" }>
			${fuente.nombre}
		</option>
	</c:forEach>
</select>

<table class="datatable">
	<thead>
		<tr>
			<th>Derecho</th>
			<th>Descripción</th>
			<th>Fecha</th>
			<sec:authorize access="hasRole('administrador')">
				<th>Acciones</th>
			</sec:authorize>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />