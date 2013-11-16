$(document).ready(function() {
    $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/fechaDestacada/listar_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers" 
    } );
} );