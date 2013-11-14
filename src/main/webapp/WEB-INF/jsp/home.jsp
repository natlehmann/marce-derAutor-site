<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<script type="text/javascript" src='<c:url value="/js/home.js" />' ></script>

<form action='<c:url value="/home/filtrar" />' method="post">
	
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
	
	<input type="submit" value="Buscar" />
</form>


<table class="datatable">
	<thead>
	  <tr>
	    <th>Número</th>
	    <th>Artista</th>
	    <th>Unidades</th>
	  </tr>
  </thead>
  <tbody>
  		<c:forEach items="${autoresMasEjecutados}" var="dato" varStatus="indice">
		  <tr>
		  	<td>${indice.count}</td>
		    <td>${dato.nombreAutor}</td>
		    <td>${dato.cantidadUnidades}</td>
		  </tr>
  		</c:forEach>
  </tbody>
</table>


<table class="datatable">
	<thead>
	  <tr>
	    <th>Número</th>
	    <th>Artista</th>
	    <th>Monto</th>
	  </tr>
  </thead>
  <tbody>
  		<c:forEach items="${autoresMasCobrados}" var="dato" varStatus="indice">
		  <tr>
		  	<td>${indice.count}</td>
		    <td>${dato.nombreAutor}</td>
		    <td>${dato.monto}</td>
		  </tr>
  		</c:forEach>
  </tbody>
</table>



<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
