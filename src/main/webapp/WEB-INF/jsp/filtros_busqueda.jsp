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


<div id="busca">
	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>SEARCH</h1>
	<div class="der">
		<img src=<c:url value="/images/h1Der.jpg" /> width="31" height="34" />
	</div>
	
	<form action='<c:url value="${url}" />' method="post">
	
		<h2>Country</h2>
		
		<div class="dropdown">
			<select name="pais" class="dropdown-select">
				<option value="">Select...</option>
				<c:forEach items="${paises}" var="pais">
					<option value="${pais.id}" ${paisSeleccionado eq pais.id ? "selected='selected'" : ""}>${pais.nombre}</option>
				</c:forEach>
			</select>
		</div>

		<h2>Years</h2>
		
		<div class="dropdown">
			<select name="anio" class="dropdown-select">
				<option value="">Select...</option>
				<c:forEach items="${anios}" var="anio">
					<option value="${anio}" ${anioSeleccionado eq anio ? "selected='selected'" : ""}>${anio}</option>
				</c:forEach>
			</select>
		</div>
		
		<h2>Quarter</h2>
		
		<div class="dropdown">
			<select name="trimestre" class="dropdown-select">
				<option value="">Select...</option>
				<option value="1" ${trimestreSeleccionado eq 1 ? "selected='selected'" : ""}>1</option>
				<option value="2" ${trimestreSeleccionado eq 2 ? "selected='selected'" : ""}>2</option>
				<option value="3" ${trimestreSeleccionado eq 3 ? "selected='selected'" : ""}>3</option>
				<option value="4" ${trimestreSeleccionado eq 4 ? "selected='selected'" : ""}>4</option>
			</select>
		</div>
		
		
		<c:if test="<%=mostrarAutores %>">
		
			<h2>Artist</h2>
			
			<div class="dropdown">
				<input name="nombreAutor" value="${nombreAutor}" id="autorAutocomplete" class="dropdown-select" />
				<input type="hidden" name="autor" value="${autorSeleccionado}" id="autorId" />
			</div>
	
		</c:if>
		
		
		<div id="buscaBot">
			<input type="submit" value="Buscar" />
			<a href="#" class="classname">SEARCH <img src="image/flecha.png"
				width="17" height="19" /></a>
		</div>
		
	</form>
</div>


