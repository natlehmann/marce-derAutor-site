<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


	<form:form method="POST" action="iniciar_importacion">
 
		<form:errors path="*" cssClass="errorblock" element="div" />
		
		<label>Seleccione un archivo para importar:</label>
		
		<select name="archivo">
			<c:forEach items="${archivos}" var="archivo">
				<option value="${archivo}">${archivo}</option>
			</c:forEach>
		</select>
		
		<input type="submit" value="Aceptar" />
 
	</form:form>