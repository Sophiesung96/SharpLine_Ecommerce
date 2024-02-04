//Sales Report By Category
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
    SetUpButtonEventHandler('_category',loadSalesReportByDateForCategory);
})




function loadSalesReportByDateForCategory(period,startTime,endTime){
    if(period=='custom'){
        startDate=startTime ;
        endDate=endTime;
        console.log(" Patameterized startDate:",startDate);
        console.log("Patameterized endDate:",endDate);
        RequestUrl='/reports/category/'+startDate+'/'+endDate;
        console.log("URL",RequestUrl);
    }else{
        RequestUrl='/reports/category/'+period;
    }
    //Retrieve the data by calling REST API
   $.get(RequestUrl,function (responseJSON) {
        prepareChartDataByCategory(responseJSON);
        customizeChartByCategory();
        formatChartData(data,1,2);
        drawChartByCategory();
        setSalesAmount(period,'_category','Total Products');
    });
}

function prepareChartDataByCategory(responseJSON){
    data=new google.visualization.DataTable();
    data.addColumn('string','Category');
    data.addColumn('number','Gross Sales');
    data.addColumn('number','Net Sales');
    totalGrossSales=0.0;
    totalNetSales=0.0;
    totalItems=0;
    $.each(responseJSON,function (index,reportItem) {
        data.addRows([[reportItem.identifier,reportItem.grossSales,reportItem.netSales]]);
        totalGrossSales+=parseFloat(reportItem.grossSales);
        totalNetSales+=parseFloat(reportItem.netSales);
        totalItems+=parseInt(reportItem.productCount);
        console.log("Count:",totalItems);
    })
}

function customizeChartByCategory(){
    chartOptions={
        height:360,legend:{position:'right'}
    };
}

function drawChartByCategory(){
    var saleChart=new google.visualization.PieChart(document.getElementById('chart_sales_by_category'));
    saleChart.draw(data,chartOptions);

}

