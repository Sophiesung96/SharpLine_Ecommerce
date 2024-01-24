package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.ReportItem;
import com.example.demo01.src.Service.MasterOrderReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ReportRestController {

    @Autowired
    MasterOrderReportService masterOrderReportService;

    @GetMapping("/reports/sales_by_date/{period}")
    public List<ReportItem> getReportDatByPeriod(@PathVariable String period){
        log.info("Get Report Data for 7 Days");
       return masterOrderReportService.getReportDataLast7Days();

    }
}
