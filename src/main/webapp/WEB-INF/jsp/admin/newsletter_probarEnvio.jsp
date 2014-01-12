<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />


<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>PROBAR ENVIO DEL NEWSLETTER</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>

	<form action='<c:url value="/admin/newsletter/enviarPrueba"/>' method="POST">
	
		<input type="hidden" name="id" value="${idNewsletter}">
		
		<div class="campo">
			<label>Ingrese una dirección de email a la que se enviará el newsletter</label>
			<span class="error">${emailError}</span>
			<input name="email" value="${email}"/>
		</div>
		
		<div class="acciones">
			<input type="submit" value="Enviar" />
			<button type="button" onclick="ir('listar')">Cancelar</button>
		</div>
		
	</form>
	
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />