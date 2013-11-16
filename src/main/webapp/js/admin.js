$(function() {
	$("#dialog-eliminar").dialog({
		resizable : false,
		width : 350,
		modal : true,
		autoOpen : false
	});
});

function confirmarEliminar(id) {
	
	$("#dialog-eliminar-id").val(id);
	$("#dialog-eliminar").dialog("open");
}

function ir(url) {
	window.location.assign(url);
}