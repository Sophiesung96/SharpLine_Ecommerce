//Sales Report By Date
var RequestUrl;
var data;
var chartOptions;
var totalGrossSales;
var totalNetSales;
var totalOrders;
var denominators;
var startDatefield;
var endDatefield;
var days;
var startDate;
var endDate;
var MILLISECOND_A_DAY=24*60*60*1000;


$(document).ready(function () {
    startDatefield=document.getElementById('startDate');
    endDatefield=document.getElementById('endDate');
    $('.button_sales_by_date').on('click',function () {
        divClassRange= $('#divCustomDateRange');

        // Set the color of the other non-clicked button from blue to white(unfilled)
        // while switching between them
        $('.button_sales_by_date').each(function (e) {
            $(this).removeClass('btn-primary').addClass('btn-light');
        })
        //Set the color of current clicked button from white(unfilled)  to white
        $(this).removeClass('btn-light').addClass('btn-primary');
        period=$(this).attr('period');
       if(period){
           loadSalesReportByDate(period);
           divClassRange.addClass('d-none');
           //click the custom range button
       }else{
           divClassRange.removeClass('d-none');
       };
       initCustomDateRange();
       $('#divCustomDateRange').on('click',function (e) {
           validateDateRange();
       })
    })
    //Initiate the Date Range
    function initCustomDateRange(){
        var specificDate = new Date(2021, 2, 15, 12, 30, 0); // March 15, 2023, 12:30 PM
      toDate=specificDate;
      endDatefield.valueAsDate=toDate;
      fromDate=new Date();
      fromDate.setDate(toDate.getDate() -30);
      startDatefield.valueAsDate=fromDate;
    }
})

//Validate whether the start Date that the user chose is within the range
function validateDateRange(){
    days=CalculateDays();
    startDatefield.setCustomValidity(' ');
    //Date Range must be between at least 7 to 30 Days at most
   if(days>=7 &&days<=30 ){
      loadSalesReportByDate('custom')
   }else{
       startDatefield.setCustomValidity('Dates must be in the range of 7..30 days');
       startDatefield.reportValidity();
   }

}
//Calculate Date for customizing Timeframe
function CalculateDays(){
     startDate=startDatefield.valueAsDate;
     endDate=endDatefield.valueAsDate;
     differenceinMilliseconds=endDate-startDate;

     return differenceinMilliseconds/MILLISECOND_A_DAY;

}

function loadSalesReportByDate(period){
    if(period=='custom'){
        startDate=$('#startDate').val();
        endDate=$('#endDate').val();
        RequestUrl='/reports/sales_by_date/'+startDate+'/'+endDate;
    }else{
        RequestUrl='/reports/sales_by_date/'+period;
    }

   $.get(RequestUrl,function (responseJSON) {
      prepareChartData(responseJSON);
      customizeChart(period);
      drawChart();
   });
}

function prepareChartData(responseJSON){
    data=new google.visualization.DataTable();
    data.addColumn('string','Date');
    data.addColumn('number','Gross Sales');
    data.addColumn('number','Net Sales');
    data.addColumn('number','Orders');
    totalGrossSales=0.0;
    totalNetSales=0.0;
    totalOrders=0;
    $.each(responseJSON,function (index,reportItem) {
        data.addRows([[reportItem.identifier,reportItem.grossSales,reportItem.netSales,reportItem.orderCount]]);
        totalGrossSales+=parseFloat(reportItem.grossSales);
        totalNetSales+=parseFloat(reportItem.netSales);
        totalOrders+=parseInt(reportItem.orderCount);
        console.log("Count:",totalOrders);
    })
}

function customizeChart(period){
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
var formatter=new google.visualization.NumberFormat({
    prefix:'$'
});
formatter.format(data,1);
formatter.format(data,2);
}

function drawChart(period){
  var saleChart=new google.visualization.ColumnChart(document.getElementById('chart_sales_by_date'));
  saleChart.draw(data,chartOptions);
    denominators=getDenominators(period);
  $('#textTotalGrossSales').text("$"+totalGrossSales.toFixed(2));
    $('#textNetGrossSales').text("$"+totalNetSales.toFixed(2));
    $('#textAvgGrossSales').text("$"+(totalGrossSales/denominators).toFixed(2));
    $('#textAvgNetSales').text("$"+(totalNetSales/denominators).toFixed(2));
    $('#textTotalOrders').text(totalOrders);
}

//Create Chart title for two different timeframe
function getChartTitle(period){
    if(period=='Last_7_days') return 'Sales in Last 7 Days';
    if(period=='Last_28_days') return 'Sales in Last 28 Days';
    if(period=='Last_6_months')return 'Sales in Last 6 Months';
    if(period=='Last_year') return 'Sales in Last Year';
    if(period=='custom') return 'Custom Date Range';
}

function getDenominators(period){
    if(period=='last_7_days') return 7;
    if(period=='last_28_days') return 28;
    if(period=='Last_6_months')return 60;
    if(period=='Last_year')return 12;
    if(period=='custom') return CalculateDays();
    else{
        return 7;
    }
}