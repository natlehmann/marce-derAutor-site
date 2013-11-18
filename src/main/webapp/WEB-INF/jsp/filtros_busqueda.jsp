<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String action = request.getParameter("action");
	if (action == null) {
		action = "/home/filtrar";
	}
	
	boolean mostrarAutores = request.getParameter("mostrarAutores") != null 
			&& request.getParameter("mostrarAutores").equalsIgnoreCase("true");
%>

<c:set var="url" value="<%= action %>" />

<form action='<c:url value="${url}" />' method="post">
	
	<select name="pais">
		<option value="">TODOS</option>
		<c:forEach items="${paises}" var="pais">
			<option value="${pais.id}" ${paisSeleccionado eq pais.id ? "selected='selected'" : ""}>${pais.nombre}</option>
		</c:forEach>
	</select>
	
	<select name="anio">
		<option value="">TODOS</option>
		<c:forEach items="${anios}" var="anio">
			<option value="${anio}" ${anioSeleccionado eq anio ? "selected='selected'" : ""}>${anio}</option>
		</c:forEach>
	</select>
	
	<select name="trimestre">
		<option value="">TODOS</option>
		<option value="1" ${trimestreSeleccionado eq 1 ? "selected='selected'" : ""}>1</option>
		<option value="2" ${trimestreSeleccionado eq 2 ? "selected='selected'" : ""}>2</option>
		<option value="3" ${trimestreSeleccionado eq 3 ? "selected='selected'" : ""}>3</option>
		<option value="4" ${trimestreSeleccionado eq 4 ? "selected='selected'" : ""}>4</option>
	</select>
	
	<c:if test="<%=mostrarAutores %>">
	
		<select name="autor">
			<option value="">TODOS</option>
			<c:forEach items="${autores}" var="autor">
				<option value="${autor.id}" ${autorSeleccionado eq autor.id ? "selected='selected'" : ""}>${autor.nombre}</option>
			</c:forEach>
		</select>
	</c:if>
	
	<input type="submit" value="Buscar" />
</form>