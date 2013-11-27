<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />


<form:form commandName="reglamentoDeDistribucion" action="aceptarEdicion" method="POST">

	<form:hidden path="id"/>
	
	<div class="campo">
		<form:label path="fuente.id">Fuente</form:label>
		<form:errors path="fuente.id" cssClass="error"/>
		<form:select path="fuente.id">
			<form:options items="${fuentes}" itemLabel="nombre" itemValue="id"/>
		</form:select>
	</div>
	
	<div class="campo">
		<form:label path="derecho.nombre">Derecho</form:label>
		<form:errors path="derecho.nombre" cssClass="error"/>
		<form:select path="derecho.nombre">
			<form:options items="${derechos}" itemLabel="nombre" itemValue="nombre"/>
		</form:select>
	</div>

	<div class="campo">
		<form:label path="descripcion">Descripción</form:label>
		<form:errors path="descripcion" cssClass="error"/>
		<form:textarea path="descripcion" cssErrorClass="error"/>
	</div>
	
	<div class="campo">
		<form:label path="fecha">Fecha</form:label>
		<form:errors path="fecha" cssClass="error"/>
		<form:input path="fecha" cssClass="datepicker"/>
	</div>
	
	
	<div class="acciones">
		<form:button value="Aceptar">Aceptar</form:button>
		<button type="button" onclick="irAbsoluto('reglamentoDeDistribucion')">Cancelar</button>
	</div>
	
</form:form>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />