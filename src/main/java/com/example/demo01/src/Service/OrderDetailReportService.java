package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.OrderDAO;
import com.example.demo01.src.Pojo.Order;
import com.example.demo01.src.Pojo.ReportItem;
import com.example.demo01.src.Pojo.ReportType;
import com.example.demo01.src.Pojo.TableOrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderDetailReportService extends  AbstractReportService{
    @Autowired
    OrderDAO orderDAO;

    @Override
    public List<ReportItem> getReportDataByDatRangeInternal(Date startDate, Date endDate, ReportType reportType) throws ParseException {
        List<TableOrderDetail>  detailList=new ArrayList<>();
        //Generate the chart by Category
        if(reportType.equals(ReportType.CATEGORY)){
            detailList=orderDAO.findOrderDetailListForGoogleChart(startDate,endDate);
            //Generate the chart by Product
        }else if(reportType.equals(ReportType.PRODUCT)){
            detailList=orderDAO.findOrderDetailListForGoogleChart(startDate,endDate);
        }

        printRawData(detailList);
        List<ReportItem> reportItemList=new ArrayList<>();
        for(TableOrderDetail order:detailList){
            String identifier;
            identifier=order.getCategoryName();
            ReportItem item=new ReportItem(identifier);
            int itemindex=0;
            itemindex=reportItemList.indexOf(item);
            float grossSales=order.getSubTotal()+order.getShippingCost();
            float netSales=order.getSubTotal()-order.getProductCost();
            // if the reportItem exists in the reportItemList
            if(itemindex>=0){
              item=reportItemList.get(itemindex);
              item.addNetSales(netSales);
              item.addGrossSales(grossSales);
              item.increaseProductCount(order.getQuantity());
            }else{
                reportItemList.add(new ReportItem(identifier,grossSales,netSales));
            }
           printReportData(reportItemList);
        }  
        return reportItemList;
    }

    private void printReportData(List<ReportItem> reportItemList) {
        for(ReportItem reportItem:reportItemList){
            System.out.printf("%-20s, %10.2f, %10.2f, %d, \n",
                    reportItem.getIdentifier(),reportItem.getGrossSales()
                    ,reportItem.getNetSales(),reportItem.getProductCount());
        }
    }


    private void printRawData( List<TableOrderDetail>  detailList){
        for(TableOrderDetail order:detailList){
            System.out.printf("%d, %-20s, %10.2f,%10.2f,%10.2f \n",
                    order.getQuantity(),order.getCategoryName(),order.getSubTotal(),
            order.getProductCost(),order.getShippingCost());
        }
    }


}
