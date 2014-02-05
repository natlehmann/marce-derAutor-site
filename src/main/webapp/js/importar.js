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
	$("#archivoAImportar").val( $("#nombreArchivo").val() );
	$("#dialog-confirmacion").dialog("open");
}

function iniciarConsultaStatus() {
	
	$( "#progressBar" ).progressbar({
		max: 100,
		value: 0
	});
	
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
			
			if ( isNumber(data) ) {
				$( "#progressBar" ).progressbar( "option", "value",  parseInt(data) );
			
			} else {
				$("#progressBar").hide();
				$("#resultadoImportacion").html(data);
				frenarConsultaStatus();
			}
		}
	});
}

function completarDuracionEstimada() {
	
	setTimeout(function(){
		
		$.ajax({
			type : "POST",
			url : $("#contexto").val() + "admin/consultar_duracion",
			async : true,
			dataType : 'html',
			success : function(data) {
				$("#duracion_estimada").html(data);
			}
		});
		
	},1000);	
}

function cerrarDialog(idDialog) {
	$("#" + idDialog).dialog("close");
}

function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}