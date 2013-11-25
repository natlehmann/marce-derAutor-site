<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="msg">${msg}</div>

<div class="error">${msgError}</div>

<form action="aceptarEdicion" method="POST" id="puntajesForm">

	<input type="hidden" name="idFuente" value="${idFuente}" />

	<table>
		<c:forEach items="${items}" var="item">
			<tr>
				<td>${item.itemAuditoria.nombre}</td>
				<td>
					<input type="text" name="${item.itemAuditoria.id}" value="${item.puntajeAsignado}"/>
				</td>
				<td>
					${item.itemAuditoria.puntaje}
				</td>
				<td>
					<c:if test="${item.puntajeAsignado >= 0 and item.puntajeAsignado <= 10}">
						${item.puntajeAsignado}
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<button type="button" onclick="enviarPuntajes()">Aceptar</button>
	
</form>


