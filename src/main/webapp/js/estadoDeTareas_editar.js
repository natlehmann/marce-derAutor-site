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
    
    $("#dialog-eliminar").dialog({
		resizable : false,
		width : 350,
		modal : true,
		autoOpen : false
	});
    
} );

function limpiarSeleccionAutor() {
	if ( $.trim( $("#autorAutocomplete").val() ) == '') {
		$("#autorId").val("");
	}
}

function verMas(elemento, idEstadoDeTareas) {
	$.ajax({
		 url: $("#contexto").val() + "estadoDeTareas/verDescripcion",
		 data: { id: idEstadoDeTareas },
		 success: function( data ) {
			$(elemento).parent().html(data); 
	 	}
	 });
}

function reducirDescripcion(elemento, idEstadoDeTareas) {
	$.ajax({
		 url: $("#contexto").val() + "estadoDeTareas/reducirDescripcion",
		 data: { id: idEstadoDeTareas },
		 success: function( data ) {
			$(elemento).parent().html(data); 
	 	}
	 });
}

function confirmarEliminar(id) {
	
	$("#dialog-eliminar-id").val(id);
	$("#dialog-eliminar").dialog("open");
}