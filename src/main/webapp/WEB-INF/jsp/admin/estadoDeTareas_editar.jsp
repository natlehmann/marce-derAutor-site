<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<c:url value="/admin/estadoDeTareas/aceptarEdicion" var="formAction" />
<form:form commandName="estadoDeTareas" action="${formAction}" method="POST">

	<form:hidden path="id"/>

	<div class="campo">
		<form:label path="fecha">Fecha</form:label>
		<form:errors path="fecha" cssClass="error"/>
		<form:input path="fecha" cssClass="datepicker"/>
	</div>
	
	<div class="campo">
		<form:label path="autor.id">Artista</form:label>
		<form:errors path="autor.id" cssClass="error"/>
		<form:input path="autor.id"/>
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


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />