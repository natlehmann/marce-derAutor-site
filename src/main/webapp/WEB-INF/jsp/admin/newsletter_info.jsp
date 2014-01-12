<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<div class="msg">${msg}</div>

<div id="crear">
	<button type="button" onclick="ir('listar')">Volver</button>
</div>

<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>ENVIOS DEL NEWSLETTER</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Fecha envío</th>
					<th>Usuarios a los que fue enviado</th>
					<th>Usuarios que lo abrieron</th>
					<th>Errores</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			
				<c:forEach items="${newsletter.envios}" var="envio">
				
					<tr>
						<td><fmt:formatDate value="${envio.fechaEnvio}" pattern="dd/MM/yyyy"/></td>
						<td>${envio.cantidadReceptores}</td>
						<td>${envio.cantidadReceptoresActivos}</td>
						<td>
							<c:choose>
								<c:when test="${empty envio.errores}">
									Ninguno
								</c:when>
								
								<c:otherwise>
									<ul>
										<c:forEach items="${envio.errores}" var="error">
											<li>${error.error}</li>
										</c:forEach>
									</ul>
								</c:otherwise>
							</c:choose>
						</td>
					
						<td>
							<a class='ver-usuarios-link' title='Ver usuarios' 
								href='<c:url value="/admin/newsletter/verReceptores/${envio.id}"/>'>Ver usuarios</a>
						</td>
					</tr>
					
				</c:forEach>
			
			</tbody>
		</table>
		
	</div>
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />