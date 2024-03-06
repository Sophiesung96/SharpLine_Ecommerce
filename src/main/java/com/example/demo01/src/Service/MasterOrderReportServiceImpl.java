package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.Order;
import com.example.demo01.src.Pojo.OrderStatus;
import com.example.demo01.src.Pojo.ReportItem;
import com.example.demo01.src.Pojo.ReportType;
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
public class MasterOrderReportServiceImpl extends AbstractReportService {
    @Autowired
    OrderDAO orderDAO;

    private SimpleDateFormat dateFormatter;





    public  List<ReportItem> getReportDataByDatRangeInternal(Date startTime, Date endTime, ReportType reportType) throws ParseException {
        List<Order> Orderlist=orderDAO.findByOrderTimeBetween(startTime, endTime);
        printRawData(Orderlist);
        List<ReportItem> ReportList=createReportData(startTime,endTime,reportType);
        System.out.println();
        calculateSalesforReportData(Orderlist,ReportList);
        printFormattedReportData(ReportList);
        return ReportList;
    }
   //For creating custom data
    public List<ReportItem> getReportDataByDatRange(Date startTime, Date endTime,ReportType reportType) throws ParseException {
            dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
            return getReportDataByDatRangeInternal(startTime, endTime,reportType);
    }


    //format the ReportItem List
    private void printFormattedReportData(List<ReportItem> reportList) {
        reportList.forEach(ReportItem->
                System.out.printf("%s, %10.2f, %10.2f, %d \n",
                        ReportItem.getIdentifier(),ReportItem.getGrossSales()
                        ,ReportItem.getNetSales(),ReportItem.getOrderCount()));
    }
     //Creating Report Data
    private List<ReportItem> createReportData(Date startTime, Date endTime,ReportType reportType) {
        List<ReportItem> reportItemList=new ArrayList<>();
        dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
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
            if(reportType.equals(ReportType.DAY)){
                startDate.add(Calendar.DAY_OF_MONTH,1);
            }else if(reportType.equals(ReportType.MONTH)){
                startDate.add(Calendar.MONTH,1);
            }
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


    private void calculateSalesforReportData(List<Order>OrderList,List<ReportItem> reportItemList ) throws ParseException {
        dateFormatter=new SimpleDateFormat("yyyy-MM-dd");
       for(Order order:OrderList){
           Date orderDate=dateFormatter.parse(order.getOrderTime());
           String orderTime=dateFormatter.format(orderDate);
           ReportItem reportItem=new ReportItem(orderTime);
          int index= reportItemList.indexOf(reportItem);
           if(index>=0){
               reportItem=reportItemList.get(index);
               reportItem.addGrossSales(order.getTotal());
               reportItem.addNetSales(order.getSubTotal()-order.getProductCost());
               //calculate order number per day
               reportItem.increaseOrderCount();
           }
       }
    }






}
