$(document).ready(function() {
    var listado = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/derecho/listar_ajax",
        "bJQueryUI": true,
        "bSort": false,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        }
    } );
    
    setTimeout(function (){
    	listado.fnFilterOnEnter();
    }, 3000);
    
} );