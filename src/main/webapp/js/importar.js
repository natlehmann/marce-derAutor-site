$(function() {
	$("#dialog-confirmacion").dialog({
		resizable : false,
		width : 350,
		modal : true,
		autoOpen : false
	});
});

function confirmarImportacion() {
	$("#dialog-confirmacion").dialog("open");
}

function iniciarImportacion() {

	cerrarDialog('dialog-confirmacion');
	$("#statusImportacion").show();

	$.ajax({
		type : "POST",
		url : $("#urlImportacion").val(),
		async : true,
		data : {
			'archivo' : $("#nombreArchivo").val()
		},
		dataType : 'html',
		success : function(data) {
			$("#resultadoImportacion").html(data);
		}
	});
}

function cerrarDialog(idDialog) {
	$("#" + idDialog).dialog("close");
}