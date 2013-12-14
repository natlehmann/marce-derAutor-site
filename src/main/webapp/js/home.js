$(document).ready(function() {

	$('.datatable').dataTable({
		"bPaginate" : false,
		"bLengthChange" : false,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : false
	});

	var fechas = $("#calendar_dates").val().replace("[","").replace("]","").split(',');
	var descripciones = $("#calendar_tooltips").val().replace("[","").replace("]","").split(',');

	$('#calendar').datepicker({
		dateFormat : 'dd/mm/yy',
		beforeShowDay : highlightDays,
		onChangeMonthYear : function(year, month, instance) {
			setTimeout( "$( '#calendar .ui-datepicker-calendar tbody' ).tooltip()", '250'); 
		}
	});
	
	$( '#calendar .ui-datepicker-calendar tbody' ).tooltip();

	function highlightDays(date) {

		for (var i = 0; i < fechas.length; i++) {
			if ( $.trim(fechas[i]) == $.trim($.datepicker.formatDate('dd/mm/yy', date)) ) {
				return [ true, 'destacada', descripciones[i].replace(/\'/g, "") ];
			} 
		}
		return [ true, '' ];
	}
	
	inicializarGrafico();
});


function inicializarGrafico() {
    google.setOnLoadCallback(drawChart);
}

function drawChart() {

	var jsonData = $.ajax({
        url: $("#contexto").val() + "home/grafico",
        dataType:"json",
        async: false
        }).responseText;

	var data = new google.visualization.DataTable(jsonData);

    var options = {
      title: document.getElementById('titulo_grafico').value,
      hAxis: {title: document.getElementById('titulo_eje_x').value}
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('grafico_estadisticas'));
    chart.draw(data, options);
  }