<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/visitaTecnica_editar.js" />' ></script>

<select name="fuente" onchange="seleccionarFuente()" id="fuenteSeleccionada">
	<option value="">-- Seleccionar --</option>
	<c:forEach items="${fuentes}" var="fuente">
		<option value="${fuente.id}">${fuente.nombre}</option>
	</c:forEach>
</select>


<div id="visitaTecnicaForm"></div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />