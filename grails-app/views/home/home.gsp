<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Marcelo Mingrone</title>
	</head>
	<body>
		
		<div id="page-body" role="main">

			<div id="controller-list" role="navigation">
				<h2>Available Controllers:</h2>
				<ul>
					<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
						<li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
					</g:each>
				</ul>
			</div>
		</div>


		<div>
		<%
		   def employeeColumns = [['string', 'Year'], ['number', 'Austria'], ['number', 'Belgium'],['number', 'CzechRepublic'],['number', 'Finland'],['number', 'France'],['number', 'Germany']]
		   def employeeData = [
    ['2003',  1336060,   3817614,       974066,       1104797,   6651824,  15727003],
    ['2004',  1538156,   3968305,       928875,       1151983,   5940129,  17356071],
    ['2005',  1576579,   4063225,       1063414,      1156441,   5714009,  16716049],
    ['2006',  1600652,   4604684,       940478,       1167979,   6190532,  18542843],
    ['2007',  1968113,   4013653,       1037079,      1207029,   6420270,  19564053],
    ['2008',  1901067,   6792087,       1037327,      1284795,   6240921,  19830493]]   
		%>

		<gvisualization:columnCoreChart elementId="table" width="${800}" height="${600}" columns="${employeeColumns}" data="${employeeData}" select="selectHandler" /> 
		<div id="table"></div>

		</div>

	</body>
</html>
