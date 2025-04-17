// Generating Basic Sales Report Function
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
var toDate;
var parametrizedStartDate;
var parameterizedEndDate;

function SetUpButtonEventHandler(reportType,callbackFunction) {
    startDatefield = document.getElementById('startDate'+ reportType);
    endDatefield = document.getElementById('endDate'+ reportType);
    $('.button-sales-by'+ reportType).on('click', function () {

        // Set the color of the other non-clicked button from blue to white(unfilled)
        // while switching between them
        $('.button-sales-by'+ reportType).each(function (e) {
            $(this).removeClass('btn-primary').addClass('btn-light');
        })
        //Set the color of current clicked button from white(unfilled)  to white
        $(this).removeClass('btn-light').addClass('btn-primary');
        period = $(this).attr('period');
        console.log(period)
        if (period) {
            callbackFunction(period);
            $('#divCustomDateRange'+ reportType).addClass('d-none');
            //click the custom range button
        } else {
            $('#divCustomDateRange'+ reportType).removeClass('d-none');
        }
        ;
        initCustomDateRange(reportType);

        $('#divCustomDateRange'+ reportType).on('click', function (e) {
            validateDateRange(reportType);
        })
    });
}


// Initiate the Date Range
function initCustomDateRange(reportType) {
    var specificDate = new Date(2021, 2, 15, 12, 30, 0); // March 15, 2021, 12:30 PM
    endDatefield = document.getElementById('endDate' + reportType);
    if (endDatefield !== null) {
        endDatefield.valueAsDate = specificDate;
    }
    console.log(specificDate);

    var fromDate = new Date(specificDate);
    fromDate.setDate(specificDate.getDate() - 30);
    console.log(fromDate);
     startDatefield = document.getElementById('startDate' + reportType);
    if (startDatefield !== null) {
        startDatefield.valueAsDate = fromDate;
    }
}



//Validate whether the start Date that the user chose is within the range
function validateDateRange(reportType){
    startDatefield = document.getElementById('startDate'+reportType);
    endDatefield=document.getElementById('endDate' + reportType);
    days=CalculateDays(reportType);
    console.log(days);
    startDatefield.setCustomValidity('');
    parametrizedStartDate=startDatefield.value;
    parameterizedEndDate=endDatefield.value;
    //Date Range must be between at least 7 to 30 Days at most
    if(days>=7 && days<=30 ){
        callbackFunction('custom',parametrizedStartDate,parameterizedEndDate);
    }

    else{
        startDatefield.setCustomValidity('Dates must be in the range of 7..30 days');
        startDatefield.reportValidity();
    }

}
//Calculate Date for customizing Timeframe
function CalculateDays(reportType){
    startDatefield = document.getElementById('startDate'+ reportType);
    endDatefield = document.getElementById('endDate'+ reportType);
    startDate=startDatefield.valueAsDate;
    endDate=endDatefield.valueAsDate;
    console.log("startDate,endDate",startDate,endDate);
    differenceinMilliseconds=endDate-startDate;

    return differenceinMilliseconds/MILLISECOND_A_DAY;

}

function setSalesAmount(period,reportType,LabelTotalItems){
    denominators=getDenominators(period,reportType);
    $('#textTotalGrossSales'+ reportType).text("$"+totalGrossSales.toFixed(2));
    $('#textNetGrossSales'+ reportType).text("$"+totalNetSales.toFixed(2));
    $('#textAvgGrossSales'+ reportType).text("$"+(totalGrossSales/denominators).toFixed(2));
    $('#textAvgNetSales'+ reportType).text("$"+(totalNetSales/denominators).toFixed(2));
    $('#textTotalOrders'+ reportType).text(totalItems);
    $('#LabelTotalItems'+ reportType).text(LabelTotalItems);
}



//Create Chart title for  different timeframes
function getChartTitle(period){
    if(period=='Last_7_days') return 'Sales in Last 7 Days';
    if(period=='Last_28_days') return 'Sales in Last 28 Days';
    if(period=='Last_6_months')return 'Sales in Last 6 Months';
    if(period=='Last_year') return 'Sales in Last Year';
    if(period=='custom') return 'Custom Date Range';
}

function getDenominators(period,reportType){
    if(period=='last_7_days') return 7;
    if(period=='last_28_days') return 28;
    if(period=='Last_6_months')return 60;
    if(period=='Last_year')return 12;
    if(period=='custom') return CalculateDays(reportType);
    else{
        return 7;
    }
}

//Format ChartData
function formatChartData(data,ColumnIndex1,ColumnIndex2){
    var formatter=new google.visualization.NumberFormat({
        prefix:'$'
    });
    formatter.format(data,ColumnIndex1);
    formatter.format(data,ColumnIndex2);
}