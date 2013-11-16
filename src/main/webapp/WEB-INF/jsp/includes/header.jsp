<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

	<head>
		<title>Marcelo Mingrone</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<script type="text/javascript" src='<c:url value="/js/jquery-1.10.2.min.js" />' ></script>	
		<script type="text/javascript" src='<c:url value="/js/jquery-ui-1.10.3.custom.min.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/jquery.dataTables.min.js" />' ></script>
		<script type="text/javascript" src='<c:url value="/js/jquery.ui.datepicker-es.js" />' ></script>
		
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />'>	
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/jquery.dataTables_themeroller.css" />'>	
	</head>
	
	<body id="estadisticas">
		
		<input type="hidden" id="contexto" value='<c:url value="/" />' />
	