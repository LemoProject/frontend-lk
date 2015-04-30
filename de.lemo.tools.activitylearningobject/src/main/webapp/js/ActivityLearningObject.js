(function(d3custom, $, _) {

	d3custom.run = function() {

		var data = d3custom.data;
		var locale = data.pop();
		var chart;

		$(".row").append('<div id="viz"><svg class="nvd3Svg"><!-- d3js visualization --></svg></div>');
		if (!data) {
			// TODO there's some nvd3/d3 function for this case
			$("#viz").prepend($('<div class="alert">No matching data found. Please check your filter setting.</div>'));
			return;
		}

		var dataRequests = data[0].values;
		var dataUser = data[1].values;
		var nameSortToggle = false;
		var maxPerPage = 30,
		page = 1,
		pages = 1;

		var sortData = function(nameToggle){

			dataRequests.sort(function(a, b) {
				if(nameToggle){ // sorting by name
					console.log("Sorting by name");
					if ( a.x < b.x )
						return -1;
					if ( a.x > b.x )
						return 1;
					return 0;
				} else {
					console.log("Sorting by value")
					return (b.y - a.y); // sorting by value
				}
			});
			dataUser.sort(function(a, b) {

				var result = [];

				for (var i = 0; i<dataRequests.length; i++) {
					for (var j = 0; j<dataUser.length; j++) {
						if (dataRequests[i].x == dataUser[j].x) {
							result.push(dataUser[j]);
							j=dataUser.length;
						}
					}
				}

				data[1].values = result;

			});
		}


		var sortToggle = function() {
			console.log("SortToggle function fired ... nameSortToggle Before:"+nameSortToggle);
			nameSortToggle = !nameSortToggle;
			console.log("nameSortToggle After:"+nameSortToggle);
			sortData(nameSortToggle);
			console.log("Chart: "+chart )
			chart.update();
		}

		$( "#sortToggle" ).bind( "click", function(event, ui) {
			console.log("SortToggel pressed"); 
			sortToggle(); 
		});

		//sorting data
		sortData(nameSortToggle);

		var perPage = data[0].values.length;

		while (perPage > maxPerPage) {

			pages++;
			perPage = data[0].values.length/pages;

		}   

		realData = jQuery.extend(true, [], data);
		realData[0].values = realData[0].values.slice((page-1)*perPage,page*perPage);
		realData[1].values = realData[1].values.slice((page-1)*perPage,page*perPage);
/*		for (var i = 0; i < realData[0].values.length; i++) {
			realData[0].values[i].x = realData[0].values[i].x.substring(0,realData[0].values[i].x.indexOf("_"));
			realData[1].values[i].x = realData[1].values[i].x.substring(0,realData[1].values[i].x.indexOf("_"));
		}*/	
		
		$("#pages").html('' + page + "/" + pages);

		if (pages > 1)
			$("#pagination").show();
		if (page == 1)
			$("#prev").hide();
		if (page == pages)
			$("#next").hide();

		nv.addGraph(function() {
			chart = nv.models.multiBarChart().showControls(false).reduceXTicks(false);


			chart.yAxis.tickFormat(d3.format('d'));
			d3.select('#viz svg').datum(realData).transition().duration(500).call(chart);
			d3.selectAll('.nv-x text').attr('transform', 'translate(0,5)rotate(45)').style('text-anchor', 'start');

			var rects = d3.selectAll('rect').style("fill", function(d, i) { if (detail(d.series)) {return hashColor(d.x.substring(0,d.x.lastIndexOf("_")))} else {return d.x === undefined ? "blue" : d3.rgb(hashColor(d.x.substring(0,d.x.lastIndexOf("_")))).brighter().toString();} });
			function detail(d){
				return d % 2 === 0;
			}
			nv.utils.windowResize(chart.update);

			dataExport.barChartButton('.export-button', d3.select('#viz svg').data(), chart, locale);
			return chart;
		});  

		next = function(bool) {
			if (bool) {
				page++;
			} else {
				page--;
			}
			if (page == 1)
				$("#prev").hide();
			else
				$("#prev").show();
			if (page == pages)
				$("#next").hide();
			else
				$("#next").show();
			$("#pages").html('' + page + "/" + pages);      

			realData = jQuery.extend(true, [], data);
			realData[0].values = realData[0].values.slice((page-1)*perPage,page*perPage);
			realData[1].values = realData[1].values.slice((page-1)*perPage,page*perPage);
		/*	for (var i = 0; i < realData[0].values.length; i++) {
				realData[0].values[i].x = realData[0].values[i].x.substring(0,realData[0].values[i].x.indexOf("_"));
				realData[1].values[i].x = realData[1].values[i].x.substring(0,realData[1].values[i].x.indexOf("_"));
			}*/

			d3.select('#viz svg').datum(realData).transition().duration(500).call(chart);
			d3.selectAll('.nv-x text').attr('transform', 'translate(0,5)rotate(45)').style('text-anchor', 'start');
			var rects = d3.selectAll('rect').style("fill", function(d, i) { if (d.series % 2 === 0) {return hashColor(d.x.substring(0,d.x.lastIndexOf("_")));} else {return d.x === undefined ? "blue" : d3.rgb(d.x.substring(0,d.x.lastIndexOf("_"))).brighter().toString();} }); 			
		}

	};


})(window.d3custom = window.d3custom || {}, jQuery);

$(document).ready(window.d3custom.run);
