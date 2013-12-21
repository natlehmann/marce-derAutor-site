<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/newsletter_listar.js" />' ></script>

<div class="msg">${msg}</div>

<button type="button" onclick="ir('crear')">Nuevo newsletter</button>

<table class="datatable">
	<thead>
		<tr>
			<th>Fecha creación</th>
			<th>Subject</th>
			<th>Contenido</th>
			<th>Acciones</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>


<div id="dialog-enviar" style="display:none;" title="Confirmación">
	<form action="enviar" method="post">
		
		<input type="hidden" name="id" id="dialog-enviar-id" value="" />
		
		<p>
			<div id="dialog-enviar-msg"></div>
		</p>
		
		<div class="ui-dialog-buttonpane">
			<input type="submit" value="Aceptar" />
			<button type="button" onclick="ir('listar')">Cancelar</button>
		</div>
	</form>
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />