//Sales Report By Date
var RequestUrl;
var data;
var chartOptions;
var totalGrossSales;
var totalNetSales;
var totalItems;
var denominators;
var days;
var startDate;
var endDate;
var divClassRange;

$(document).ready(function () {
    SetUpButtonEventHandler('_date',loadSalesReportByDate);
})




function loadSalesReportByDate(period,startTime,endTime){
    if(period=='custom'){
        startDate=startTime ;
        endDate=endTime;
        console.log(" Patameterized startDate:",startDate);
        console.log("Patameterized endDate:",endDate);
        RequestUrl='/reports/sales_by_date/'+startDate+'/'+endDate;
        console.log("url",RequestUrl);
    }else{
        RequestUrl='/reports/sales_by_date/'+period;
    }
  //Retrieve the data by calling REST API
  $.get(RequestUrl,function (responseJSON) {
      prepareChartDataByDate(responseJSON);
      customizeChartByDate(period);
      formatChartData(data,1,2);
      drawChartByDate();
      setSalesAmount(period,'_date','Total Orders');
   });
}

function prepareChartDataByDate(responseJSON){
    data=new google.visualization.DataTable();
    data.addColumn('string','Date');
    data.addColumn('number','Gross Sales');
    data.addColumn('number','Net Sales');
    data.addColumn('number','Orders');
    totalGrossSales=0.0;
    totalNetSales=0.0;
    totalItems=0;
    $.each(responseJSON,function (index,reportItem) {
        data.addRows([[reportItem.identifier,reportItem.grossSales,reportItem.netSales,reportItem.orderCount]]);
        totalGrossSales+=parseFloat(reportItem.grossSales);
        totalNetSales+=parseFloat(reportItem.netSales);
        totalItems+=parseInt(reportItem.orderCount);
        console.log("Count:",totalItems);
    })
}

function customizeChartByDate(period){
chartOptions={
    title:getChartTitle(period),
    'height':360,
    legend:{'position':'top'},
    series:{
        0:{targetAxisIndex:0},
        1:{targetAxisIndex:0},
        2:{targetAxisIndex:1}
    },
    vAxes:{
        0:{title:'Sales Amount',format:'currency'},
        1:{title:'Number of Orders'}
    }
};
}

function drawChartByDate(){
  var saleChart=new google.visualization.ColumnChart(document.getElementById('chart_sales_by_date'));
  saleChart.draw(data,chartOptions);

}

