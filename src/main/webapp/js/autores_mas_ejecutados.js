$(document).ready(function() {
    $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "autoresMasEjecutados_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "bSort": false,
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        }
    } );
} );