$(document).ready(function() {
    var listado = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "autoresMasEjecutados_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "bSort": false,
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "bAutoWidth" : false,
        "aoColumns": [
                      {"sWidth" : "10%"},
                      {"sWidth" : "40%"},
                      { "sClass": "right", "sWidth" : "25%" },
                      { "sClass": "right", "sWidth" : "25%" }
                    ],
        "iDisplayLength": 10,
        "aLengthMenu": [[10], [10]]
    } );
    
    setTimeout(function (){
    	listado.fnFilterOnEnter();
    }, 3000);
} );