package com.example.demo01.src.Controller;

import com.example.demo01.src.Service.MasterOrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @Autowired
    MasterOrderReportService orderReportService;

@GetMapping("/report")
    public String viewSalesReportHome(){
      return "Reports";
    }
}
