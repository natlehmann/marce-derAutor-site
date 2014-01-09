<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/visitaTecnica_editar.js" />' ></script>

<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>CARGAR VISITA TÉCNICA</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="Grid">

		<div class="campo">
			<select name="fuente" onchange="seleccionarFuente()" id="fuenteSeleccionada">
				<option value="">-- Seleccionar fuente --</option>
				<c:forEach items="${fuentes}" var="fuente">
					<option value="${fuente.id}">${fuente.nombre}</option>
				</c:forEach>
			</select>
		</div>
		
	</div>
</div>


<div id="visitaTecnicaForm"></div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />