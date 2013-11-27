$(document).ready(function() {
    $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "reglamentoDeDistribucion/listar_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        }
    } );
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