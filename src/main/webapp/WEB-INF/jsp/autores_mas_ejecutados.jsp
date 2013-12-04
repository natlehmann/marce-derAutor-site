<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="autoresMasEjecutados" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/autores_mas_ejecutados.js" />' ></script>

<jsp:include page="/WEB-INF/jsp/filtros_busqueda.jsp">
	<jsp:param value="/autoresMasEjecutados/filtrar" name="action"/>
</jsp:include>

<div class="ranking ranking-no-sort" id="rankingInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>TOP ARTISTAS + EJECUTADOS</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Ranking</th>
					<th>Artista</th>
					<th>Cantidad unidades</th>
					<th>Monto percibido</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
	
</div>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />