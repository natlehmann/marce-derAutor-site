<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />


<form:form commandName="itemAuditoria" action="aceptarEdicion" method="POST">

	<form:hidden path="id"/>

	<div class="campo">
		<form:label path="orden">Orden</form:label>
		<form:errors path="orden" cssClass="error"/>
		<form:input path="orden" maxlength="4"  cssErrorClass="error"/>
	</div>
	
	<div class="campo">
		<form:label path="nombre">Nombre</form:label>
		<form:errors path="nombre" cssClass="error"/>
		<form:input path="nombre" maxlength="255" cssErrorClass="error"/>
	</div>
	
	<div class="campo">
		<form:label path="puntaje">Puntaje</form:label>
		<form:errors path="puntaje" cssClass="error"/>
		<form:input path="puntaje" maxlength="4" cssErrorClass="error"/>
	</div>
	
	<div class="acciones">
		<form:button value="Aceptar">Aceptar</form:button>
		<button type="button" onclick="ir('listar')">Cancelar</button>
	</div>
	
</form:form>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />