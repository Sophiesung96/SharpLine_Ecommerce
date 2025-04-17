// Sales Report By Product
var ProductURL;
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
    SetUpButtonEventHandler('_product',loadSalesReportByDateForProduct);
})




function loadSalesReportByDateForProduct(period,startTime,endTime){
    if(period=='custom'){
        startDate=startTime ;
        endDate=endTime;
        console.log(" Product startDate:",startDate);
        console.log("Product endDate:",endDate);
        ProductURL='/reports/product/'+startDate+'/'+endDate;
        console.log("Product URL",ProductURL);
    }else{
        ProductURL='/reports/product/'+period;
    }
    //Retrieve the data by calling REST API
    $.get(ProductURL,function (responseJSON) {
        prepareChartDataByProduct(responseJSON);
        customizeChartByProduct();
        formatChartData(data,2,3);
        drawChartByProduct();
        setSalesAmount(period,'_product','Total Products');
    });
}

function prepareChartDataByProduct(responseJSON){
    data=new google.visualization.DataTable();
    data.addColumn('string','Product');
    data.addColumn('number','Quantity');
    data.addColumn('number','Gross Sales');
    data.addColumn('number','Net Sales');
    totalGrossSales=0.0;
    totalNetSales=0.0;
    totalItems=0;
    $.each(responseJSON,function (index,reportItem) {
        data.addRows([[reportItem.identifier,reportItem.productCount,reportItem.grossSales,reportItem.netSales]]);
        totalGrossSales+=parseFloat(reportItem.grossSales);
        totalNetSales+=parseFloat(reportItem.netSales);
        totalItems+=parseInt(reportItem.productCount);
        console.log("Count:",totalItems);
    })
}

function customizeChartByProduct(){
    chartOptions={
        height:360,width:'80%',
        showRowNumber:true,
        page:'enable',
        sortColumn:2,
        sortAscending:false
    };
}

function drawChartByProduct(){
    var saleChart=new google.visualization.Table(document.getElementById('chart_sales_by_product'));
    saleChart.draw(data,chartOptions);

}

