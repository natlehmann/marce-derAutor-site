<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src='<c:url value="/js/admin.js" />' ></script>

<div class="admin-menu">
	<ul>
		<li><a href='<c:url value="/admin/importar" />'>Importar nuevos datos</a></li>
		<li><a href='<c:url value="/admin/historialImportacion/listar" />'>Historial de importaciones</a></li>
		<li><a href='<c:url value="/admin/fechaDestacada/listar" />'>Fechas destacadas</a></li>
		<li><a href='<c:url value="/admin/itemAuditoria/listar" />'>Items de auditoría</a></li>
		<li><a href='<c:url value="/admin/fuenteAuditada/listar" />'>Sources para visitas técnicas</a></li>
		<li><a href='<c:url value="/admin/visitaTecnica/cargar" />'>Cargar visita técnica</a></li>
		<li><a href='<c:url value="/estadoDeTareas" />'>Estado de tareas</a></li>
		<li><a href='<c:url value="/admin/derecho/listar" />'>Derechos</a></li>
		<li><a href='<c:url value="/reglamentoDeDistribucion" />'>Reglamento de distribución</a></li>
		<li><a href='<c:url value="/admin/newsletter/listar" />'>Newsletters</a></li>
		<li><a href='<c:url value="/admin/usuario/listar" />'>Usuarios receptores de newsletters</a></li>
	</ul>
</div>



<div id="dialog-eliminar" style="display:none;" title="Confirmación">
	<form action="eliminar" method="post">
		
		<input type="hidden" name="id" id="dialog-eliminar-id" value="" />
		
		<p>
			<span id="dialog-eliminar-mensaje">
				¿Está seguro que desea eliminar este elemento?
			</span>
		</p>
		
		<div class="ui-dialog-buttonpane">
			<input type="submit" value="Aceptar" />
			<button type="button" onclick="ir('listar')">Cancelar</button>
		</div>
	</form>
</div>