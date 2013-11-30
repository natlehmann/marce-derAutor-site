<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="visitasTecnicas" name="itemMenuSeleccionado"/>
</jsp:include>


<div class="ranking-centrado ranking-no-sort">
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Source</th>
					<th>Puntos</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${visitasTecnicas}" var="visita">
					<tr>
						<td>${visita.fuente.nombre}</td>
						<td>${visita.puntos}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
</div>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />