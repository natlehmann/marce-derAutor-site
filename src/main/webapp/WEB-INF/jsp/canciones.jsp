<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<script type="text/javascript" src='<c:url value="/js/canciones.js" />' ></script>

<jsp:include page="/WEB-INF/jsp/filtros_busqueda.jsp">
	<jsp:param value="/canciones/filtrar" name="action"/>
	<jsp:param value="true" name="mostrarAutores"/>
</jsp:include>


<table class="datatable">
	<thead>
		<tr>
			<th>Canción</th>
			<th>Artista</th>
			<th>Cantidad unidades</th>
			<th>Monto percibido</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />