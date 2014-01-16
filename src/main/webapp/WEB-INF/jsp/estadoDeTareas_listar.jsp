<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="estadoDeTareas" name="itemMenuSeleccionado"/>
</jsp:include>

<script type="text/javascript" src='<c:url value="/js/estadoDeTareas_editar.js" />' ></script>


<sec:authorize access="hasRole('administrador')">
	<div class="msg">${msg}</div>
	
	<div id="crear">
		<button type="button" onclick="irAbsoluto('admin/estadoDeTareas/crear')">Nueva tarea</button>
	</div>
</sec:authorize>



<div id="busca-ancho-pag">
	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>SEARCH</h1>
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<form action='<c:url value="/estadoDeTareas/buscar" />' method="POST">
	
		<div class="campo">
			<h2>Buscar por artista</h2>
			
			<div class="dropdown">
				<input type="hidden" id="autorId" name="autor" value="${filtro_autor}"/>
				<input type="text" id="autorAutocomplete" name="nombreAutor" value="${nombreAutor}"
					oninput="limpiarSeleccionAutor()" class="dropdown-select"/>
			</div>
		</div>
		
		<div class="campo">
			<h2>Buscar por asunto</h2>
			
			<div class="dropdown">
				<input type="text" name="asunto" value="${filtro_asunto}" class="dropdown-select"/>
			</div>
		</div>
		
		<div class="campo">
			<h2>Buscar por fuente</h2>
			
			<div class="dropdown">
				<select name="fuente" class="dropdown-select">
					<option value="">-- Seleccionar --</option>
					<c:forEach items="${fuentes}" var="fuente">
						<option value="${fuente.id}" ${fuente.id eq filtro_fuente ? "selected='selected'" : "" }>
							${fuente.nombre}
						</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="campo">
			<h2>Buscar por estado</h2>
			
			<div class="dropdown">
				<select name="estado" class="dropdown-select">
					<option value="">-- Seleccionar --</option>
					<c:forEach items="${estados}" var="estado">
						<option value="${estado}" ${estado eq filtro_estado ? "selected='selected'" : "" }>
							${estado}
						</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div class="campo">
			<h2>Buscar por prioridad</h2>
			
			<div class="dropdown">
				<select name="prioridad" class="dropdown-select">
					<option value="">-- Seleccionar --</option>
					<c:forEach items="${prioridades}" var="prioridad">
						<option value="${prioridad}" ${prioridad eq filtro_prioridad ? "selected='selected'" : "" }>
							${prioridad}
						</option>
					</c:forEach>
				</select>
			</div>
		</div>
		
		<div id="buscaBot1">
			<input type="submit" value="Buscar" />
		</div>
		
		<div class="separador"></div>
		
	</form>
		
</div>


<c:set var="esAdmin" value="false"/>
<sec:authorize access="hasRole('administrador')">
	<c:set var="esAdmin" value="true"/>
</sec:authorize>


<div class="ranking-centrado-total ranking-no-sort">
	
	<div class="Grid">

		<table class="datatable estadoTareas">
			<thead>
				<tr>
					<th>Fecha</th>
					<th>Artista</th>
					<th>Asunto</th>
					<th>Fuente</th>
					<th>Estado</th>
					<th>Prior.</th>
					<sec:authorize access="hasRole('administrador')">
						<th style="width: 8%">Acciones</th>
					</sec:authorize>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listado}" var="estadoDeTareas">
					<tr>
						<td class="fecha">
							<fmt:formatDate value="${estadoDeTareas.fecha}" pattern="dd/MM/yyyy"/>
						</td>
						<td class="autor">${estadoDeTareas.autor.nombre}</td>
						<td class="asunto">${estadoDeTareas.asunto}</td>
						<td class="fuente">${estadoDeTareas.fuente.nombre}</td>
						<td class="estado">${estadoDeTareas.estado}</td>
						<td class="prioridad">${estadoDeTareas.prioridad}</td>
						
						<sec:authorize access="hasRole('administrador')">
							<td class="acciones">
								<c:url value="/admin/estadoDeTareas/modificar" var="modificarUrl">
									<c:param name="id" value="${estadoDeTareas.id}"/>
								</c:url>
								<a href='${modificarUrl}' class='modificar-link' title='Modificar'></a>
								<a href="#" onclick="confirmarEliminar('${estadoDeTareas.id}')" class='eliminar-link' title='Eliminar'></a>
							</td>
						</sec:authorize>
					</tr>
					
					<tr>
						<td class="titulo-interno-descripcion">Descripción</td>
						<td colspan="${esAdmin ? 6 : 5 }" class="descripcion">
							${estadoDeTareas.descripcionCorta}
						</td>
					</tr>
					
					<tr>
						<td class="titulo-interno-comentario">Comentario</td>
						<td colspan="${esAdmin ? 6 : 5 }" class="comentario">
							${estadoDeTareas.comentario}
						</td>
					</tr>				
					
				</c:forEach>
			</tbody>
		</table>
		
	</div>
</div>


<sec:authorize access="hasRole('administrador')">
	<div id="dialog-eliminar" style="display:none;" title="Confirmación">
		<form action='<c:url value="/admin/estadoDeTareas/eliminar" />' method="post">
			
			<input type="hidden" name="id" id="dialog-eliminar-id" value="" />
			
			<p>
				¿Está seguro que desea eliminar este elemento?
			</p>
			
			<div class="ui-dialog-buttonpane">
				<input type="submit" value="Aceptar" />
				<button type="button" onclick="irAbsoluto('estadoDeTareas')">Cancelar</button>
			</div>
		</form>
	</div>
</sec:authorize>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />