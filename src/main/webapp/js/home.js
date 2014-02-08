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
			setTimeout( activarTooltips, '250'); 
		}
	});
	
	activarTooltips();

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


function activarTooltips() {
	
	$( '#calendar .ui-datepicker-calendar tbody' ).tooltip({
        content: function(){
            return $(this).attr('title');
        }
    });
}


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
      hAxis: { title: document.getElementById('titulo_eje_x').value,
    	  gridlines : {color : '#d7d7d7'}
      },
      colors : ['#858384', '#ff422f'],
      chartArea : { width: '65%' }
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('grafico_estadisticas'));
    chart.draw(data, options);
  }