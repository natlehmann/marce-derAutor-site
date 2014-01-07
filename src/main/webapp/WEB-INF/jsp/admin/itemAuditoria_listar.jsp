<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />


<div class="msg">${msg}</div>

<button type="button" onclick="ir('crear')">Nuevo item</button>

<table class="datatable">
	<thead>
		<tr>
			<th>Orden</th>
			<th>Nombre</th>
			<th>Puntaje</th>
			<th>Acciones</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${items}" var="item">
			<tr>
				<td>${item.orden}</td>
				<td>${item.nombre}</td>
				<td>${item.puntaje}</td>
				<td>
					<a href="modificar?id=${item.id}" class='modificar-link' title='Modificar'></a>
					<a href="#" onclick="confirmarEliminar('${item.id}')" class='eliminar-link' title='Eliminar'></a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />