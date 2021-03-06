$(document).ready(function() {
    var listado = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "canciones_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "bSort": false,
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "bAutoWidth" : false,
        "aoColumns": [
                      {"sWidth" : "60%"},
                      { "sClass": "right", "sWidth" : "15%" },
                      { "sClass": "right", "sWidth" : "15%" }
                    ],
        "iDisplayLength": 10,
        "aLengthMenu": [[10], [10]]
    } );
    
    setTimeout(function (){
    	listado.fnFilterOnEnter();
    }, 3000);
    
    $( "#autorAutocomplete" ).autocomplete({
    	 source: function( request, response ) {
    		 $.ajax({
    			 url: $("#contexto").val() + "buscarAutorPorNombre",
    			 dataType: "json",
    			 data: { autor: request.term },
    			 success: function( data ) {
	    			 response( $.map( data, function( item ) {
	    				 return {
	    					 label: item.nombre,
	    					 id: item.id
	    				 };
	    			 }));
    		 	}
    		 });
    	},
    	minLength: 2,
    	select: function( event, ui ) {
    		if (ui.item) {
    			$("#autorId").val(ui.item.id);
    		}
    	}
    });
} );