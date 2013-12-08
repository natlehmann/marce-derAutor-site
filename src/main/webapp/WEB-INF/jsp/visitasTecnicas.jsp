<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="visitasTecnicas" name="itemMenuSeleccionado"/>
</jsp:include>


<div class="ranking-centrado ranking-no-sort">
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Fecha</th>
					<th>Source</th>
					<th>Puntos</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${visitasTecnicas}" var="visita">
					<tr>
						<td><fmt:formatDate value="${visita.fecha}" pattern="dd/MM/yyyy"/></td>
						<td>${visita.fuenteAuditada.nombre}</td>
						<td>${visita.puntajeTotal}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
</div>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />