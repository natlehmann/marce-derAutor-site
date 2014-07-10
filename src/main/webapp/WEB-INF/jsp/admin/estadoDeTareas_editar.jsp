<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="admin" name="itemMenuSeleccionado"/>
</jsp:include>

<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/estadoDeTareas_editar.js" />' ></script>

<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>${estadoDeTareas.id == null ? "CREAR " : "MODIFICAR " } ESTADO DE TAREAS</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	

	<c:url value="/admin/estadoDeTareas/aceptarEdicion" var="formAction" />
	<form:form commandName="estadoDeTareas" action="${formAction}" method="POST">
	
		<form:hidden path="id"/>
	
		<div class="campo">
			<form:label path="fecha">Fecha</form:label>
			<form:errors path="fecha" cssClass="error"/>
			<form:input path="fecha" cssClass="datepicker"/>
		</div>
		
		<div class="campo">
			<form:label path="idAutor">Artista</form:label>
			<form:errors path="idAutor" cssClass="error"/>
			<form:hidden path="idAutor" id="autorId"/>
			<input type="text" id="autorAutocomplete" name="nombreAutor" value="${nombreAutor}"
				oninput="limpiarSeleccionAutor()" />
		</div>
		
		<div class="campo">
			<form:label path="asunto">Asunto</form:label>
			<form:errors path="asunto" cssClass="error"/>
			<form:input path="asunto" maxlength="255"/>
		</div>
		
		<div class="campo">
			<form:label path="fuente.id">Fuente</form:label>
			<form:errors path="fuente.id" cssClass="error"/>
			<form:select path="fuente.id">
				<form:option value="">Ninguna</form:option>
				<form:options items="${fuentes}" itemLabel="nombre" itemValue="id"/>
			</form:select>
		</div>
		
		<div class="campo">
			<form:label path="descripcion">Descripción</form:label>
			<form:errors path="descripcion" cssClass="error"/>
			<form:textarea path="descripcion" rows="3" cssErrorClass="error"/>
		</div>
		
		<div class="campo">
			<form:label path="comentario">Comentario</form:label>
			<form:errors path="comentario" cssClass="error"/>
			<form:textarea path="comentario" rows="3" cssErrorClass="error"/>
		</div>
		
		<div class="campo">
			<form:label path="estado">Estado</form:label>
			<form:errors path="estado" cssClass="error"/>
			<form:select path="estado">
				<form:options items="${estados}"/>
			</form:select>
		</div>
		
		<div class="campo">
			<form:label path="prioridad">Prioridad</form:label>
			<form:errors path="prioridad" cssClass="error"/>
			<form:select path="prioridad">
				<form:options items="${prioridades}"/>
			</form:select>
		</div>
		
		<div class="acciones">
			<form:button value="Aceptar">Aceptar</form:button>
			<button type="button" onclick="irAbsoluto('estadoDeTareas')">Cancelar</button>
		</div>
		
	</form:form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />