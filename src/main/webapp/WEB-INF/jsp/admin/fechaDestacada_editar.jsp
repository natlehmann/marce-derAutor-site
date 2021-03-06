<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="admin" name="itemMenuSeleccionado"/>
</jsp:include>

<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />


<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>${fechaDestacada.id == null ? "CREAR " : "MODIFICAR " } FECHA DESTACADA</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>


	<form:form commandName="fechaDestacada" action="aceptarEdicion" method="POST">
	
		<form:hidden path="id"/>
	
		<div class="campo">
			<form:label path="fecha">Fecha</form:label>
			<form:errors path="fecha" cssClass="error"/>
			<form:input path="fecha" cssClass="datepicker"/>
		</div>
		
		<div class="campo">
			<form:label path="descripcion">Descripción</form:label>
			<form:errors path="descripcion" cssClass="error"/>
			<form:textarea path="descripcion" cssErrorClass="error"/>
		</div>
		
		<div class="acciones">
			<form:button value="Aceptar">Aceptar</form:button>
			<button type="button" onclick="ir('listar')">Cancelar</button>
		</div>
	
	</form:form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />