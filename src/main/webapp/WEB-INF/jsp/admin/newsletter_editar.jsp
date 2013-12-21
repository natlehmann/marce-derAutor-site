<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />


<form:form commandName="newsletter" action="aceptarEdicion" method="POST">

	<form:hidden path="id"/>
	
	<form:hidden path="fechaCreacion"/>

	<div class="campo">
		<form:label path="subject">Subject</form:label>
		<form:errors path="subject" cssClass="error"/>
		<form:input path="subject" cssErrorClass="error"/>
	</div>
	
	<div class="campo">
		<form:label path="contenido">Contenido</form:label>
		<form:errors path="contenido" cssClass="error"/>
		<form:textarea path="contenido" cssErrorClass="error"/>
	</div>
	
	<div class="acciones">
		<form:button value="Aceptar">Aceptar</form:button>
		<button type="button" onclick="ir('listar')">Cancelar</button>
	</div>
	
</form:form>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />