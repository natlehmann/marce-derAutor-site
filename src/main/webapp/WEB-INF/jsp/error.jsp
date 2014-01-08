<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />

<div id="login">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>${titulo}</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="user">${msg}</div>
    
	<div id="buscaBot">
		<a href='<c:url value="/home"/>' class="classname">
			VOLVER<img src='<c:url value="/images/flecha.png" />' width="17" height="19" />
		</a>
	</div>
	
</div>

<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />