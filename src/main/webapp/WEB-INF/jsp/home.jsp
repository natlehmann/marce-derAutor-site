<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
google.load("visualization", "1", {packages:["corechart"]});
</script>

<script type="text/javascript" src='<c:url value="/js/home.js" />' ></script>

<jsp:include page="/WEB-INF/jsp/filtros_busqueda.jsp">
	<jsp:param value="/home/filtrar" name="action"/>
</jsp:include>


<div class="ranking ranking-home">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>TOP ARTISTAS + EJECUTADOS</h1>
	
	<div class="der">
		<a href='<c:url value="/autoresMasEjecutados"/>'>
			<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
		</a>
	</div>
	
	<div class="Grid">
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
				    <td class="right">${dato.cantidadUnidades}</td>
				  </tr>
		  		</c:forEach>
		  </tbody>
		</table>
	</div>
</div>


<div class="fila">
	<div id="calendario">
	
		<div class="izq">
			<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
		</div>
		
		<h1>CALENDARIO</h1>
		
		<div class="der">
			<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
		</div>
		
		<div class="contenedor_calendario">
			<div id="calendar"></div>
			<input type="hidden" id="calendar_dates" value="${diasDestacados}" />
			<input type="hidden" id="calendar_tooltips" value="${textoDiasDestacados}" />
		</div>
	</div>
	
	
	<div class="ranking ranking-home">
		<div class="izq">
			<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
		</div>
		
		<h1>TOP ARTISTAS + COBRADOS</h1>
		
		<div class="der">
			<a href='<c:url value="/autoresMasCobrados"/>'>
				<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
			</a>
		</div>
		
		<div class="Grid">
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
					    <td class="right">${dato.montoPercibido}</td>
					  </tr>
			  		</c:forEach>
			  </tbody>
			</table>
		</div>
	</div>
	
	<div class="separador"></div>
</div>


<div class="fila">
	<div id="botonera">
		<a href='<c:url value="/visitasTecnicas"/>' class="classname">VISITAS TECNICAS 
			<img src='<c:url value="/images/flecha.png" />' width="17" height="19" />
		</a>
		<br /> 
		<a href='<c:url value="/reglamentoDeDistribucion"/>' class="classname">REG. DE DISTRIBUCION 
			<img src='<c:url value="/images/flecha.png" />' width="17" height="19" />
		</a>
		<br /> 
		<a href='<c:url value="/estadoDeTareas"/>' class="classname">ESTADO	DE TAREAS 
			<img src='<c:url value="/images/flecha.png" />' width="17" height="19" />
		</a>
	</div>


	<div id="estadisticas">
		<div class="izq">
			<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
		</div>
		
		<h1>ESTADISTICAS</h1>
		
		<div class="der">
			<a href='<c:url value="/estadisticas"/>'>
				<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
			</a>
		</div>
		
		<div id="grafico_estadisticas" style="height:240px"></div>
		
		<input type="hidden" id="titulo_grafico" value="${tituloGrafico}" />
		<input type="hidden" id="titulo_eje_x" value="${tituloEjeX}" />
	</div>
	
	<div class="separador"></div>
</div>



<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />
