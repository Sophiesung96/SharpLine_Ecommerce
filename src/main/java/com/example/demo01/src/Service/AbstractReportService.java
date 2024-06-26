package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.Order;
import com.example.demo01.src.Pojo.ReportItem;
import com.example.demo01.src.Pojo.ReportType;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractReportService {
    @Autowired
    OrderDAO orderDAO;

    protected SimpleDateFormat dateFormatter;

    //Get Data for 7 Days
    public List<ReportItem> getReportDataLast7Days(ReportType reportType) {
        try {
            return getReportDataLastXDays(7,reportType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Get Data for 28 Days
    public List<ReportItem> getReportDataLast28Days(ReportType reportType) {
        try {
            return getReportDataLastXDays(28,reportType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Get Data for over 6 months
    public List<ReportItem> getReportDataLast6months(ReportType reportType) {
        try {
            return getReportDataLastXMonths(6,reportType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Get Data for a whole year
    public List<ReportItem> getReportDataLastYear(ReportType reportType) {
        try {
            return getReportDataLastXMonths(12,reportType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //calculate Data by Days
    protected List<ReportItem> getReportDataLastXDays(int day,ReportType reportType) throws ParseException {
        dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
        Date endTime = dateFormatter.parse("2021-10-05"); // End date is set to "2021-01-22"
        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime); // Set calendar time to end time
        cal.add(Calendar.DAY_OF_MONTH, -(day - 1)); // Subtracting 6 days from end time to get the start time
        Date startTime = cal.getTime(); // Getting the start time
        System.out.println(startTime); // Output the calculated start time
        return getReportDataByDatRangeInternal(startTime,endTime,reportType);
    }

    //Calculate Data By Months
    protected List<ReportItem> getReportDataLastXMonths(int months,ReportType reportType) throws ParseException {
        // Date endTime=new Date();
        dateFormatter=new SimpleDateFormat("yyyy-MM");
        Date endTime=dateFormatter.parse("2021-10-05");
        Calendar cal= Calendar.getInstance();
        cal.setTime(endTime);
        cal.add(Calendar.MONTH,-(months-1));
          Date startTime=cal.getTime();
        return getReportDataByDatRangeInternal(startTime,endTime,reportType);
    }

    public abstract List<ReportItem> getReportDataByDatRangeInternal(Date startDate, Date endDate
            , ReportType reportType) throws ParseException;




}
