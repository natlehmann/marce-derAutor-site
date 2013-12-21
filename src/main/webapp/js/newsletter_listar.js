$(document).ready(function() {
    $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/newsletter/listar_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        }
    } );
    
    $("#dialog-enviar").dialog({
		resizable : false,
		width : 350,
		modal : true,
		autoOpen : false
	});
} );

function confirmarEnviar(id) {
	
	$.ajax({
		 url: $("#contexto").val() + "admin/newsletter/validarEnvio",
		 data: { 'id': id },
		 success: function( data ) {
			 $("#dialog-enviar-id").val(id);
			 $("#dialog-enviar-msg").html(data);
			 $("#dialog-enviar").dialog("open");
	 	}
	 });
}