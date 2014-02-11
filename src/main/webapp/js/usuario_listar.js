$(document).ready(function() {
    $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/usuario/listar_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        }
    } );
} );


function confirmarEliminarUsuario(idUsuario) {
	
	$.ajax({
		type : "POST",
		url : $("#contexto").val() + "admin/usuario/confirmarEliminar",
		data: {
			'id': idUsuario
		},
		async : false,
		dataType : 'html',
		success : function(data) {
			$("#dialog-eliminar-mensaje").html(data);
			confirmarEliminar(idUsuario);
		}
	});
	
}