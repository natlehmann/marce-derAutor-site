<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/WEB-INF/jsp/includes/header.jsp" />
<jsp:include page="/WEB-INF/jsp/includes/admin_menu.jsp" />

<script type="text/javascript" src='<c:url value="/js/newsletter_listar.js" />' ></script>

<div class="msg">${msg}</div>

<div id="crear">
	<button type="button" onclick="ir('crear')">Nuevo newsletter</button>
</div>

<div id="adminInt">

	<div class="izq">
		<img src='<c:url value="/images/h1Izq.jpg" />' width="14" height="34" />
	</div>
	
	<h1>NEWSLETTERS</h1>
	
	<div class="der">
		<img src='<c:url value="/images/h1Der.jpg" />' width="31" height="34" />
	</div>
	
	<div class="Grid">

		<table class="datatable">
			<thead>
				<tr>
					<th>Fecha creación</th>
					<th>Subject</th>
					<th>Contenido</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
	</div>
</div>


<div class="divider"><br/></div>

<div class="recomendaciones">
<h4>A tener en cuenta al crear un nuevo newsletter</h4>
<ul>
	<li>Las imágenes que vaya a contener el newsletter se deben copiar por FTP al servidor adentro de la carpeta <b>newsletter</b>.</li>
	<li>En ningún caso se debe cambiar el nombre de la carpeta <b>newsletter</b>, pero si es posible crear
	dentro todos los subdirectorios que se deseen para organizar las imágenes subidas.</li>
	<li>Las imágenes subidas podrán luego accederse de la siguiente manera:
		<pre>http://www.marcelomingrone.com.ar/newsletter/miDirectorio/mi_imagen.jpg</pre>
	</li>
	<li>Armar el newsletter referenciando a las imágenes subidas con paths absolutos (no es
	posible utilizar paths relativos). Por ejemplo:
		<pre>&lt;img src="http://www.marcelomingrone.com.ar/newsletter/miDirectorio/mi_imagen.jpg" /&gt;</pre>
	</li>
	<li>Un newsletter armado de esta manera se puede abrir perfectamente en un browser para verificarlo.</li>
	<li>El newsletter debe comenzar con el tag &lt;html&gt; directamente. No debería incluirse ninguna linea
	antes que esa.</li>
	<li>Es preferible reducir la cantidad de imágenes al mínimo. Cuantas más imágenes tenga un newsletter
	más tardará el envío de cada mail.</li>
	<li>El newsletter <b>no debe incluir</b> el link de desuscripción, ya que esto lo agrega automáticamente
	el sistema.</li>
	<li>Gmail no soporta que los estilos se definan por separado dentro del tag &lt;head&gt; y luego se referencien 
		con atributos class o id. Es necesario que los estilos estén embebidos. Por ejemplo:
		<pre>
			en lugar de  &lt;div class="negrita"&gt;.....<br/>
			se debe hacer  &lt;div style="font-style:bold;"&gt;....		
		</pre>
	</li>
	<li>Gmail tampoco soporta que las imagenes se definan en estilos. No se puede poner una imagen como background en un
		atributo "style". Es necesario resolverlo con un tag &lt;img&gt;
	</li>


</ul>
</div>


<div id="dialog-enviar" style="display:none;" title="Confirmación">
	<form action='<c:url value="/admin/newsletter/enviar"/>' method="post">
		
		<input type="hidden" name="id" id="dialog-enviar-id" value="" />
		
		<p>
			<div id="dialog-enviar-msg"></div>
		</p>
		
		<div class="ui-dialog-buttonpane">
			<input type="submit" value="Aceptar" />
			<button type="button" onclick="ir('listar')">Cancelar</button>
		</div>
	</form>
</div>


<jsp:include page="/WEB-INF/jsp/includes/footer.jsp" />