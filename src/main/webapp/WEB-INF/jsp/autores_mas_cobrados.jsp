<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="autoresMasCobrados" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/autores_mas_cobrados.js" />' ></script>

<jsp:include page="/WEB-INF/jsp/filtros_busqueda.jsp">
	<jsp:param value="/autoresMasCobrados/filtrar" name="action"/>
</jsp:include>


<div class="ranking ranking-no-sort">
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Ranking</th>
					<th>Artista</th>
					<th>Cant. unidades</th>
					<th>Monto percibido</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>

	</div>
</div>



<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />