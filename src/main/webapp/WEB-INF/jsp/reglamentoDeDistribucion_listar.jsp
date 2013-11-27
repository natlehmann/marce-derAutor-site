<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/reglamentoDeDistribucion_listar.js" />' ></script>

<sec:authorize access="hasRole('administrador')">
	<div class="msg">${msg}</div>
	
	<button type="button" onclick="irAbsoluto('admin/reglamentoDeDistribucion/crear')">Nuevo reglamento</button>
</sec:authorize>

<table class="datatable">
	<thead>
		<tr>
			<th>Derecho</th>
			<th>Descripción</th>
			<th>Fecha</th>
			<th>Fuente</th>
			<sec:authorize access="hasRole('administrador')">
				<th>Acciones</th>
			</sec:authorize>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />