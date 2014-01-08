<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="visitasTecnicas" name="itemMenuSeleccionado"/>
</jsp:include>


<div class="ranking-centrado ranking-no-sort">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>VISITAS TÉCNICAS</h1>
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
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
						<td class="right"><fmt:formatNumber maxFractionDigits="2" value="${visita.puntajeTotal}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
</div>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />