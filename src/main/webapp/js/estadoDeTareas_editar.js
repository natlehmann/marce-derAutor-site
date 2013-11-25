$(document).ready(function() {
    
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

function limpiarSeleccionAutor() {
	if ( $.trim( $("#autorAutocomplete").val() ) == '') {
		$("#autorId").val("");
	}
}