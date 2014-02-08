<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp">
	<jsp:param value="admin" name="itemMenuSeleccionado"/>
</jsp:include>

<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />


Por favor seleccione una opción del menu.

<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />