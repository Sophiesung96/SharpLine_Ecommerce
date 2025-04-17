package com.example.demo01.src.Controller;

import com.example.demo01.src.Service.MasterOrderReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @Autowired
    MasterOrderReportServiceImpl orderReportService;

@GetMapping("/report")
    public String viewSalesReportHome(){
      return "Reports";
    }
}
