<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/fuenteAuditada_listar.js" />' ></script>

<div class="msg">${msg}</div>

<button type="button" onclick="ir('crear')">Nueva fuente</button>

<table class="datatable">
	<thead>
		<tr>
			<th>Nombre</th>
			<th>Acciones</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />