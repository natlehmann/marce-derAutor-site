<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


	<form:form method="POST" modelAttribute="archivo" enctype="multipart/form-data"
		action="upload">
 
		<form:errors path="*" cssClass="errorblock" element="div" />
 
		Please select a file to upload : <input type="file" name="file" />
		<input type="submit" value="upload" />
		<span><form:errors path="file" cssClass="error" />
		</span>
 
	</form:form>