<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Marcelo Mingrone</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<script type="text/javascript" src='<c:url value="/js/jquery-1.10.2.min.js" />' ></script>	
		<script type="text/javascript" src='<c:url value="/js/jquery-ui-1.10.3.custom.min.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/jquery.dataTables.min.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/jquery.ui.datepicker-es.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/master.js" />' ></script>
		
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/estadisticas/jquery-ui-1.10.3.custom.min.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/jquery.dataTables.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/jquery.dataTables_themeroller.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/master.css" />'>
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/jquery-estadisticas.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/login.css" />'>	
	</head>
	
	<%
		String itemMenuSeleccionado = request.getParameter("itemMenuSeleccionado") != null ?
				request.getParameter("itemMenuSeleccionado") : "";
	%>
	
	<body id="mingrone">
		
		<input type="hidden" id="contexto" value='<c:url value="/" />' />
		
		<div class="contenedor">  

        	<div class="top">
            	<div id="logo">
            		<a href='<c:url value="/home"/>'>
	            		<img src='<c:url value="/images/logo.jpg"/>' width="355" height="55" />
            		</a>
            	</div>
            	
            	<sec:authorize access="isAuthenticated()">
		            <div id="logout">
		            	<strong>Usuario:</strong>  <sec:authentication property="principal.username" /> / 
		            	<a href='<c:url value="/logout"/>'>Salir</a>
		            </div>
		       </sec:authorize>
        	</div>
        	
        	
			<div class="menu">
        
            	<ul id="button"> 
            		<li><img src='<c:url value="/images/latMenuIzq.jpg"/>' width="64" height="54" /></li>
            		<li>
            			<a href='<c:url value="/autoresMasEjecutados"/>' 
            				class='<%= itemMenuSeleccionado.equals("autoresMasEjecutados") ? "activo" : "" %>'>
            				TOP ARTISTAS + EJECUTADOS
            			</a></li> 
            		<li>
            			<a href='<c:url value="/autoresMasCobrados"/>' 
            				class='<%= itemMenuSeleccionado.equals("autoresMasCobrados") ? "activo" : "" %>'>
            				TOP ARTISTAS + COBRADOS
            			</a></li> 
            		<li>
            			<a href='<c:url value="/visitasTecnicas"/>'
            				class='<%= itemMenuSeleccionado.equals("visitasTecnicas") ? "activo" : "" %>'>
            				VISITAS TECNICAS
            			</a></li> 
            		<li>
            			<a href='<c:url value="/reglamentoDeDistribucion"/>'
            				class='<%= itemMenuSeleccionado.equals("reglamentoDeDistribucion") ? "activo" : "" %>'>
            				REGLAMENTO DE DISTRIBUCION
            			</a></li> 
            		<li>
            			<a href='<c:url value="/estadoDeTareas"/>'
            				class='<%= itemMenuSeleccionado.equals("estadoDeTareas") ? "activo" : "" %>'>
            				ESTADO DE TAREAS
            			</a></li> 
            			
            			
            		<sec:authorize access="hasRole('administrador')">	
            			<li>
            				<a href='<c:url value="/admin"/>'
	            				class='<%= itemMenuSeleccionado.equals("admin") ? "activo" : "" %>'>
	            				ADMIN.
            				</a>
            			</li> 
            		</sec:authorize>
            		
            	</ul>
            	<div id="ImgFin"><img src='<c:url value="/images/latDerMenu.jpg"/>' width="64" height="54" /></div>
			</div>
	