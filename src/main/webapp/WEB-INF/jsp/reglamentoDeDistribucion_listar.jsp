<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="reglamentoDeDistribucion" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/reglamentoDeDistribucion_listar.js" />' ></script>

<sec:authorize access="hasRole('administrador')">
	<c:if test="${msg != null}">
		<div class="msg">${msg}</div>
	</c:if>
	
	<div id="crear">
		<button type="button" onclick="irAbsoluto('admin/reglamentoDeDistribucion/crear')">Nuevo reglamento</button>
	</div>
</sec:authorize>



<div id="busca">
	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>SEARCH</h1>
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<h2>Seleccionar fuente</h2>
	
	<div class="dropdown">
		<select name="fuente" id="fuenteSeleccionada" onchange="filtrarListado()" class="dropdown-select">
			<c:forEach items="${fuentes}" var="fuente">
				<option value="${fuente.nombre}" ${fuente.nombre eq fuenteSeleccionada ? "selected='selected'" : "" }>
					${fuente.nombre}
				</option>
			</c:forEach>
		</select>
	</div>
		
</div>


<div class="ranking ranking-no-sort">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>REGLAMENTO DE DISTRIBUCIÓN</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="Grid reglamentoDistribucion">

		<table class="datatable">
			<thead>
				<tr>
					<th>Derecho</th>
					<th>Descripción</th>
					<th>Fecha</th>
					<sec:authorize access="hasRole('administrador')">
						<th>Acciones</th>
					</sec:authorize>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>


<sec:authorize access="hasRole('administrador')">
	<div id="dialog-eliminar" style="display:none;" title="Confirmación">
		<form action='<c:url value="/admin/reglamentoDeDistribucion/eliminar" />' method="post">
			
			<input type="hidden" name="id" id="dialog-eliminar-id" value="" />
			
			<p>
				¿Está seguro que desea eliminar este elemento?
			</p>
			
			<div class="ui-dialog-buttonpane">
				<input type="submit" value="Aceptar" />
				<button type="button" onclick="irAbsoluto('reglamentoDeDistribucion')">Cancelar</button>
			</div>
		</form>
	</div>
</sec:authorize>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />