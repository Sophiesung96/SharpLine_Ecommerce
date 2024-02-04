package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.ReportItem;
import com.example.demo01.src.Pojo.ReportType;
import com.example.demo01.src.Service.MasterOrderReportServiceImpl;
import com.example.demo01.src.Service.OrderDetailReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class ReportRestController {

    @Autowired
    MasterOrderReportServiceImpl masterOrderReportService;

    @Autowired
    OrderDetailReportService orderDetailReportService;

    @GetMapping("/reports/sales_by_date/{period}")
    @Operation(summary = "Get Report Data", description = "Get Report Data For 7 Days")
    public List<ReportItem> getReportDatByPeriod(@PathVariable String period){
        switch (period){
            case "Last_7_days":
                log.info("Get Report Data for 7 Days");
                return masterOrderReportService.getReportDataLast7Days(ReportType.DAY);

            case "Last_28_days":
                log.info("Get Report Data for 28 Days");
                return masterOrderReportService.getReportDataLast28Days(ReportType.DAY);
            case "Last_6_months":
                log.info("Get Report Data for 6 Months");
                return masterOrderReportService.getReportDataLast6months(ReportType.MONTH);
            case "Last_year":
                log.info("Get Report Data for a Year");
                return masterOrderReportService.getReportDataLastYear(ReportType.MONTH);
            default:
                log.info("Get Report Data for 7 Days By Default");
                return masterOrderReportService.getReportDataLast7Days(ReportType.DAY);




        }
    }
    @SneakyThrows
    @GetMapping("/reports/sales_by_date/{startDate}/{endDate}")
    @Operation(summary = "Get Report Data", description = "Get Report Data For Customized Timeframe")
    public List<ReportItem> getReportDatByCustomPeriod(@PathVariable String startDate
            ,@PathVariable String endDate){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date startTime=new Date();
        Date endTime=new Date();
        try {
             startTime =dateFormat.parse(startDate);
             endTime=dateFormat.parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return masterOrderReportService.getReportDataByDatRange(startTime,endTime,ReportType.DAY);
    }
    //Generating Google chart for category or product
    @GetMapping("/reports/{GroupBy}/{period}")
    public List<ReportItem> getReportByCategoryOrByProduct(@PathVariable String GroupBy,@PathVariable String period){
        ReportType reportType=ReportType.valueOf(GroupBy.toUpperCase());
        System.out.println(ReportType.valueOf(GroupBy.toUpperCase()));
        switch(period){
            case "Last_7_days":
                log.info("Get Report Data for 7 Days");
                return orderDetailReportService.getReportDataLast7Days(reportType);

            case "Last_28_days":
                log.info("Get Report Data for 28 Days");
                return orderDetailReportService.getReportDataLast28Days(reportType);
            case "Last_6_months":
                log.info("Get Report Data for 6 Months");
                return orderDetailReportService.getReportDataLast6months(reportType);
            case "Last_year":
                log.info("Get Report Data for a Year");
                return orderDetailReportService.getReportDataLastYear(reportType);
            default:
                log.info("Get Report Data for 7 Days By Default");
                return orderDetailReportService.getReportDataLast7Days(reportType);

        }

    }

    @GetMapping("/reports/{groupBy}/{startDate}/{endDate}")
    public List<ReportItem> getReportDataByCategoryOrProduct(@PathVariable String groupBy,
                                                             @PathVariable String startDate,@PathVariable String endDate) throws ParseException {
        ReportType reportType=ReportType.valueOf(groupBy.toUpperCase());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date startTime=new Date();
        Date endTime=new Date();
        try {
            startTime =dateFormat.parse(startDate);
            endTime=dateFormat.parse(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return orderDetailReportService.getReportDataByDatRangeInternal(startTime,endTime,reportType);
    }

}
