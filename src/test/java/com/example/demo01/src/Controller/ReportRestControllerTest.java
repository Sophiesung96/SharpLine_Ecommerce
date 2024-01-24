package com.example.demo01.src.Controller;

import com.example.demo01.src.Service.MasterOrderReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReportRestControllerTest {

    @Autowired
    MasterOrderReportService masterOrderReportService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetReportDataLast7Days() throws Exception {
        String URL="/reports/sales_by_date/last_7_days";
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andDo(print());
    }



}