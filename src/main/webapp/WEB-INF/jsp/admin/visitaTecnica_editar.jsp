<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="msg">${msg}</div>

<div class="error">${msgError}</div>

<form action="aceptarEdicion" method="POST" id="puntajesForm">

	<input type="hidden" name="id" value="${visitaTecnica.id}" />

	<input type="hidden" name="idFuente" value="${visitaTecnica.fuente.id}" />
	
	<label>Fecha</label>
	<input type="text" name="fecha" 
		value='<fmt:formatDate value="${visitaTecnica.fecha}" pattern="dd/MM/yyyy"/>' class="datepicker"/>

	<table>
		<c:forEach items="${visitaTecnica.puntosAuditoria}" var="item">
			<tr>
				<td>${item.itemAuditoria.nombre}</td>
				<td>
					<input type="text" name="${item.itemAuditoria.id}" value="${item.puntajeAsignado}"/>
				</td>
				<td>
					${item.itemAuditoria.puntaje}
				</td>
				<td>
					${item.puntajePonderado}
				</td>
			</tr>
		</c:forEach>
		
		<tr>
			<td>TOTAL</td>
			<td><br/></td>
			<td><br/></td>
			<td>${visitaTecnica.puntajeTotal}</td>
		</tr>
	</table>
	
	<button type="button" onclick="enviarPuntajes()">Aceptar</button>
	
</form>


