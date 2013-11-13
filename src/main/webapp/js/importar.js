var chequearStatus;

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
		url : $("#contexto").val() + "admin/iniciar_importacion",
		async : false,
		data : {
			'archivo' : $("#nombreArchivo").val()
		},
		dataType : 'html',
		success : function(data) {
			
			if (data != '') {
				$("#resultadoImportacion").html(data);
				
			} else {
				iniciarConsultaStatus();
			}
		}
	});
	
}

function iniciarConsultaStatus() {
	chequearStatus = setInterval(consultarStatus, 1000);
}

function frenarConsultaStatus() {
	clearInterval(chequearStatus);
}

function consultarStatus() {
	$.ajax({
		type : "POST",
		url : $("#contexto").val() + "admin/status_importacion",
		async : true,
		dataType : 'html',
		success : function(data) {
			$("#progressBar").append(data);
		}
	});
}

function cerrarDialog(idDialog) {
	$("#" + idDialog).dialog("close");
}