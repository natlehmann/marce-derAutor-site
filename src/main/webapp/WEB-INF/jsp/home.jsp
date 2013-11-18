<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<script type="text/javascript" src='<c:url value="/js/home.js" />' ></script>

<jsp:include page="/WEB-INF/jsp/filtros_busqueda.jsp">
	<jsp:param value="/home/filtrar" name="action"/>
</jsp:include>


<div class="titulo">
	<a href='<c:url value="/autoresMasEjecutados"/>'>Top artistas más ejecutados</a>
</div>

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
		    <td>
		    	<c:url value="/canciones/${dato.autor.id}" var="verCanciones" />
		    	<a href='${verCanciones}'>${dato.autor.nombre}</a>
		    </td>
		    <td>${dato.cantidadUnidades}</td>
		  </tr>
  		</c:forEach>
  </tbody>
</table>


<div class="titulo">
	<a href='<c:url value="/autoresMasCobrados"/>'>Top artistas más cobrados</a>
</div>

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
		    <td>
		    	<c:url value="/canciones/${dato.autor.id}" var="verCanciones" />
		    	<a href='${verCanciones}'>${dato.autor.nombre}</a>
		    </td>
		    <td>${dato.montoPercibido}</td>
		  </tr>
  		</c:forEach>
  </tbody>
</table>


<div id="calendar"></div>

<input type="hidden" id="calendar_dates" value="${diasDestacados}" />
<input type="hidden" id="calendar_tooltips" value="${textoDiasDestacados}" />


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
