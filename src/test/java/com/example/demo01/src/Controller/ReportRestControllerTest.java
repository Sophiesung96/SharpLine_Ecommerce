package com.example.demo01.src.Controller;

import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.Order;
import com.example.demo01.src.Pojo.ReportItem;
import com.example.demo01.src.Pojo.ReportType;
import com.example.demo01.src.Service.MasterOrderReportServiceImpl;
import com.example.demo01.src.Service.OrderDetailReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReportRestControllerTest {

    @Autowired
    MasterOrderReportServiceImpl masterOrderReportService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrderDetailReportService orderDetailReportService;

    @Autowired
    OrderDAO orderDAO;

    @Test
    public void testGetReportDataLast7Days() throws Exception {
        String URL="/reports/sales_by_date/Last_7_days";
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void testGetReportDataLast28Days() throws Exception {
        String URL="/reports/sales_by_date/Last_28_days";
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetReportDataLast6Months() throws Exception {
        String URL="/reports/sales_by_date/Last_6_months";
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void testGetReportDataByDataRange() throws Exception {
        String startDate="2021-09-01";
        String endDate="2021-09-30";
        String URL="/reports/sales_by_date/"+startDate+"/"+endDate;
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetReportDataByCategory() throws Exception {
        String url="/reports/category/Last_7_days";
        String startDate="2021-01-01";
        String endDate="2021-01-22";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void testGetReportData() throws ParseException {
     List<ReportItem> list=masterOrderReportService.getReportDataLast7Days(ReportType.DAY);

        System.out.println(list);

    }

}