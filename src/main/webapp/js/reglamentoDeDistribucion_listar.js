var datatable;

$(document).ready(function() {
	
	datatable = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "reglamentoDeDistribucion/listar_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "oSearch" : {"sSearch": $("#fuenteSeleccionada").val()}

    } );
	
	$("#dialog-eliminar").dialog({
		resizable : false,
		width : 350,
		modal : true,
		autoOpen : false
	});
    
} );


function verMas(elemento, idReglamento) {
	$.ajax({
		 url: $("#contexto").val() + "reglamentoDeDistribucion/verDescripcion",
		 data: { id: idReglamento },
		 success: function( data ) {
			$(elemento).parent().html(data); 
	 	}
	 });
}

function reducirDescripcion(elemento, idReglamento) {
	$.ajax({
		 url: $("#contexto").val() + "reglamentoDeDistribucion/reducirDescripcion",
		 data: { id: idReglamento },
		 success: function( data ) {
			$(elemento).parent().html(data); 
	 	}
	 });
}

function confirmarEliminar(id) {
	
	$("#dialog-eliminar-id").val(id);
	$("#dialog-eliminar").dialog("open");
}

function filtrarListado() {
	datatable.fnFilter($("#fuenteSeleccionada").val());
	datatable.fnDraw(false);
}