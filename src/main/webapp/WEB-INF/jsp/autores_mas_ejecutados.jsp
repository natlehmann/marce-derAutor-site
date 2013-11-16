<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<script type="text/javascript" src='<c:url value="/js/autores_mas_ejecutados.js" />' ></script>

<jsp:include page="/WEB-INF/jsp/filtros_busqueda.jsp">
	<jsp:param value="/home/filtrar" name="action"/>
</jsp:include>


<table class="datatable">
	<thead>
		<tr>
			<th>Artista</th>
			<th>Cantidad unidades</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />