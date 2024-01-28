package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.ReportItem;
import com.example.demo01.src.Service.MasterOrderReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    MasterOrderReportService masterOrderReportService;

    @GetMapping("/reports/sales_by_date/{period}")
    @Operation(summary = "Get Report Data", description = "Get Report Data For 7 Days")
    public List<ReportItem> getReportDatByPeriod(@PathVariable String period){
        switch (period){
            case "Last_7_days":
                log.info("Get Report Data for 7 Days");
                return masterOrderReportService.getReportDataLast7Days();

            case "Last_28_days":
                log.info("Get Report Data for 28 Days");
                return masterOrderReportService.getReportDataLast28Days();
            case "Last_6_months":
                log.info("Get Report Data for 6 Months");
                return masterOrderReportService.getReportDataLast6months();
            case "Last_year":
                log.info("Get Report Data for a Year");
                return masterOrderReportService.getReportDataLastYear();
            default:
                log.info("Get Report Data for 7 Days By Default");
                return masterOrderReportService.getReportDataLast7Days();




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
        return masterOrderReportService.getReportDataByDatRange(startTime,endTime);
    }

}
