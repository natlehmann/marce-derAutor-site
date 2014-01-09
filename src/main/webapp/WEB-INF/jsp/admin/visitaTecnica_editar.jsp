<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>${visitaTecnica.id == null ? "CREAR " : "MODIFICAR " } VISITA TÉCNICA</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>

	<div class="msg">${msg}</div>
	
	<div class="error">${msgError}</div>
	
	<form action="aceptarEdicion" method="POST" id="puntajesForm">
	
		<input type="hidden" name="id" value="${visitaTecnica.id}" />
	
		<input type="hidden" name="idFuente" value="${visitaTecnica.fuenteAuditada.id}" />
		
		<div class="campo">
			<label>Fecha</label>
			<input type="text" name="fecha" 
				value='<fmt:formatDate value="${visitaTecnica.fecha}" pattern="dd/MM/yyyy"/>' class="datepicker"/>
		</div>
	
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
				<td><fmt:formatNumber value="${visitaTecnica.puntajeTotal}" maxFractionDigits="2"/></td>
			</tr>
		</table>
		
		
		<div class="acciones">
			<button type="button" onclick="enviarPuntajes()">Aceptar</button>
		</div>
		
	</form>
	
</div>


