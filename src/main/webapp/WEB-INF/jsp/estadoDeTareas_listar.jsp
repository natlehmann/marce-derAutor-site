<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<div class="titulo">Estado de tareas</div>

<div class="msg">${msg}</div>

<button type="button" onclick="irAbsoluto('admin/estadoDeTareas/crear')">Nueva tarea</button>


<div class="filtros">
	<form action='<c:url value="/estadoDeTareas/buscar" />' method="POST">
	
		<div>
			Buscar por artista
			<input type="text" name="autor" value="${filtro_autor}" />
		</div>
	
		<div>
			Buscar por asunto
			<input type="text" name="asunto" value="${filtro_asunto}" />
		</div>
		
		<div>
			Buscar por fuente
			<select name="fuente">
				<option value="">-- Seleccionar --</option>
				<c:forEach items="${fuentes}" var="fuente">
					<option value="${fuente.id}" ${fuente.id eq filtro_fuente ? "selected='selected'" : "" }>
						${fuente.nombre}
					</option>
				</c:forEach>
			</select>
		</div>
		
		<div>
			Buscar por estado
			<select name="estado">
				<option value="">-- Seleccionar --</option>
				<c:forEach items="${estados}" var="estado">
					<option value="${estado}" ${estado eq filtro_estado ? "selected='selected'" : "" }>
						${estado}
					</option>
				</c:forEach>
			</select>
		</div>
		
		<div>
			Buscar por prioridad
			<select name="prioridad">
				<option value="">-- Seleccionar --</option>
				<c:forEach items="${prioridades}" var="prioridad">
					<option value="${prioridad}" ${prioridad eq filtro_prioridad ? "selected='selected'" : "" }>
						${prioridad}
					</option>
				</c:forEach>
			</select>
		</div>
		
		<input type="submit" value="Buscar" />
	</form>
</div>

<table class="datatable">
	<thead>
		<tr>
			<th>Fecha</th>
			<th>Artista</th>
			<th>Asunto</th>
			<th>Fuente</th>
			<th>Descripción</th>
			<th>Comentario</th>
			<th>Estado</th>
			<th>Prioridad</th>
			<th>Acciones</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${listado}" var="estadoDeTareas">
			<tr>
				<td><fmt:formatDate value="${estadoDeTareas.fecha}" pattern="dd/MM/yyyy"/></td>
				<td>${estadoDeTareas.autor.nombre}</td>
				<td>${estadoDeTareas.asunto}</td>
				<td>${estadoDeTareas.fuente.nombre}</td>
				<td>${estadoDeTareas.descripcion}</td>
				<td>${estadoDeTareas.comentario}</td>
				<td>${estadoDeTareas.estado}</td>
				<td>${estadoDeTareas.prioridad}</td>
				<td>
					<c:url value="/admin/estadoDeTareas/modificar" var="modificarUrl">
						<c:param name="id" value="${estadoDeTareas.id}"/>
					</c:url>
					<a href='${modificarUrl}'>Modificar</a>
					<a href="#" onclick="confirmarEliminar('${estadoDeTareas.id}')">Eliminar</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>




<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />