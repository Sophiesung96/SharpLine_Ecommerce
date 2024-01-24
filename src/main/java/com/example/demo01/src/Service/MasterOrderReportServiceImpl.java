package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.Order;
import com.example.demo01.src.Pojo.ReportItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MasterOrderReportServiceImpl implements MasterOrderReportService {
    @Autowired
    OrderDAO orderDAO;

    private SimpleDateFormat dateFormatter;


    @Override
    public List<ReportItem> getReportDataLast7Days() {
        try {
            return getReportDataLastXDays(7);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return null;
    }

    private List<ReportItem> getReportDataLastXDays(int day) throws ParseException {
        Date endTime=new Date();
       dateFormatter=new SimpleDateFormat("yyy-MM-dd");
       // Date startTime=dateFormatter.parse("2020-10-31");
      //  Date endTime=dateFormatter.parse("2021-11-29");
        Calendar cal= Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-(day-1));
         Date startTime=cal.getTime();
        log.info("StartTime:{}",startTime);
        log.info("EndTime:{}",endTime);
        return getReportDataByDatRange(startTime,endTime);
    }

    private List<ReportItem> getReportDataByDatRange(Date startTime,Date endTime){
        List<Order> Orderlist=orderDAO.findByOrderTimeBetween(startTime, endTime);
        printRawData(Orderlist);
        List<ReportItem> ReportList=createReportData(startTime,endTime);
        System.out.println();
        calculateSalesforReportData(Orderlist,ReportList);
        printFormattedReportData(ReportList);
        return ReportList;
    }
    //format the ReportItem List
    private void printFormattedReportData(List<ReportItem> reportList) {
        reportList.forEach(ReportItem->
                System.out.printf("%s, %10.2f, %10.2f, %d \n",
                        ReportItem.getIdentifier(),ReportItem.getGrossSales()
                        ,ReportItem.getNetSales(),ReportItem.getOrderCount()));
    }
     //Creating Report Data
    private List<ReportItem> createReportData(Date startTime, Date endTime) {
        List<ReportItem> reportItemList=new ArrayList<>();
        Calendar startDate=Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate=Calendar.getInstance();
        endDate.setTime(endTime);
        Date currentDate=startDate.getTime();
        String dateString=dateFormatter.format(currentDate);
        reportItemList.add(new ReportItem(dateString));
        //Add 1 day to the calendar incrementally according to the Calendar startDate
        // until the startDate is no longer before the endDate
        do{
            startDate.add(Calendar.DAY_OF_MONTH,1);
            currentDate=startDate.getTime();
             dateString=dateFormatter.format(currentDate);
            reportItemList.add(new ReportItem(dateString));
        }while(startDate.before(endDate));

        return reportItemList;
    }

     //Formatting the Order List (Raw data)
    private void printRawData( List<Order> Orderlist) {
        Orderlist.forEach(order-> System.out.printf("%-3d| %s | %10.2f | %10.2f \n"
          ,order.getId(),order.getOrderTime(),order.getTotal(),order.getProductCost()));
    }


    private void calculateSalesforReportData(List<Order>OrderList,List<ReportItem> reportItemList ){
       for(Order order:OrderList){
           ReportItem reportItem=new ReportItem(order.getOrderTime());
          int index= reportItemList.indexOf(reportItem);
           if(index>0){
               reportItem=reportItemList.get(index);
               reportItem.addGrossSales(order.getTotal());
               reportItem.addNetSales(order.getSubTotal()-order.getProductCost());
               reportItem.increaseOrderCount();

           }
       }
    }
}
