<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<div id="login">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg"/>' width="14" height="34" />
	</div>
	
	<h1>PEDIDO DE DESUSCRIPCIÓN</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg"/>' width="31" height="34" />
	</div>
	
	<h3>Usted ha solicitado dejar de recibir el newsletter de BMat.<br/> 
	Si desea recibirlo nuevamente, envíenos un mail a mmingrone@bmat.com</h3>
	
	
	<div id="crear">
		<button type="button" onclick="irAbsoluto('home')">Home</button>
	</div>
		
	
</div>


	<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />